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
import ua.nure.bratchun.summary_task4.db.entity.Grade;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;

/**
 * DAO class for working with MySQL
 * 
 * @author D.Bratchun
 *
 */
public class GradeDAO extends AbstractDAO {

	private static GradeDAO instance;

	private static final Logger LOG = Logger.getLogger(GradeDAO.class);

	public static final String SQL_INSERT_GRADE = "INSERT INTO grades (entrant_id, faculty_id, subject_id, value, exam_type_id) "
			+ "VALUES (?,?,?,?,?)";
	public static final String SQL_UPDATE_GRADE = "UPDATE users SET " + "grade=? WHERE id=?";
	public static final String SQL_DELETE_GRADE_BY_ID = "";
	public static final String SQL_FIND_ALL_GRADE_BY_ENTRANT_ID_AND_FACULTY_ID = "SELECT * FROM grades WHERE entrant_id=? and faculty_id=?";
	public static final String SQL_HAS_GRADES_BY_FACULTY_AND_ENTRANT_ID = "SELECT entrant_id, faculty_id FROM grades WHERE entrant_id=? "
			+ "AND faculty_id=? GROUP BY entrant_id";
	
	// standard constructor
	private GradeDAO() throws DBException {
		super();
	}

	// constructor constructor with the option not to use JNDI for Junit
	private GradeDAO(boolean isUseJNDI) throws DBException {
		super(isUseJNDI);
	}
	/**
	 * Extract grade from ResultSet
	 * @param ResultSet
	 * @return Grade
	 * @throws SQLException
	 */
	private Grade extract(ResultSet resultSet) throws SQLException {
	
		Grade grade = new Grade();
		grade.setEntrantId(resultSet.getInt(Fields.GRADES_ENTRANT_ID));
		grade.setFacultyId(resultSet.getInt(Fields.GRADES_FACULTY_ID));
		grade.setSubjectId(resultSet.getInt(Fields.GRADES_SUBJECT_ID));
		grade.setValue(resultSet.getInt(Fields.GRADES_VALUE));
		grade.setExamTypeId(resultSet.getInt(Fields.GRADES_EXAM_TYPE_ID));
		return grade;
	}
	
	// singleton pattern with use constructor with the option not to use JNDI for Junit 
	public static synchronized GradeDAO getInstance() throws DBException {
		if (instance == null) {
			instance = new GradeDAO();
		}
		return instance;
	}
	
	// singleton
	public static synchronized GradeDAO getInstance(boolean isUseJNDI) throws DBException {
		if (instance == null) {
			instance = new GradeDAO(isUseJNDI);
		}
		return instance;
	}
	
	/**
	 * Insert Grade
	 * @param grade
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean insert(Grade grade) throws DBException {
	
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
	
		try {
	
			con = getConnection();
			statement = con.prepareStatement(SQL_INSERT_GRADE, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
	
			statement.setInt(k++, grade.getEntrantId());
			statement.setInt(k++, grade.getFacultyId());
			statement.setInt(k++, grade.getSubjectId());
			statement.setInt(k++, grade.getValue());
			statement.setInt(k, grade.getExamTypeId());
	
			if (statement.executeUpdate() > 0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					grade.setId(resultSet.getInt(1));
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
	 * Insert all grade from list
	 * @param Grades
	 * @return result true or false
	 * @throws DBException
	 */
	public boolean insertAll(List<Grade> grades) throws DBException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;

		try {

			con = getConnection();
			con.setAutoCommit(false);
			statement = con.prepareStatement(SQL_INSERT_GRADE);
			int k = 1;

			for (Grade grade : grades) {
				k = 1;

				statement.setInt(k++, grade.getEntrantId());
				statement.setInt(k++, grade.getFacultyId());
				statement.setInt(k++, grade.getSubjectId());
				statement.setInt(k++, grade.getValue());
				statement.setInt(k, grade.getExamTypeId());

				result = statement.executeUpdate() > 0;

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
	 * Update grade
	 * @param grade
	 * @return
	 * @throws DBException
	 */
	public boolean update(Grade grade) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection con = null;
		boolean result = false;
	
		try {
	
			con = getConnection();
			statement = con.prepareStatement(SQL_UPDATE_GRADE, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
	
			statement.setInt(k++, grade.getValue());
			statement.setInt(k++, grade.getId());
	
			if (statement.executeUpdate() > 0) {
				LOG.trace("Grade with id " + grade.getId() + " was update");
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
	 * Return all grade by entrant and faculty
	 * @param entrantId
	 * @param facultyId
	 * @return
	 * @throws DBException
	 */
	public List<Grade> findAllByEntrantId(int entrantId, int facultyId) throws DBException {
		List<Grade> grades = new ArrayList<>();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_FIND_ALL_GRADE_BY_ENTRANT_ID_AND_FACULTY_ID);
			int k = 1;
			statement.setInt(k++, entrantId);
			statement.setInt(k++, facultyId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				grades.add(extract(resultSet));
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		} finally {
			close(con, statement, resultSet);
		}

		return grades;
	}
	
	/**
	 * Check if this entrant have grades for admission to this faculty
	 * @param facultyId
	 * @param entrantId
	 * @return result true or false
	 * @throws AppException
	 */
	public boolean hasGrades(int facultyId, int entrantId) throws AppException {
		boolean result = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			statement = con.prepareStatement(SQL_HAS_GRADES_BY_FACULTY_AND_ENTRANT_ID);
			int k = 1;
			statement.setInt(k++, entrantId);
			statement.setInt(k++, facultyId);
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
}
