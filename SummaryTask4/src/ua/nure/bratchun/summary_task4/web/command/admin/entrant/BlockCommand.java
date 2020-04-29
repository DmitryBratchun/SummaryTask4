package ua.nure.bratchun.summary_task4.web.command.admin.entrant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.EntrantDAO;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;

/**
 * Block entrant
 * @author D.Bratchun
 *
 */
public class BlockCommand extends Command {

	private static final long serialVersionUID = 2667487622021951919L;
	private static final Logger LOG = Logger.getLogger(BlockCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;

		if (method == HttpMethod.POST) {
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
		if (request.getParameter(AttributeNames.PAGINATION_PAGE) != null) {
			page = Integer.parseInt(request.getParameter(AttributeNames.PAGINATION_PAGE).replace("/D", ""));
		}
		if (page < 1) {
			page = 1;
		}
		if (request.getParameter(AttributeNames.PAGINATION_LINES) != null) {
			lines = Integer.parseInt(request.getParameter(AttributeNames.PAGINATION_LINES).replace("/D", ""));
		}
		if (lines < 1) {
			lines = 10;
		}
		request.setAttribute(AttributeNames.PAGINATION_LINES, lines);
		request.setAttribute(AttributeNames.PAGINATION_PAGE, page);

		EntrantDAO entrantDAO = EntrantDAO.getInstance();
		entrantDAO.blockById(entrantId);
		return Path.COMMAND_VIEW_FACULTY + "&" + AttributeNames.FACULTY_ID + "=" + facultyId + "&"
				+ AttributeNames.PAGINATION_PAGE + "=" + page + "&" + AttributeNames.PAGINATION_LINES + "=" + lines;
	}
}
