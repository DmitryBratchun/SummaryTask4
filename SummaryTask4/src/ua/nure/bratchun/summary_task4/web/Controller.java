package ua.nure.bratchun.summary_task4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.CommandContainer;


/**
 * Main servlet controller.
 * 
 * @author D.Bratchun
 * 
 */
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 2423353715955164816L;

	private static final Logger LOG = Logger.getLogger(Controller.class);
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Try to execute GET command (" + request.getParameter(AttributeNames.COMMAND) + ")");
		String forward = process(request, response, HttpMethod.GET);
		request.getRequestDispatcher(forward).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Try to execute POST command (" + request.getParameter(AttributeNames.COMMAND) + ")");
		String redirect = process(request, response, HttpMethod.POST);
		response.sendRedirect(redirect);
		
	}

	/**
	 * Main method of this controller.
	 */
	private String process(HttpServletRequest request,
			HttpServletResponse response, HttpMethod method) throws IOException, ServletException {
		
		LOG.debug("Controller starts");

		// extract command name from the request
		String commandName = request.getParameter(AttributeNames.COMMAND);
		LOG.trace("Request parameter: command --> " + commandName);

		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);

		// execute command and get forward address
		String forward = null;
		if(method == HttpMethod.GET) {
			forward = Path.PAGE_ERROR;
		} else {
			forward = Path.COMMAND_VIEW_ERROR_PAGE;
		}
		try {
			forward = command.execute(request, response, method);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}
		LOG.trace("Forward address --> " + forward);

		LOG.debug("Controller finished, now go to forward address --> " + forward);
		
		return forward;
	}
}