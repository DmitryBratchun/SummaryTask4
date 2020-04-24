package ua.nure.bratchun.summary_task4.db.validation;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.db.dao.UserDAO;
import ua.nure.bratchun.summary_task4.exception.DBException;

/**
 * This class has methods for validating user fields.
 * @author D.Bratchun
 *
 */
public class UserValidation {
	private UserValidation() {}
	
	private static final String EMAIL_PATTERN = ".+@.+\\..+";
	
	private static final Logger LOG = Logger.getLogger(UserValidation.class);
	
	public static boolean validationFields(String login, String password,
			String firstName, String lastName, String email, String lang) {
		LOG.trace("Validation fields start fields");
		
		if(login == null || password == null || firstName==null || lastName == null) {
			return false;
		}
		
		if(login.length() < 2 || firstName.length() < 2 || 
				lastName.length() < 2 || password.length() <2) {
			return false;
		}
		
		if(email == null || lang == null) {
			return false;
		}
		
		if(!validationEmail(email)) {
			return false;
		}
		
		if(!"ru".equals(lang) && !"en".equals(lang)) {
			return false;
		}
		LOG.trace("Validation fields finish fields");
		return true;
	}
	
	public static boolean validationEmail(String email) {
		return email.matches(EMAIL_PATTERN);
		
	}
	
	public static boolean checkUniquenessEmail(String email) throws DBException {
		if(email == null) {
			return false;
		}
		UserDAO userDAO = UserDAO.getInstance();
		return !userDAO.hasEmail(email);
	}
	
	public static boolean validationLogin(String login) throws DBException {
		if(login == null) {
			return false;
		}
		UserDAO userDAO = UserDAO.getInstance();
		return !userDAO.hasLogin(login);
	}
}
