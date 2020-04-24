package ua.nure.bratchun.summary_task4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.db.Fields;
import ua.nure.bratchun.summary_task4.db.entity.Entrant;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;

/**
 * DAO class for working with MySQL
 * 
 * @author D.Bratchun
 *
 */
public class EntrantDAO extends AbstractDAO{
	
	
	private static final Logger LOG = Logger.getLogger(EntrantDAO.class);
	
	// SQL commands
	private static EntrantDAO instance;

	private static final String SQL_INSERT_ENTRANTS = "INSERT INTO entrants (city, region, school, id) VALUES(?,?,?,?)";
	private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, first_Name, last_Name, email, lang, role_id)"
			+ " VALUES (?,?,?,?,?,?,?)";
	private static final String SQL_FIND_ENTRANT_BY_LOGIN = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, users.lang , entrants.* "
			+ "FROM users JOIN entrants ON entrants.id = users.id WHERE users.login=?";
	private static final String SQL_FIND_ENTRANTS = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, users.lang , entrants.* "
			+ "FROM users JOIN entrants ON entrants.id = users.id";
	private static final String SQL_BLOCK_ENTRANT_BY_ID = "UPDATE entrants SET isBlocked = 1 WHERE id=?";
	private static final String SQL_UNBLOCK_ENTRANT_BY_ID = "UPDATE entrants SET isBlocked = 0 WHERE id=?";
	private static final String SQL_UPDATE_ENTRANT = "UPDATE entrants SET city =?, region=?, school=?" + " WHERE id=?";
	private static final String SQL_UPDATE_USER = "UPDATE users SET "
			+ "password=?, first_name=?, last_name=?, email=?, lang=?, role_id=? " + " WHERE id=?";
	private static final String SQL_FIND_BLOCKED_ENTRANTS = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, users.lang , entrants.* "
			+ "FROM users JOIN entrants ON entrants.id = users.id WHERE entrants.isBlocked = true";
	
	// standard constructor
	private EntrantDAO() throws DBException {
		super();
	}
	
	// constructor without JNDI for Junit
	private EntrantDAO(boolean isUseJNDI) throws DBException {
		super(isUseJNDI);
	}

	/**
	 * Extract entrant from ResultSet
	 * @param ResultSet from the database request 
	 * @return Entrant
	 * @throws SQLException
	 */
	private Entrant extract(ResultSet rs) throws SQLException {
		Entrant entrant = new Entrant();
		entrant.setId(rs.getInt(Fields.ENTITY_ID));
		entrant.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		entrant.setLastName(rs.getString(Fields.USER_LAST_NAME));
		entrant.setEmail(rs.getString(Fields.USER_EMAIL));
		entrant.setLang(rs.getString(Fields.USER_LANG));
		entrant.setLogin(rs.getString(Fields.USER_LOGIN));
		entrant.setPassword(rs.getString(Fields.USER_PASSWORD));
		entrant.setRoleId(0);
		
		entrant.setCity(rs.getString(Fields.ENTRANTS_CITY));
		entrant.setRegion(rs.getString(Fields.ENTRANTS_REGION));
		entrant.setSchool(rs.getString(Fields.ENTRANTS_SCHOOL));
		entrant.setIsBlocked(rs.getBoolean(Fields.ENTRANTS_IS_BLOCKED));
		
		return entrant;
	}

	// singleton pattern
	public static synchronized EntrantDAO getInstance() throws DBException{
		if(instance == null) {
			instance = new EntrantDAO();
		}
		return instance;
	}
	
	// singleton pattern use constructor without JUNDI for Junit
	public static synchronized EntrantDAO getInstance(boolean isUseJNDI) throws DBException{
		if(instance == null) {
			instance = new EntrantDAO(isUseJNDI);
		}
		return instance;
	}
	
	/**
	 * Insert entrant
	 * @param entrant
	 * @throws DBException
	 */
	public boolean insert(Entrant entrant) throws DBException {
		
		PreparedStatement statementUser = null;
		PreparedStatement statementEntrant = null;
		ResultSet resultSetUser = null;
		ResultSet resultSetEntrant = null;
		Connection connectionUser = null;
		Connection connectionEntrant = null;
		boolean result = false;
		
		try {
			
			connectionUser = getConnection();
			connectionEntrant = getConnection();
			statementUser = connectionUser.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			statementEntrant = connectionEntrant.prepareStatement(SQL_INSERT_ENTRANTS, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statementUser.setString(k++, entrant.getLogin());
			statementUser.setString(k++, entrant.getPassword());
			statementUser.setString(k++, entrant.getFirstName());
			statementUser.setString(k++, entrant.getLastName());
			statementUser.setString(k++, entrant.getEmail());
			statementUser.setString(k++, entrant.getLang());
			statementUser.setInt(k, entrant.getRoleId());
			
			if(statementUser.executeUpdate()>0) {
				resultSetUser = statementUser.getGeneratedKeys();
				if (resultSetUser.next()) {
					entrant.setId(resultSetUser.getInt(1));
				}
			} else {
				throw new SQLException(Messages.ERR_CANNOT_CREATE_USER);
			}
			k=1;
			statementEntrant.setString(k++, entrant.getCity());
			statementEntrant.setString(k++, entrant.getRegion());
			statementEntrant.setString(k++, entrant.getSchool());
			statementEntrant.setInt(k++, entrant.getId());
			if(statementEntrant.executeUpdate()>0) {
				resultSetEntrant = statementEntrant.getGeneratedKeys();
				if(resultSetEntrant.next()) {
					entrant.setId(resultSetEntrant.getInt(1));
					result = true;
				}
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(connectionEntrant, statementEntrant, resultSetEntrant);
			close(connectionUser, statementUser, resultSetUser);
		}
		
		return result;
	}
	
	/**
	 * Update entrant
	 * @param entrant
	 * @throws DBException
	 */
	public boolean update(Entrant entrant) throws DBException {
		PreparedStatement statementUser = null;
		PreparedStatement statementEntrant = null;
		ResultSet resultSetUser = null;
		ResultSet resultSetEntrant = null;
		Connection connectionUser = null;
		Connection connectionEntrant = null;
		boolean result = false;
		
		try {
			
			connectionUser = getConnection();
			connectionEntrant = getConnection();
			statementUser = connectionUser.prepareStatement(SQL_UPDATE_USER, Statement.RETURN_GENERATED_KEYS);
			statementEntrant = connectionEntrant.prepareStatement(SQL_UPDATE_ENTRANT, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statementUser.setString(k++, entrant.getPassword());
			statementUser.setString(k++, entrant.getFirstName());
			statementUser.setString(k++, entrant.getLastName());
			statementUser.setString(k++, entrant.getEmail());
			statementUser.setString(k++, entrant.getLang());
			statementUser.setInt(k++, entrant.getRoleId());
			statementUser.setInt(k, entrant.getId());
			
			if(statementUser.executeUpdate() > 0) {
				LOG.trace("User with login " + entrant.getLogin() + " try to update");
			}
			k=1;
			statementEntrant.setString(k++, entrant.getCity());
			statementEntrant.setString(k++, entrant.getRegion());
			statementEntrant.setString(k++, entrant.getSchool());
			statementEntrant.setInt(k++, entrant.getId());
			if(statementEntrant.executeUpdate()>0) {
				LOG.trace("Entrant with login " + entrant.getLogin() + " try to update");
			}
			result = true;
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(connectionEntrant, statementEntrant, resultSetEntrant);
			close(connectionUser, statementUser, resultSetUser);
		}
		
		return result;
		
	}
	
	/**
	 * Delete entrant
	 * @param login
	 * @throws DBException
	 */
	public boolean deleteByLogin(String login) throws DBException {
		UserDAO userDAO = UserDAO.getInstance(isUseJNDI());
		return userDAO.deleteByLogin(login);
	}
	
	/**
	 * Block entrant by id
	 * @param id
	 * @throws DBException
	 */
	public boolean blockById(int id) throws DBException {
		boolean result = false;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_BLOCK_ENTRANT_BY_ID);
			statement.setInt(1, id);
			result = statement.executeUpdate() > 0;
			LOG.trace("Entrant (id:" + id  + ") has been blocked ");
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Unblock for entrant by id
	 * @param id
	 * @throws DBException
	 */
	public boolean unblockById(int id) throws DBException {
		boolean result = false;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_UNBLOCK_ENTRANT_BY_ID);
			statement.setInt(1, id);
			result = statement.executeUpdate() > 0;
			LOG.trace("User (id: " + id + ") has been unblocked ");
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Find all entrants
	 * @return list of entrants
	 * @throws DBException
	 */
	public List<Entrant> findAll() throws DBException {
		List<Entrant> entrants = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ENTRANTS);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				entrants.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return entrants;
	}
	
	/**
	 * Find entrant by login
	 */
	public Entrant findByLogin(String login) throws DBException {
		Entrant entrant = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ENTRANT_BY_LOGIN);
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				entrant = extract(resultSet);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return entrant;
	}
	
	/**
	 * This method returns a list of blocked entrants
	 * @param offset
	 * @param lines
	 * @return list of entrant who was blocked
	 * @throws DBException
	 */
	public List<Entrant> findAllBlocked() throws DBException {
		List<Entrant> entrants = new ArrayList<>();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_BLOCKED_ENTRANTS);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				entrants.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return entrants;
	}
	
	/**
	 * This method returns a list of blocked entrants with pagination
	 * @param offset
	 * @param lines
	 * @return list of entrant who was blocked
	 * @throws DBException
	 */
	public List<Entrant> findAllBlocked(int offset, int lines) throws DBException {
		List<Entrant> entrants = new ArrayList<>();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_BLOCKED_ENTRANTS + " LIMIT " + offset + ", " + lines);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				entrants.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return entrants;
	}
	
}
