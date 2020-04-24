package ua.nure.bratchun.summary_task4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.db.EntryType;
import ua.nure.bratchun.summary_task4.db.ExamType;
import ua.nure.bratchun.summary_task4.db.Fields;
import ua.nure.bratchun.summary_task4.db.entity.Application;
import ua.nure.bratchun.summary_task4.db.entity.Entrant;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;

/**
 * This class manages statements
 * @author D.Bratchun
 *
 */
public class StatementDAO extends AbstractDAO {

	private static StatementDAO instance;

	private static final String SQL_ADD_APPLICATION_TO_STATEMENT = "INSERT INTO statement (entrant_id, faculty_id, diploma_score, preliminary_score, entry_type_id) VALUES (?,?,?,?,?)";
	private static final String SQL_UPDATE_APPLICATION_ENTRY_TYPE = "UPDATE statement SET entry_type_id = ? WHERE entrant_id = ? and faculty_id = ?";
	private static final String SQL_GET_APPLICATION_FROM_STATEMENT = "SELECT users.login, users.password, users.first_name, users.last_name, users.email,users.lang, "
			+ "entrants.*, statement.diploma_score, (statement.preliminary_score+statement.diploma_score) AS score, statement.preliminary_score, "
			+ "statement.entry_type_id, statement.faculty_id "
			+ "FROM statement JOIN entrants ON statement.entrant_id=entrants.id "
			+ "JOIN users ON users.id = entrants.id WHERE faculty_id=? " + "ORDER BY score DESC";
	private static final String SQL_IS_STATEMENT_RESULT = 
			"SELECT faculty_id FROM statement WHERE faculty_id = ? "
			+ "AND entry_type_id>1 GROUP BY faculty_id;";
	private static final String SQL_GET_APPLICATIONS_BY_FACULTY = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, "
			+ "users.lang , entrants.*, statement.entry_type_id , avg(grades.value) score FROM grades "
			+ "JOIN entrants ON grades.entrant_id=entrants.id " + "JOIN users ON users.id = entrants.id LEFT "
			+ "JOIN statement ON grades.entrant_id = statement.entrant_id " + "WHERE grades.faculty_id=? "
			+ "GROUP BY grades.entrant_id ORDER BY avg(grades.value) DESC";
	private static final String SQL_GET_APPLICATIONS_BY_FACULTY_AND_ENTRANT = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, users.lang , entrants.*, statement.entry_type_id "
			+ "FROM grades JOIN entrants ON grades.entrant_id=entrants.id " + "JOIN users ON users.id = entrants.id "
			+ "LEFT JOIN statement ON statement.entrant_id = entrants.id "
			+ "WHERE grades.faculty_id=? AND grades.entrant_id=? " + "GROUP BY grades.entrant_id";
	private static final String SQL_GET_SCORE = "SELECT avg(value) score FROM grades WHERE entrant_id = ? and faculty_id=? and exam_type_id =? "
			+ "GROUP BY entrant_id and exam_type_id;";

	private static final Logger LOG = Logger.getLogger(StatementDAO.class);
	
	// standard constructor
	private StatementDAO() throws DBException {
		super();
	}
	
	// constructor constructor with the option not to use JNDI for Junit
	private StatementDAO(boolean isUseJNDI) throws DBException {
		super(isUseJNDI);
	}

	// singleton pattern
	public static synchronized StatementDAO getInstance() throws DBException {
		if (instance == null) {
			instance = new StatementDAO();
		}
		return instance;
	}
	
	// singleton pattern with use constructor with the option not to use JNDI for Junit 
	public static synchronized StatementDAO getInstance(boolean isUseJNDI) throws DBException {
		if (instance == null) {
			instance = new StatementDAO(isUseJNDI);
		}
		return instance;
	}

