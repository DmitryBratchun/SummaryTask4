package ua.nure.bratchun.summary_task4.web.command.admin.faculty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.StatementDAO;
import ua.nure.bratchun.summary_task4.db.entity.Application;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;

/**
 * Add application to statement
 * @author D.Bratchun
 *
 */
public class AddToStatementCommand extends Command{

	private static final long serialVersionUID = 1207072911411314240L;
	private static final Logger LOG = Logger.getLogger(AddToStatementCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = doPost(request);
		} else {
			result = null;
		}
		LOG.debug("Command finished");
		return result;
	}
	
	private String doPost(HttpServletRequest request) throws AppException {
		int facultyId = Integer.parseInt(request.getParameter(AttributeNames.FACULTY_ID));
		int entrantId = Integer.parseInt(request.getParameter(AttributeNames.ENTRANT_ID));
		
		int page = 1;
		int lines = 10;
		if(request.getParameter(ParameterNames.PAGINATION_PAGE)!=null ) {	
			page = Integer.parseInt(request.getParameter(AttributeNames.PAGINATION_PAGE).replace("/D", ""));
		}
		if (page <1){
			page = 1;
		} 
		if(request.getParameter(ParameterNames.PAGINATION_LINES)!=null) {	
			lines = Integer.parseInt(request.getParameter(AttributeNames.PAGINATION_LINES).replace("/D", ""));
		}
		if (lines <1){
			lines = 10;
		}
		request.setAttribute(AttributeNames.PAGINATION_LINES, lines);
		request.setAttribute(AttributeNames.PAGINATION_PAGE, page);
		
		StatementDAO statementDAO = StatementDAO.getInstance();
		Application application = statementDAO.getApplicationByFacultyIdEntrantId(facultyId, entrantId);
		statementDAO.addApplicationToStatement(application);
		return Path.COMMAND_VIEW_FACULTY+"&"+ AttributeNames.FACULTY_ID+"=" + facultyId+"&"+ AttributeNames.PAGINATION_PAGE +"=" + page + "&" + AttributeNames.PAGINATION_LINES + "=" + lines ;
	}

}
