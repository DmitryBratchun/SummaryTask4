package test.ua.nure.bratchun.summary_task4.db.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.nure.bratchun.summary_task4.db.dao.EntrantDAO;
import ua.nure.bratchun.summary_task4.db.entity.Entrant;
import ua.nure.bratchun.summary_task4.exception.DBException;

class EntrantDAOTest {
	
	private static Entrant entrant;
	private static EntrantDAO entrantDAO;

	@BeforeAll
	public static void setUpClass() throws DBException {
		BasicConfigurator.configure();
		entrant = new Entrant();
		entrant.setFirstName("testusername");
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
	}
	
	@AfterAll
	public static void tearDown() throws DBException {
		entrantDAO.deleteByLogin(entrant.getLogin());
	}
	
	
	@Test
	void testBlock() throws DBException {
		entrantDAO.blockById(entrant.getId());
		assertTrue(entrantDAO.findByLogin(entrant.getLogin()).isBlocked());
	}
	
	@Test
	void testUpdate() throws DBException {
		entrant.setCity("Kharkiv");
		entrantDAO.update(entrant);
		String newCity = entrantDAO.findByLogin(entrant.getLogin()).getCity();
		assertTrue(newCity.equals(entrant.getCity()));
	}
	
	@Test
	void testUnblock() throws DBException {
		entrantDAO.blockById(entrant.getId());
		assertTrue(entrantDAO.findByLogin(entrant.getLogin()).isBlocked());
		entrantDAO.unblockById(entrant.getId());	
		assertFalse(entrantDAO.findByLogin(entrant.getLogin()).isBlocked());
	}
	
	@Test
	void testFindAllNotEmptyReturn() throws DBException {
		List<Entrant> entrants = entrantDAO.findAll();
		assertNotNull(entrants);
		assertFalse(entrants.isEmpty());
	}

}
