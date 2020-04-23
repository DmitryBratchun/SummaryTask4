package ua.nure.bratchun.SummaryTask4.db.validation;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.exception.DBException;
import ua.nure.bratchun.SummaryTask4.exception.Messages;

/**
 * This class has methods for validating faculty fields.
 * @author D.Bratchun
 *
 */
public class FacultyValidation {
	
	private static final Logger LOG = Logger.getLogger(FacultyValidation.class);

	public static final String REGULAR_EXPRESSION_NAME_EN = "[A-Za-z0-9\\s]+";
	public static final String REGULAR_EXPRESSION_NAME_RU = "[Р-пр-џ0-9ИЈ\\s]+";
	
	private FacultyValidation() {}
	
	public static boolean validationNameRu(String nameRu) {
		return !nameRu.isEmpty() && nameRu.matches(REGULAR_EXPRESSION_NAME_RU);
	}
	
	public static boolean validationNameEn(String nameEn) {
		return !nameEn.isEmpty() && nameEn.matches(REGULAR_EXPRESSION_NAME_EN);
	}
	
	public static boolean hasName(String name) throws AppException {
		boolean result = true;
		try {
			FacultyDAO facultyDAO = FacultyDAO.getInstance();
			result = facultyDAO.hasFacultyName(name);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new AppException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		}
		return result;
	}
	
	public static boolean validationPlaces(int total, int budget) {
		return total>=budget && total>0 && budget>=0;
	}
}
