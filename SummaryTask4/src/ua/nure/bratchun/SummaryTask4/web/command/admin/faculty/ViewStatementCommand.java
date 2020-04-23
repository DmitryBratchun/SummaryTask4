package ua.nure.bratchun.SummaryTask4.web.command.admin.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.Path;
import ua.nure.bratchun.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bratchun.SummaryTask4.db.dao.StatementDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Application;
import ua.nure.bratchun.SummaryTask4.db.entity.Faculty;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.exception.DBException;
import ua.nure.bratchun.SummaryTask4.exception.Messages;
import ua.nure.bratchun.SummaryTask4.web.HttpMethod;
import ua.nure.bratchun.SummaryTask4.web.command.Command;

public class ViewStatementCommand extends Command{

	private static final long serialVersionUID = -7102535496614062286L;
	private static final Logger LOG = Logger.getLogger(ViewStatementCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = null;
		} else {
			result = doGet(request, response);
		}
		LOG.debug("Command finished");
		return result;
	}
	
	public String doGet (HttpServletRequest request, HttpServletResponse response) throws AppException {
		
		int page = 1;
		int lines = 10;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page").replace("/D", ""));
		}
		if (page < 1) {
			page = 1;
		}
		if (request.getParameter("lines") != null) {
			lines = Integer.parseInt(request.getParameter("lines").replace("/D", ""));
		}
		if (lines < 1) {
			lines = 10;
		}
		
		int facultyId = Integer.parseInt(request.getParameter("facultyId"));
		Faculty faculty = null;
		try {	
			FacultyDAO facultyDAO = FacultyDAO.getInstance();
			faculty = facultyDAO.findById(facultyId);
			
			request.setAttribute("faculty", faculty);
			LOG.debug("request faculty " + faculty);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_GET_FACULTY, e);
		}
		
		StatementDAO statementDAO = StatementDAO.getInstance();
		List<Application> applications = statementDAO.getApplicationFromStatement(facultyId, (page - 1) * lines, lines);
		while (applications.isEmpty() && page > 1) {
			page--;
			applications = statementDAO.getApplicationFromStatement(facultyId, (page - 1) * lines, lines);
		}
		request.setAttribute("lines", lines);
		request.setAttribute("page", page);
		request.setAttribute("applications", applications);
		
		
		return Path.PAGE_VIEW_STATEMENT;
	}

}
