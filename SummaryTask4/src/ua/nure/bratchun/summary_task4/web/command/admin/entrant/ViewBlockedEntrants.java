package ua.nure.bratchun.summary_task4.web.command.admin.entrant;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.EntrantDAO;
import ua.nure.bratchun.summary_task4.db.entity.Entrant;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;

/**
 * View all blocked entrants
 * @author D.Bratchun
 *
 */
public class ViewBlockedEntrants extends Command{

	private static final long serialVersionUID = 6073244272734332926L;
	private static final Logger LOG = Logger.getLogger(ViewBlockedEntrants.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = null;
		} else {
			result = doGet(request);
		}
		LOG.debug("Command finished");
		return result;
	}

	private String doGet(HttpServletRequest request) throws AppException {
			// pagination
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
			EntrantDAO entrantDAO = EntrantDAO.getInstance();
			List<Entrant> entrants = entrantDAO.findAllBlocked( (page - 1) * lines, lines);
			while (entrants.isEmpty() && page > 1) {
				page--;
				entrants = entrantDAO.findAllBlocked((page - 1) * lines, lines);
			}
			request.setAttribute(AttributeNames.PAGINATION_LINES, lines);
			request.setAttribute(AttributeNames.PAGINATION_PAGE, page);
			request.setAttribute(AttributeNames.ENTRANTS, entrants);

			return Path.PAGE_VIEW_BLOCKED_ENTRANTS;
	}
}
