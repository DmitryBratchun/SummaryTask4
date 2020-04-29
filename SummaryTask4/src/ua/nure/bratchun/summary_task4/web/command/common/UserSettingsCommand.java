package ua.nure.bratchun.summary_task4.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.UserDAO;
import ua.nure.bratchun.summary_task4.db.entity.User;
import ua.nure.bratchun.summary_task4.db.validation.UserValidation;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;

/**
 * User settings
 * @author D.Bratchun
 *
 */
public class UserSettingsCommand extends Command{

	private static final long serialVersionUID = -6336511588952034804L;
	private static final Logger LOG = Logger.getLogger(UserSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Start executing Command");
		String result = null;
		if(method == HttpMethod.POST) {
			result = doPost(request);
		} else {
			result = doGet();
		}
		LOG.debug("Finished executing Command");
		return result;
	}
	
	private String doGet() {
		return Path.PAGE_USER_SETTINGS;
	}
	
	private String doPost(HttpServletRequest request) throws DBException {
		String result = null;
		
		HttpSession session = request.getSession();
		User user = null;
		UserDAO userDAO = UserDAO.getInstance(); 
		String newPassword = request.getParameter(ParameterNames.NEW_PASSWORD);
		String newEmail = request.getParameter(ParameterNames.NEW_EMAIL);
		String newLand = request.getParameter(ParameterNames.LANG);
		LOG.debug("User try to change his password to (" + newPassword + ") and email to (" + newEmail + ")");
		if((user=(User) session.getAttribute(AttributeNames.USER)) == null) {
			return Path.COMMAND_VIEW_LOGIN_PAGE;
		}
		if(!newPassword.isEmpty() && newPassword.length() < 2) {
			session.setAttribute(AttributeNames.USER_SETTINGS_ERROR_MESSAGE, "user.settings_jsp.error.incorrect_password");
			return Path.COMMAND_SETTINGS_USER;
		}
		
		if(!newEmail.isEmpty() && !UserValidation.validationEmail(newEmail)) {
			session.setAttribute(AttributeNames.USER_SETTINGS_ERROR_MESSAGE, "user.settings_jsp.error.incorrect_email");
			return Path.COMMAND_SETTINGS_USER;
		}
		if(!newEmail.isEmpty() && userDAO.hasEmail(newEmail)) {
			session.setAttribute(AttributeNames.USER_SETTINGS_ERROR_MESSAGE, "user.settings_jsp.error.no_uniq_email");
			return Path.COMMAND_SETTINGS_USER;
		}
		if(!newEmail.isEmpty()) {
			user.setEmail(newEmail);
		}
		if(!newPassword.isEmpty()) {
			user.setPassword(newPassword);
		}
		user.setLang(newLand);
		userDAO.update(user);
		
		if(user.getRoleId() == 0) {
			result = Path.COMMAND_VIEW_PAGE_ADMIN;
		} else {
			result = Path.COMMAND_VIEW_PAGE_CLIENT;
		}
		
		return result;
	}
	
}
