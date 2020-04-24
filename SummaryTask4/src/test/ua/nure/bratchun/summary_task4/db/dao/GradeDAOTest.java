package test.ua.nure.bratchun.summary_task4.db.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.nure.bratchun.summary_task4.db.dao.EntrantDAO;
import ua.nure.bratchun.summary_task4.db.dao.FacultyDAO;
import ua.nure.bratchun.summary_task4.db.dao.GradeDAO;
import ua.nure.bratchun.summary_task4.db.dao.SubjectDAO;
import ua.nure.bratchun.summary_task4.db.entity.Entrant;
import ua.nure.bratchun.summary_task4.db.entity.Faculty;
import ua.nure.bratchun.summary_task4.db.entity.Grade;
import ua.nure.bratchun.summary_task4.db.entity.Subject;
import ua.nure.bratchun.summary_task4.exception.DBException;

class GradeDAOTest {
	
	private static Faculty faculty;
	private static FacultyDAO facultyDAO;
	
	private static Entrant entrant;
	private static EntrantDAO entrantDAO;
	
	private static SubjectDAO subjectDAO;
	private static Subject subject;
	
	private static GradeDAO gradeDAO;
	private static Grade grade;
	
	@BeforeAll
	public static void setUpClass() throws DBException {
		BasicConfigurator.configure();
		
		entrant = new Entrant();
		entrant.setFirstName("testuser");
		entrant.setLogin("testuser");
		entrant.setLastName("testuser");
		entrant.setEmail("testuser@gmail.com");
		entrant.setPassword("1234");
		entrant.setRoleId(0);
		entrant.setLang("ru");
		entrant.setCity("---");
		entrant.setRegion("---");
		entrant.setSchool("---");
		entrantDAO = EntrantDAO.getInstance(false);
		entrantDAO.insert(entrant);
		
		faculty = new Faculty();
		faculty.setBudgetPlaces(3);
		faculty.setTotalPlaces(10);
		faculty.setNameEn("testJunit");
		faculty.setNameRu("тестёнит");
		facultyDAO = FacultyDAO.getInstance(false);
		facultyDAO.insert(faculty);
		
		subject = new Subject();
		subject.setNameEn("TestJunit");
		subject.setNameRu("тестёнит");
		subjectDAO = SubjectDAO.getInstance(false);
		subjectDAO.insert(subject);
		
		grade = new Grade();
		grade.setEntrantId(entrant.getId());
		grade.setExamTypeId(0);
		grade.setFacultyId(faculty.getId());
		grade.setSubjectId(subject.getId());
		grade.setValue(5);
		gradeDAO = GradeDAO.getInstance(false);
		gradeDAO.insert(grade);
	}
	
	@AfterAll
	public static void tearDown() throws DBException {
		entrantDAO.deleteByLogin(entrant.getLogin());
		subjectDAO.delete(subject.getId());
		facultyDAO.deleteByID(faculty.getId());
		
	}

	@Test
	void testGetGradeNotNull() throws DBException {
		assertNotNull(gradeDAO.findAllByEntrantId(entrant.getId(), faculty.getId()));
	}

}
