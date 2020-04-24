package ua.nure.bratchun.summary_task4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.db.Fields;
import ua.nure.bratchun.summary_task4.db.entity.User;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;

/**
 * DAO class for working with MySQL
 * 
 * @author D.Bratchun
 *
 */
public class UserDAO extends AbstractDAO{
	
	private static final Logger LOG = Logger.getLogger(UserDAO.class);
	
	private static UserDAO instance;
	
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
	private static final String SQL_INSERT_USER = 
			"INSERT INTO users (login, password, first_Name, last_Name, email, lang, role_id)"
			+ " VALUES (?,?,?,?,?,?,?)";
	private static final String SQL_GET_USER_ID_BY_LOGIN = "SELECT id FROM users WHERE login=?";
	private static final String SQL_DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login=?";
	private static final String SQL_UPDATE_USER = "UPDATE users SET "
			+ "password =?, first_name=?, last_name=?, email=?, lang=?, role_id=? "
			+ "WHERE id=?";

	// constructor constructor with the option not to use JNDI for Junit
	private UserDAO(boolean isUseJNDI) throws DBException {
		super(isUseJNDI);
	}
	
	//standard constructor
	private UserDAO() throws DBException {
		super();
	}
	
	/**
	 * Extract user from ResultSet
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setLang(rs.getString(Fields.USER_LANG));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setEmail(rs.getString(Fields.USER_EMAIL));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setRoleId(rs.getInt(Fields.USER_ROLE));
		return user;
	}
	
	// singleton pattern
	public static synchronized UserDAO getInstance() throws DBException{
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	// singleton pattern with use constructor with the option not to use JNDI for Junit 
	public static synchronized UserDAO getInstance(boolean isUseJNDI) throws DBException{
		if(instance == null) {
			instance = new UserDAO(isUseJNDI);
		}
		return instance;
	}
	
	/**
	 * Return user id by login
	 * @param login
	 * @return user id or -1 if user with this login isn't exist
	 * @throws DBException
	 */
	public int getIdByLogin(String login) throws DBException {
		int result = -1;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_USER_ID_BY_LOGIN);
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return result;
	}
	
	/**
	 * Find user by login
	 * @param login
	 * @return	User
	 * @throws DBException
	 */
	public User findByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = extractUser(resultSet);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return user;
	}
	
	/**
	 * Find User by email
	 * @param email
	 * @return user
	 * @throws DBException
	 */
	public User findByEmail(String email) throws DBException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			statement.setString(1, email);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = extractUser(resultSet);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return user;
	}

	/**
	 * Insert user
	 * @param user
	 * @return User
	 * @throws DBException
	 */
	public boolean insert(User user) throws DBException {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		if(user == null) {
			return result;
		}
		
		try {
			
			con = getConnection();
			statement = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statement.setString(k++, user.getLogin());
			statement.setString(k++, user.getPassword());
			statement.setString(k++, user.getFirstName());
			statement.setString(k++, user.getLastName());
			statement.setString(k++, user.getEmail());
			statement.setString(k++, user.getLang());
			statement.setInt(k++, user.getRoleId());
			
			if(statement.executeUpdate()>0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					user.setId(resultSet.getInt(1));
					result = true;
				}
			}
			
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return result;
	}
	
	/**
	 * Check email
	 * @param email
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean hasEmail(String email) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			statement.setString(1, email);
			
			resultSet = statement.executeQuery();
			result= resultSet.next();
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Check login
	 * @param login
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean hasLogin(String login) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			result= resultSet.next();
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	/**
	 * Delete by login
	 * @param login
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean deleteByLogin(String login) throws DBException {
		boolean result = false;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_DELETE_USER_BY_LOGIN);
			statement.setString(1, login);
			
			result =  statement.executeUpdate() > 0;
			LOG.trace("User was delited (login: " + login + ")");
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException();
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Update
	 * @param user
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean update(User user) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			
			con = getConnection();
			statement = con.prepareStatement(SQL_UPDATE_USER, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statement.setString(k++, user.getPassword());
			statement.setString(k++, user.getFirstName());
			statement.setString(k++, user.getLastName());
			statement.setString(k++, user.getEmail());
			statement.setString(k++, user.getLang());
			statement.setInt(k++, user.getRoleId());
			statement.setInt(k, user.getId());
			
			if(statement.executeUpdate() > 0) {
				LOG.trace("User with login " + user.getLogin() + " was update");
			}
			result = true;
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return result;
	}
}
