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
import ua.nure.bratchun.summary_task4.db.entity.Subject;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;

/**
 * DAO class for working with MySQL
 * 
 * @author D.Bratchun
 *
 */
public class SubjectDAO extends AbstractDAO{
	
	private static SubjectDAO instance;
	
	private static final Logger LOG = Logger.getLogger(SubjectDAO.class);
	
	private static final String SQL_INSERT_SUBJECT ="INSERT INTO subjects (name_ru, name_en) VALUES (?, ?)";
	private static final String SQL_UPDATE_SUBJECT ="UPDATE subjects SET name_ru=?, name_en=? WHERE id=?";
	private static final String SQL_DELITE_SUBJECT_BY_ID ="DELETE FROM subjects WHERE id=?";
	private static final String SQL_FIND_SUBJECT_BY_NAME ="SELECT * FROM subjects WHERE name_ru=? or name_en=?";
	private static final String SQL_FIND_ALL_SUBJECT ="SELECT * FROM subjects";
	private static final String SQL_FIND_ALL_SUBJECTS_BY_FACULTY_ID =
			"SELECT * FROM subjects "
			+ "JOIN faculty_subjects ON subjects.id = faculty_subjects.subject_id "
			+ "WHERE faculty_subjects.faculty_id=?";
	
	// standard constructor
	private SubjectDAO() throws DBException {
		super();
	}
	
	// constructor constructor with the option not to use JNDI for Junit
	private SubjectDAO(boolean isUseJNDI) throws DBException {
		super(isUseJNDI);
	}
	
	/**
	 * Extract subject from ResultSet
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Subject extract(ResultSet resultSet) throws SQLException {
		Subject subject = new Subject();
		subject.setId(resultSet.getInt(Fields.ENTITY_ID));
		subject.setNameEn(resultSet.getString(Fields.SUBJECTS_NAME_EN));
		subject.setNameRu(resultSet.getString(Fields.SUBJECTS_NAME_RU));
		return subject;
	}
	
	// singleton pattern
	public static synchronized SubjectDAO getInstance() throws DBException{
		if(instance == null) {
			instance = new SubjectDAO();
		}
		return instance;
	}
	
	// singleton pattern with use constructor with the option not to use JNDI for Junit 
	public static synchronized SubjectDAO getInstance(boolean isUseJNDI) throws DBException{
		if(instance == null) {
			instance = new SubjectDAO(isUseJNDI);
		}
		return instance;
	}
	
	/**
	 * Find all subject
	 * @return
	 * @throws DBException
	 */
	public List<Subject> findAll() throws DBException {
		List<Subject> subjects = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ALL_SUBJECT);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				subjects.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return subjects;
	}
	
	/**
	 * Find all subject with sorting
	 * @param Sort option
	 * @param Sort direction
	 * @return List of subjects
	 * @throws DBException
	 */
	public List<Subject> findAllOrderBy(String orderBy, String direction) 
			throws DBException {
		
		List<Subject> subjects = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			
			statement = con.prepareStatement(SQL_FIND_ALL_SUBJECT + " ORDER BY " + orderBy + " " + direction);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				subjects.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return subjects;
	}
	
	/**
	 * Check name
	 * @param name
	 * @return result true or false
	 * @throws DBException
	 */
	 public boolean hasName(String name) throws DBException {
		boolean result = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_SUBJECT_BY_NAME);
			int k = 1;
			statement.setString(k++, name);
			statement.setString(k, name);
			resultSet = statement.executeQuery();
			result = resultSet.next();
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return result;
	}
	
	/**
	 * Insert subject
	 * @param subject
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean insert(Subject subject) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			
			con = getConnection();
			statement = con.prepareStatement(SQL_INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statement.setString(k++, subject.getNameRu());
			statement.setString(k, subject.getNameEn());
			
			if(statement.executeUpdate()>0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					subject.setId(resultSet.getInt(1));
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
	 * Update subject
	 * @param subject
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean update(Subject subject) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			
			con = getConnection();
			statement = con.prepareStatement(SQL_UPDATE_SUBJECT, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statement.setString(k++, subject.getNameRu());
			statement.setString(k++, subject.getNameEn());
			statement.setInt(k, subject.getId());
			
			if(statement.executeUpdate() > 0) {
				LOG.trace("Subject with id " + subject.getId() + " was update");
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
	
	/**
	 * Delete subject by id
	 * @param id
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean delete(int id) throws DBException {
		boolean result = false;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_DELITE_SUBJECT_BY_ID);
			statement.setInt(1, id);
			
			result =  statement.executeUpdate() > 0;
			LOG.trace("Subject was delited (id: " + id + ")");
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException();
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Return all subject
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public List<Subject> getSubjectsByFacultyId(int id) throws DBException{
		List<Subject> subjects = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ALL_SUBJECTS_BY_FACULTY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				subjects.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return subjects;
	}
}
