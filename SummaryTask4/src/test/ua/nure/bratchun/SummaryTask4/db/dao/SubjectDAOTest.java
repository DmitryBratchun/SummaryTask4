package test.ua.nure.bratchun.SummaryTask4.db.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.nure.bratchun.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Subject;
import ua.nure.bratchun.SummaryTask4.exception.DBException;

class SubjectDAOTest {
	private static SubjectDAO subjectDAO;
	private static Subject subject;
	
	@BeforeAll
	public static void setUpClass() throws Exception {
		BasicConfigurator.configure();
		subjectDAO = SubjectDAO.getInstance(false);
		subject = new Subject();
		subject.setNameEn("TestJunit");
		subject.setNameRu("тестёнит");
		subjectDAO.insert(subject);
	}
	@AfterAll
	public static void tearDown() throws DBException {
		subjectDAO.delete(subject.getId());
	}
	
	@Test
	void testHasName() throws DBException {
		assertTrue(subjectDAO.hasName(subject.getNameEn()));
		assertTrue(subjectDAO.hasName(subject.getNameRu()));
	}
	
	@Test
	void testUpdate() throws DBException {
		String oldNameRu = subject.getNameRu();
		String newNameRu = "тестёнит2";
		String oldNameEn = subject.getNameEn();
		String newNameEn = "testJunit2";
		subject.setNameEn(newNameEn);
		subject.setNameRu(newNameRu);
		subjectDAO.update(subject);
		assertFalse(subjectDAO.hasName(oldNameRu));
		assertFalse(subjectDAO.hasName(oldNameEn));
		assertTrue(subjectDAO.hasName(subject.getNameEn()));
		assertTrue(subjectDAO.hasName(subject.getNameRu()));
		subject.setNameEn(oldNameEn);
		subject.setNameRu(oldNameRu);
		subjectDAO.update(subject);
		assertFalse(subjectDAO.hasName(newNameEn));
		assertFalse(subjectDAO.hasName(newNameRu));
		assertTrue(subjectDAO.hasName(subject.getNameEn()));
		assertTrue(subjectDAO.hasName(subject.getNameRu()));
	}
	
	@Test
	void testNotNullFindAll() throws DBException {
		assertNotNull(subjectDAO.findAll());
	}
}
