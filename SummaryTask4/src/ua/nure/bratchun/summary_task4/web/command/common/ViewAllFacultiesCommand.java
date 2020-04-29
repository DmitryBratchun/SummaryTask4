package ua.nure.bratchun.summary_task4.web.command.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.FacultyDAO;
import ua.nure.bratchun.summary_task4.db.entity.Faculty;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;

/**
 * View all faculties
 * 
 * @author D.Bratchun
 *
 */
public class ViewAllFacultiesCommand extends Command {

	private static final long serialVersionUID = 9188012295034506682L;
	private static final Logger LOG = Logger.getLogger(ViewAllFacultiesCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Start executing Command");
		String result = null;
		result = doGet(request);
		LOG.debug("Finished executing Command");
		return result;
	}

	private String doGet(HttpServletRequest request) throws DBException {
		FacultyDAO facultyDAO = FacultyDAO.getInstance();
		List<Faculty> faculties = null;

		int page = 1;
		int lines = 10;
		if (request.getParameter(ParameterNames.PAGINATION_PAGE) != null) {
			page = Integer.parseInt(request.getParameter(ParameterNames.PAGINATION_PAGE).replace("/D", ""));
		}
		if (page < 1) {
			page = 1;
		}
		if (request.getParameter(ParameterNames.PAGINATION_LINES) != null) {
			lines = Integer.parseInt(request.getParameter(ParameterNames.PAGINATION_LINES).replace("/D", ""));
		}
		if (lines < 1) {
			lines = 10;
		}

		String orderBy = request.getParameter(ParameterNames.ORDER_BY) == null ? "id"
				: request.getParameter(ParameterNames.ORDER_BY);
		String direction = request.getParameter(ParameterNames.DIRECTION) == null ? "DESC"
				: request.getParameter(ParameterNames.DIRECTION);

		faculties = facultyDAO.findAllOrderBy(orderBy, direction, (page - 1) * lines, lines);

		while (faculties.isEmpty() && page > 1) {
			page--;
			faculties = facultyDAO.findAllOrderBy(orderBy, direction, (page - 1) * lines, lines);
		}
		request.setAttribute(AttributeNames.PAGINATION_LINES, lines);
		request.setAttribute(AttributeNames.PAGINATION_PAGE, page);
		request.setAttribute(AttributeNames.FACULTIES, faculties);

		return Path.PAGE_LIST_ALL_FACULTIES;
	}

}
