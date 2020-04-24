package ua.nure.bratchun.summary_task4.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.Role;
import ua.nure.bratchun.summary_task4.db.dao.UserDAO;
import ua.nure.bratchun.summary_task4.db.entity.User;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.Command;


/**
 * Login command.
 * 
 * @author D.Bratchun
 * 
 */
public class LoginCommand extends Command {
	
	private static final long serialVersionUID = 8526935211035367501L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, 
			HttpMethod method) throws IOException, ServletException, AppException {
		
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
		return Path.PAGE_LOGIN;
	}
	
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException {

		HttpSession session = request.getSession();

		// obtain login and password from a request
		UserDAO userDAO = UserDAO.getInstance();
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			request.getSession().setAttribute("loginErrorMessage", "login_jsp.error.empty_form");
			return Path.COMMAND_VIEW_LOGIN_PAGE;
		}

		User user = userDAO.findByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			request.getSession().setAttribute("loginErrorMessage", "login_jsp.error.not_found");
			return Path.COMMAND_VIEW_LOGIN_PAGE;
		}

		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);
		
		String result = Path.COMMAND_LIST_FACULTY;

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		
		return result;
	}

}