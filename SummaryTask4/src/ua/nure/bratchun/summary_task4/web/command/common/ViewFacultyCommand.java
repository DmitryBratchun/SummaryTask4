package ua.nure.bratchun.summary_task4.web.command.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.Role;
import ua.nure.bratchun.summary_task4.db.dao.FacultyDAO;
import ua.nure.bratchun.summary_task4.db.dao.StatementDAO;
import ua.nure.bratchun.summary_task4.db.dao.SubjectDAO;
import ua.nure.bratchun.summary_task4.db.entity.Application;
import ua.nure.bratchun.summary_task4.db.entity.Faculty;
import ua.nure.bratchun.summary_task4.db.entity.Subject;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;
/**
 * View faculty
 * @author D.Bratchun
 *
 */
public class ViewFacultyCommand extends Command{

	private static final long serialVersionUID = 3511501137528097203L;
	private static final Logger LOG = Logger.getLogger(ViewFacultyCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Start executing Command");
		String result = null;
		result = doGet(request);
		LOG.debug("Finished executing Command");
		return result;
	}
	
	private String doGet(HttpServletRequest request) throws AppException {
		int facultyId = Integer.parseInt(request.getParameter(ParameterNames.FACULTY_ID));
		StatementDAO statementDAO = StatementDAO.getInstance();
		
		Faculty faculty = null;
		try {	
			FacultyDAO facultyDAO = FacultyDAO.getInstance();
			faculty = facultyDAO.findById(facultyId);
			
			request.setAttribute(AttributeNames.FACULTY, faculty);
			LOG.debug("request faculty " + faculty);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_GET_FACULTY, e);
		}
		
		
		String result;
		// Admin page
		if(request.getSession().getAttribute(AttributeNames.USER_ROLE) == Role.ADMIN) {
			if(statementDAO.hasStatementResult(facultyId)) {
				return Path.COMMAND_VIEW_STATEMENT;
			}
			//pagination
			int page = 1;
			int lines = 10;
			if(request.getParameter(ParameterNames.PAGINATION_PAGE)!=null ) {	
				page = Integer.parseInt(request.getParameter(ParameterNames.PAGINATION_PAGE).replace("/D", ""));
			}
			if (page <1){
				page = 1;
			} 
			if(request.getParameter(ParameterNames.PAGINATION_LINES)!=null) {	
				lines = Integer.parseInt(request.getParameter(ParameterNames.PAGINATION_LINES).replace("/D", ""));
			}
			if (lines <1){
				lines = 10;
			}
			
			List<Application> applications = statementDAO.getApplicationByFacultyId(facultyId, (page-1)*lines ,lines);
			while(applications.isEmpty() && page>1) {
				page--;
				applications = statementDAO.getApplicationByFacultyId(facultyId, (page-1)*lines ,lines);
			}
			request.setAttribute(AttributeNames.PAGINATION_LINES, lines);
			request.setAttribute(AttributeNames.PAGINATION_PAGE, page);
			request.setAttribute(AttributeNames.APPLICATIONS, applications);
			
			result = Path.PAGE_FACULTY_ENTRANTS;
			
		// Client page	
		} else {
			
			List<Subject> preliminarySubjects = null;
			List<Subject> diplomaSubjects = null;
			try {	
				SubjectDAO subjectDAO = SubjectDAO.getInstance();
				diplomaSubjects = subjectDAO.findAll();
				preliminarySubjects = subjectDAO.getSubjectsByFacultyId(facultyId);
			} catch (DBException e) {
				LOG.error(Messages.ERR_CANNOT_GET_SUBJECTS, e);
				throw new AppException(Messages.ERR_CANNOT_GET_SUBJECTS, e);
			}
			
			request.setAttribute(AttributeNames.DIPLOMA_SUBJECTS, diplomaSubjects);
			LOG.debug("request diploma subjects " + diplomaSubjects);
			
			request.setAttribute(AttributeNames.PRELIMINARY_SUBJECTS, preliminarySubjects);
			LOG.debug("request preliminary subjects " + preliminarySubjects);
			
			result = Path.PAGE_ENTRY_FACULTY;
		}
		return result;
	}
}
