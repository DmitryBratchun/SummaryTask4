package ua.nure.bratchun.SummaryTask4.web.command.admin.faculty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.Path;
import ua.nure.bratchun.SummaryTask4.db.dao.StatementDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Application;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.web.HttpMethod;
import ua.nure.bratchun.SummaryTask4.web.command.Command;

public class AddToStatementCommand extends Command{

	private static final long serialVersionUID = 1207072911411314240L;
	private static final Logger LOG = Logger.getLogger(AddToStatementCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = doPost(request, response);
		} else {
			result = null;
		}
		LOG.debug("Command finished");
		return result;
	}
	
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException {
		int facultyId = Integer.parseInt(request.getParameter("facultyId"));
		int entrantId = Integer.parseInt(request.getParameter("entrantId"));
		
		int page = 1;
		int lines = 10;
		if(request.getParameter("page")!=null ) {	
			page = Integer.parseInt(request.getParameter("page").replace("/D", ""));
		}
		if (page <1){
			page = 1;
		} 
		if(request.getParameter("lines")!=null) {	
			lines = Integer.parseInt(request.getParameter("lines").replace("/D", ""));
		}
		if (lines <1){
			lines = 10;
		}
		request.setAttribute("lines", lines);
		request.setAttribute("page", page);
		
		StatementDAO statementDAO = StatementDAO.getInstance();
		Application application = statementDAO.getApplicationByFacultyIdEntrantId(facultyId, entrantId);
		statementDAO.addApplicationToStatement(application);
		return Path.COMMAND_VIEW_FACULTY+"&facultyId=" + facultyId+"&page=" + page + "&lines=" + lines ;
	}

}
