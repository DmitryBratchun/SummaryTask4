package ua.nure.bratchun.summary_task4.web.command.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.Fields;
import ua.nure.bratchun.summary_task4.db.Role;
import ua.nure.bratchun.summary_task4.db.dao.EntrantDAO;
import ua.nure.bratchun.summary_task4.db.entity.Entrant;
import ua.nure.bratchun.summary_task4.db.validation.UserValidation;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;

/**
 * This command use for registration new client
 * @author D.Bratchun
 */
public class CommandRegistrationClient extends Command{
	
	private static final long serialVersionUID = -4925375690428615428L;
	private static final Logger LOG = Logger.getLogger(CommandRegistrationClient.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response,
			HttpMethod method) throws IOException, ServletException, AppException {
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
		return Path.PAGE_CLIENT_REGISRTATION;
	}
	
	private String doPost(HttpServletRequest request) throws DBException {

		String login = request.getParameter(Fields.USER_LOGIN);
		String password = request.getParameter(Fields.USER_PASSWORD);
		String email = request.getParameter(Fields.USER_EMAIL);
		String firstName = request.getParameter(Fields.USER_FIRST_NAME);
		String lastName = request.getParameter(Fields.USER_LAST_NAME);
		String city = request.getParameter(Fields.ENTRANTS_CITY);
		String region = request.getParameter(Fields.ENTRANTS_REGION);
		String school = request.getParameter(Fields.ENTRANTS_SCHOOL);
		String lang = request.getParameter(Fields.USER_LANG);
		
		if(!UserValidation.validationFields(login, password, firstName, lastName, email, lang)) {
			request.getSession().setAttribute(AttributeNames.REGISTRATION_ERROR_MESSAGE, "registration_jsp.error.incorrect_form");
			return Path.COMMAND_VIEW_REGISTRATION_PAGE;
		}
		
		if(!UserValidation.validationLogin(login)) {
			request.getSession().setAttribute(AttributeNames.REGISTRATION_ERROR_MESSAGE, "registration_jsp.error.no_uniq_login");
			return Path.COMMAND_VIEW_REGISTRATION_PAGE;
		}
		
		if(!UserValidation.checkUniquenessEmail(email)) {
			request.getSession().setAttribute(AttributeNames.REGISTRATION_ERROR_MESSAGE, "registration_jsp.error.no_uniq_email");
			return Path.COMMAND_VIEW_REGISTRATION_PAGE;
		}
		
		Entrant entrant = new Entrant();
		entrant.setLogin(login);
		entrant.setPassword(password);
		entrant.setEmail(email);
		entrant.setFirstName(firstName);
		entrant.setLastName(lastName);
		entrant.setCity(city);
		entrant.setRegion(region);
		entrant.setSchool(school);
		entrant.setLang(lang);
		entrant.setRoleId(Role.CLIENT.ordinal());
		
		EntrantDAO entrantDAO = EntrantDAO.getInstance();
		entrantDAO.insert(entrant);
		
		return Path.COMMAND_VIEW_LOGIN_PAGE;
	}
	
}
