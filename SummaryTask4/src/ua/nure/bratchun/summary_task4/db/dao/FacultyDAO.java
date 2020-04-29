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
import ua.nure.bratchun.summary_task4.db.entity.Faculty;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;
/**
 * DAO class for working with MySQL
 * 
 * @author D.Bratchun
 *
 */
public class FacultyDAO extends AbstractDAO {
	
	private static final Logger LOG = Logger.getLogger(FacultyDAO.class);
	
	private static final String SQL_INSERT_FACULTY = "INSERT INTO faculties (name_ru, name_en, total_places, budget_places) VALUES (?,?,?,?)";
	private static final String SQL_DELETE_FACULTY_BY_ID = "DELETE FROM faculties WHERE id = ?";
	private static final String SQL_DELETE_PRILIMARY_BY_FACULTY_ID = "DELETE FROM faculty_subjects WHERE faculty_id=?";
	private static final String SQL_ADD_PRILIMARY_BY_FACULTY_ID_AND_SUBJECT_ID = "INSERT INTO faculty_subjects (faculty_id, subject_id) VALUES (?,?)";
	private static final String SQL_UPDATE_FACULTY = "UPDATE faculties SET name_ru=?, name_en=?, total_places=?, budget_places=? "
			+ " WHERE id=?";
	private static final String SQL_FIND_FACULTY_BY_NAME = "SELECT * FROM faculties WHERE name_ru =? OR name_en =?";
	private static final String SQL_FIND_FACULTY_BY_ID = "SELECT * FROM faculties WHERE id=?";
	private static final String SQL_FIND_ALL_FACULTY = "SELECT * FROM faculties";
	
	private static FacultyDAO instance;
	
	/**
	 * standard constructor
	 * @throws DBException
	 */
	private FacultyDAO() throws DBException {
		super();
	}
	
	/**
	 * constructor without JNDI for Junit
	 * @param isUseJNDI
	 * @throws DBException
	 */
	private FacultyDAO(boolean isUseJNDI) throws DBException {
		super(isUseJNDI);
	}
	
	/**
	 * singleton pattern
	 * @return
	 * @throws DBException
	 */
	public static synchronized FacultyDAO getInstance() throws DBException{
		if(instance == null) {
			instance = new FacultyDAO();
		}
		return instance;
	}
	
	/**
	 * singleton pattern witch use constructor without JUNDI for Junit
	 * @param isUseJNDI
	 * @return
	 * @throws DBException
	 */
	public static synchronized FacultyDAO getInstance(boolean isUseJNDI) throws DBException{
		if(instance == null) {
			instance = new FacultyDAO(isUseJNDI);
		}
		return instance;
	}
	
