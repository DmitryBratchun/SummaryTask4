package test.ua.nure.bratchun.SummaryTask4.db.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.nure.bratchun.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Faculty;
import ua.nure.bratchun.SummaryTask4.exception.DBException;

class FacultyDAOTest {
	
	private static Faculty faculty;
	private static FacultyDAO facultyDAO;
	
	@BeforeAll
	public static void setUpClass() throws Exception {
		BasicConfigurator.configure();
		faculty = new Faculty();
		faculty.setBudgetPlaces(3);
		faculty.setTotalPlaces(10);
		faculty.setNameEn("testJunit");
		faculty.setNameRu("тестёнит");
		
		facultyDAO = FacultyDAO.getInstance(false);
		facultyDAO.insert(faculty);
	}
	@AfterAll
	public static void tearDown() throws DBException {
		facultyDAO.deleteByID(faculty.getId());
	}

	@Test
	void testUpdate() throws DBException {
		String oldNameRu = faculty.getNameRu();
		String oldNameEn = faculty.getNameEn();
		String newNameRu = "тестёнит2";
		String newNameEn ="testJunit2";
		int newBudget = 2;
		int newTotal = 9;
		int oldBudget = faculty.getBudgetPlaces();
		int oldTotal = faculty.getTotalPlaces();
		faculty.setBudgetPlaces(newBudget);
		faculty.setTotalPlaces(newTotal);
		faculty.setNameEn(newNameEn);
		faculty.setNameRu(newNameRu);
		
		facultyDAO.update(faculty);
		
		Faculty facultyFromDB = facultyDAO.findById(faculty.getId());
		assertTrue(facultyFromDB.getBudgetPlaces() == newBudget);
		assertTrue(facultyFromDB.getTotalPlaces() == newTotal);
		assertTrue(newNameEn.equals(facultyFromDB.getNameEn()));
		assertTrue(newNameRu.equals(facultyFromDB.getNameRu()));
		faculty.setBudgetPlaces(oldBudget);
		faculty.setTotalPlaces(oldTotal);
		faculty.setNameEn(oldNameEn);
		faculty.setNameRu(oldNameRu);
		facultyDAO.update(faculty);
	}
	
	@Test
	void testHasFacultyName( ) throws DBException {
		assertTrue(facultyDAO.hasFacultyName(faculty.getNameEn()));
		assertTrue(facultyDAO.hasFacultyName(faculty.getNameRu()));
	}
	
	@Test 
	void testFindAllNotNull() throws DBException {
		assertNotNull(facultyDAO.findAll());
	}

}
