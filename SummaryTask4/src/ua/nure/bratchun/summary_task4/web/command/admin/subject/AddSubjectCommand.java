package ua.nure.bratchun.summary_task4.web.command.admin.subject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.SubjectDAO;
import ua.nure.bratchun.summary_task4.db.entity.Subject;
import ua.nure.bratchun.summary_task4.db.validation.SubjectValidation;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.Command;

public class AddSubjectCommand extends Command{

	private static final long serialVersionUID = -4988687434581367117L;
	private static final Logger LOG = Logger.getLogger(AddSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = doPost(request, response);
		} else {
			result = doGet(request, response);
		}
		LOG.debug("Command finished");
		return result;
	}
	
	private String doGet(HttpServletRequest request, HttpServletResponse response) {
		return Path.PAGE_ADD_SUBJECT;
	}
	
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException {
		String nameRu = request.getParameter("nameRu");
		String nameEn = request.getParameter("nameEn");
		
		if(!SubjectValidation.validationNameEn(nameEn) || !SubjectValidation.validationNameRu(nameRu)) {
			request.getSession().setAttribute("addSubjectErrorMessage", "admin.subjects.add_subject_jsp.error.incorrect_name");
			return Path.COMMAND_ADD_SUBJECT;
		}
		
		if(SubjectValidation.hasName(nameEn) || SubjectValidation.hasName(nameRu)) {
			request.getSession().setAttribute("addSubjectErrorMessage", "admin.subjects.add_subject_jsp.error.no_unique_name");
			return Path.COMMAND_ADD_SUBJECT;
		}
		try {
			Subject subject = new Subject(nameRu, nameEn);
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			subjectDAO.insert(subject);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_SUBJECT, e);
			throw new AppException(Messages.ERR_CANNOT_INSERT_SUBJECT, e);
		}
		
		return Path.COMMAND_VIEW_PAGE_ADMIN;
	}
	

}
