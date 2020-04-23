package ua.nure.bratchun.SummaryTask4.db.validation;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.exception.DBException;
import ua.nure.bratchun.SummaryTask4.exception.Messages;

public class SubjectValidation {
	private static final Logger LOG = Logger.getLogger(SubjectValidation.class);

	public static final String REGULAR_EXPRESSION_NAME_EN = "[A-Za-z0-9\\s]+";
	public static final String REGULAR_EXPRESSION_NAME_RU = "[Р-пр-џ0-9ИЈ\\s]+";
	
	public static boolean validationNameRu(String nameRu) {
		return !nameRu.isEmpty() && nameRu.matches(REGULAR_EXPRESSION_NAME_RU);
	}
	
	public static boolean validationNameEn(String nameEn) {
		return !nameEn.isEmpty() && nameEn.matches(REGULAR_EXPRESSION_NAME_EN);
	}
	
	public static boolean hasName(String name) throws AppException {
		boolean result = true;
		try {
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			result = subjectDAO.hasName(name);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new AppException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		}
		return result;
	}
}