	/**
	 * 
	 * @param Insert faculty
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean insert(Faculty faculty) throws DBException {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			
			con = getConnection();
			statement = con.prepareStatement(SQL_INSERT_FACULTY, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statement.setString(k++, faculty.getNameRu());
			statement.setString(k++, faculty.getNameEn());
			statement.setInt(k++, faculty.getTotalPlaces());
			statement.setInt(k, faculty.getBudgetPlaces());
			
			if(statement.executeUpdate()>0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					faculty.setId(resultSet.getInt(1));
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
	 * 
	 * @param Update faculty
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean update(Faculty faculty) throws DBException {
		LOG.debug("Start updating faculty " + faculty);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
		
		try {
			
			con = getConnection();
			statement = con.prepareStatement(SQL_UPDATE_FACULTY, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			
			statement.setString(k++, faculty.getNameRu());
			statement.setString(k++, faculty.getNameEn());
			statement.setInt(k++, faculty.getTotalPlaces());
			statement.setInt(k++, faculty.getBudgetPlaces());
			statement.setInt(k++, faculty.getId());
			
			if(statement.executeUpdate() > 0) {
				LOG.trace("Faculty with name " + faculty.getNameEn() + " was update");
			}
			result = true;
			LOG.trace("Faculty was updated " + faculty);
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param Delete faculty by id
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean deleteByID(int id) throws DBException {
		boolean result = false;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_DELETE_FACULTY_BY_ID);
			
			statement.setInt(1, id);
			 
			result = statement.executeUpdate() > 0;
			LOG.trace("Faculty was delited (id: " + id + ")");
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException();
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Returns a sorted list of faculties
	 * @param Sort option
	 * @param Sort direction
	 * @return List of faculties
	 * @throws DBException
	 */
	public List<Faculty> findAllOrderBy(String orderBy, String direction) 
			throws DBException {
		
		List<Faculty> facultys = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ALL_FACULTY + " ORDER BY " + orderBy + " " + direction);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				facultys.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return facultys;
	}
	
	/**
	 * Returns a sorted list of faculties with pagination 
	 * @param Sort option
	 * @param Sort direction
	 * @param Pagination start
	 * @param Pagination size
	 * @return List of faculties
	 * @throws DBException
	 */
	public List<Faculty> findAllOrderBy(String orderBy, String direction, int offset, int lines) 
			throws DBException {
		
		List<Faculty> facultys = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(
					SQL_FIND_ALL_FACULTY +  " ORDER BY " + orderBy + " " + direction +" LIMIT " + offset + ", " + lines );
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				facultys.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return facultys;
	}
	
	/**
	 * Return faculty by id
	 * @param faculty id
	 * @return
	 * @throws DBException
	 */
	public Faculty findById(int id) throws DBException {
		Faculty faculty = null;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_FACULTY_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				faculty = extract(resultSet);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return faculty;
	}
	
	/**
	 * Return all faculty
	 * @return List of faculties
	 * @throws DBException
	 */
	public List<Faculty> findAll() throws DBException {
	
		List<Faculty> facultys = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ALL_FACULTY);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				facultys.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
		
		return facultys;
	}
	
	/**
	 * The method adds exams to the faculty
	 * @param Facultie's id
	 * @param List of subject's id
	 * @return Result true or false
	 * @throws DBException
	 */
	public boolean addAllPriliminaryByFacultyId(int facultyId, List<Integer> subjectsId) throws DBException {
		boolean result = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			statement = con.prepareStatement(SQL_ADD_PRILIMARY_BY_FACULTY_ID_AND_SUBJECT_ID);
			int k = 1;
			for(int subjectId : subjectsId) {
				k=1;
				statement.setInt(k++, facultyId);
				statement.setInt(k, subjectId);
				if(statement.executeUpdate() > 0) {
					LOG.trace("try to add prilimary to faculty id " + facultyId + " subject id " + subjectId);
				}
			}
			result = true;
			con.commit();
			LOG.trace("faculty_subjects was update");
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException();
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Method removes all exams for faculty
	 * @param facultyId
	 * @return
	 * @throws DBException
	 */
	public boolean deleteAllPriliminaryByFacultyId(int facultyId) throws DBException {
		boolean result = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			statement = con.prepareStatement(SQL_DELETE_PRILIMARY_BY_FACULTY_ID);	
			statement.setInt(1, facultyId);
			 
			result = statement.executeUpdate() > 0;
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException();
		} finally {
			close(con, statement, resultSet);
		}
		return result;
	}
	
	/**
	 * Check name of faculties
	 * @param name
	 * @return
	 * @throws DBException
	 */
	public boolean hasFacultyName(String name) throws DBException {
		boolean result = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_FACULTY_BY_NAME);
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
	 * Extract faculty from ResultSet
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Faculty extract(ResultSet resultSet) throws SQLException {
		Faculty faculty = new Faculty();
		
		faculty.setId(resultSet.getInt(Fields.ENTITY_ID));
		faculty.setNameEn(resultSet.getString(Fields.FACULTY_NAME_EN));
		faculty.setNameRu(resultSet.getString(Fields.FACULTY_NAME_RU));
		faculty.setTotalPlaces(resultSet.getInt(Fields.FACULTY_TOTAL_PLACES));
		faculty.setBudgetPlaces(resultSet.getInt(Fields.FACULTY_BUDGET_PLACES));
		
		return faculty;
	}
	
}
