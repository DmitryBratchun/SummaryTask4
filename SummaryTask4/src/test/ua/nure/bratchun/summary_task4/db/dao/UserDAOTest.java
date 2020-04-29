package test.ua.nure.bratchun.summary_task4.db.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.nure.bratchun.summary_task4.db.dao.UserDAO;
import ua.nure.bratchun.summary_task4.db.entity.User;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
/**
 *	Test userDAO
 */
class UserDAOTest {
	
	private static User user;
	private static UserDAO userDAO;

	@BeforeAll
	public static void setUpClass() throws DBException  {
		BasicConfigurator.configure();
		user = new User();
		user.setFirstName("testusername");
		user.setLogin("testuser");
		user.setLastName("testuser");
		user.setEmail("testuser@gmail.com");
		user.setPassword("1234");
		user.setRoleId(0);
		user.setLang("ru");
		 
		userDAO = UserDAO.getInstance(false);
		userDAO.insert(user);
		
	}
	@AfterAll
	public static void tearDown() throws DBException {
		userDAO.deleteByLogin(user.getLogin());
	}
	
	@Test
	public void testFindByEmail() throws DBException {
		assertThat(userDAO.findByEmail(user.getEmail()).getLogin(), equalTo(user.getLogin()));
	}
	
	@Test
	void testFindByLogin() throws DBException {
		assertTrue(user.equals(userDAO.findByLogin(user.getLogin())));
	}
	
	@Test
	void testInsertNull() throws DBException {
		assertFalse(userDAO.insert(null));
	}
	
	@Test
	public void testUpdate() throws DBException {
		user.setEmail("update@gmail.com");
		userDAO.update(user);
		assertTrue(userDAO.hasLogin(user.getLogin()));
	}
	@Test
	public void testGetIdByLogin() throws DBException {
		assertThat(user.getId(), equalTo(userDAO.getIdByLogin(user.getLogin())));
	}
	
	@Test
	public void testhasLogin() throws AppException {
		assertTrue(userDAO.hasLogin("admin"));
	}
	@Test
	public void testDelete() throws DBException {
		User userDelete = new User();
		userDelete.setFirstName("testDeleteUserName");
		userDelete.setLogin("testDeleteUser");
		userDelete.setLastName("testDeleteUser");
		userDelete.setEmail("testdeleteuser@gmail.com");
		userDelete.setPassword("1234");
		userDelete.setRoleId(0);
		userDelete.setLang("ru");
		userDAO.insert(userDelete);	
		userDAO.deleteByLogin(userDelete.getLogin());
		assertFalse(userDAO.hasLogin(userDelete.getLogin()));
	}
	@Test
	void testHasEmail() throws DBException {
		assertTrue(userDAO.hasEmail(user.getEmail()));
	}
}