	/**
	 * Return score by faculty id entrant id and exam type 
	 * @param facultyId
	 * @param entrantId
	 * @param examType
	 * @return double score
	 * @throws DBException
	 */
	public double getScore(int facultyId, int entrantId, ExamType examType) throws DBException {
		double score = 0;

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_SCORE);
			int k = 1;
			statement.setInt(k++, entrantId);
			statement.setInt(k++, facultyId);
			statement.setInt(k++, examType.ordinal());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				score = resultSet.getDouble("score");
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return score;
	}
	
	/**
	 * Update application
	 * @param applications
	 * @return
	 * @throws AppException
	 */
	public boolean updateApplications(List<Application> applications) throws AppException {
		boolean result = true;
	
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		try {
	
			con = getConnection();
			con.setAutoCommit(false);
			statement = con.prepareStatement(SQL_UPDATE_APPLICATION_ENTRY_TYPE);
	
			for (Application application : applications) {
				int k = 1;
				statement.setInt(k++, application.getEntryTypeId());
				statement.setInt(k++, application.getEntrantId());
				statement.setInt(k++, application.getFacultyId());
				if (statement.executeUpdate() == 0) {
					result = false;
				}
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}
	
		return result;
	}
	
	/**
	 * Check statement result
	 * @param facultyId
	 * @return Result true or false
	 * @throws DBException
	 */
	public boolean hasStatementResult(int facultyId) throws DBException {
		
		boolean result = false;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
	
		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_IS_STATEMENT_RESULT);
			statement.setInt(1, facultyId);
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
	 * Get application by faculty
	 * @param facultyId
	 * @return
	 * @throws DBException
	 */
	public List<Application> getApplicationByFacultyId(int facultyId) throws DBException {
		List<Application> applications = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_APPLICATIONS_BY_FACULTY);
			statement.setInt(1, facultyId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Application application = new Application(extractEntrant(resultSet), facultyId);
				application.setDiplomaScore(getScore(facultyId, application.getEntrantId(), ExamType.DIPLOMA));
				application.setPreliminaryScore(getScore(facultyId, application.getEntrantId(), ExamType.PRELIMINARY));
				application.setEntryTypeId(resultSet.getInt(Fields.APPLICATION_ENTRY_TYPE_ID));
				applications.add(application);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return applications;
	}
	/**
	 * Get applications by faculty with pagination
	 * @param facultyId
	 * @param pagination start
	 * @param pagination size
	 * @return list of application
	 * @throws DBException
	 */
	public List<Application> getApplicationByFacultyId(int facultyId, int offset, int lines) throws DBException {
		List<Application> applications = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_APPLICATIONS_BY_FACULTY + " LIMIT " + offset + ", " + lines);
			statement.setInt(1, facultyId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Application application = new Application(extractEntrant(resultSet), facultyId);
				application.setDiplomaScore(getScore(facultyId, application.getEntrantId(), ExamType.DIPLOMA));
				application.setPreliminaryScore(getScore(facultyId, application.getEntrantId(), ExamType.PRELIMINARY));
				application.setEntryTypeId(resultSet.getInt(Fields.APPLICATION_ENTRY_TYPE_ID));
				applications.add(application);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return applications;
	}
	
	/**
	 * Get application by faculty and entrand
	 * @param facultyId
	 * @param entrantId
	 * @return application
	 * @throws DBException
	 */
	public Application getApplicationByFacultyIdEntrantId(int facultyId, int entrantId) throws DBException {
		Application application = null;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_APPLICATIONS_BY_FACULTY_AND_ENTRANT);
			int k = 1;
			statement.setInt(k++, facultyId);
			statement.setInt(k, entrantId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				application = new Application(extractEntrant(resultSet), facultyId);
				application.setDiplomaScore(getScore(facultyId, entrantId, ExamType.DIPLOMA));
				application.setPreliminaryScore(getScore(facultyId, entrantId, ExamType.PRELIMINARY));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return application;
	}
	
	/**
	 * Add application to statement
	 * @param application
	 * @return
	 * @throws DBException
	 */
	public boolean addApplicationToStatement(Application application) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;

		try {

			con = getConnection();
			statement = con.prepareStatement(SQL_ADD_APPLICATION_TO_STATEMENT, Statement.RETURN_GENERATED_KEYS);
			int k = 1;

			statement.setInt(k++, application.getEntrantId());
			statement.setInt(k++, application.getFacultyId());
			statement.setDouble(k++, application.getDiplomaScore());
			statement.setDouble(k++, application.getPreliminaryScore());
			statement.setInt(k++, EntryType.UNDER_CONSIDERATION.ordinal());

			if (statement.executeUpdate() > 0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
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
	 * Get applications from statement by faculty
	 * @param facultyId
	 * @return
	 * @throws DBException
	 */
	public List<Application> getApplicationFromStatement(int facultyId) throws DBException {
		List<Application> applications = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_APPLICATION_FROM_STATEMENT);
			statement.setInt(1, facultyId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Application application = new Application(extractEntrant(resultSet), facultyId);
				application.setDiplomaScore(resultSet.getDouble(Fields.APPLICATION_DIPLOMA_SCORE));
				application.setPreliminaryScore(resultSet.getDouble(Fields.APPLICATION_PRELIMINARY_SCORE));
				application.setEntryTypeId(resultSet.getInt(Fields.APPLICATION_ENTRY_TYPE_ID));
				applications.add(application);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return applications;
	}
	
	/**
	 * Get application from statement with pagination
	 * @param facultyId
	 * @param pagination start
	 * @param pagination size
	 * @return
	 * @throws DBException
	 */
	public List<Application> getApplicationFromStatement(int facultyId, int offset, int lines) throws DBException {
		List<Application> applications = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_GET_APPLICATION_FROM_STATEMENT + " LIMIT " + offset + ", " + lines);
			statement.setInt(1, facultyId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Application application = new Application(extractEntrant(resultSet), facultyId);
				application.setDiplomaScore(resultSet.getDouble(Fields.APPLICATION_DIPLOMA_SCORE));
				application.setPreliminaryScore(resultSet.getDouble(Fields.APPLICATION_PRELIMINARY_SCORE));
				application.setEntryTypeId(resultSet.getInt(Fields.APPLICATION_ENTRY_TYPE_ID));
				applications.add(application);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return applications;
	}
	/**
	 * Extract entrant from ResultSet
	 * @param ResultSet
	 * @return Entrant
	 * @throws SQLException
	 */
	private Entrant extractEntrant(ResultSet rs) throws SQLException {
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
}
