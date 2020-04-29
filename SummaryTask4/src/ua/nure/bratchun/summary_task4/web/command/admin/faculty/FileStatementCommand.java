package ua.nure.bratchun.summary_task4.web.command.admin.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.EntryType;
import ua.nure.bratchun.summary_task4.db.dao.FacultyDAO;
import ua.nure.bratchun.summary_task4.db.dao.StatementDAO;
import ua.nure.bratchun.summary_task4.db.entity.Application;
import ua.nure.bratchun.summary_task4.db.entity.Faculty;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;
import ua.nure.bratchun.summary_task4.web.mail.Sender;

/**
 * File statement command
 * @author D.Bratchun
 *
 */
public class FileStatementCommand extends Command {
	
	private static final String ADMISSIONS_EMAIL = "summary.task.4bratchun@gmail.com";
	private static Sender sender = new Sender(ADMISSIONS_EMAIL, "1qaz*2wsx"); 

	private static final Logger LOG = Logger.getLogger(FileStatementCommand.class);
	private static final long serialVersionUID = -5493384318363403045L;

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
	
	public String doPost(HttpServletRequest request) throws AppException {
		StatementDAO statementDAO = StatementDAO.getInstance();
		int facultyId = Integer.parseInt(request.getParameter(ParameterNames.FACULTY_ID));
		if(statementDAO.hasStatementResult(facultyId)) {
			return Path.COMMAND_VIEW_STATEMENT + "&" + AttributeNames.FACULTY_ID + "=" + facultyId;
		}
		
		FacultyDAO facultyDAO = FacultyDAO.getInstance();
		Faculty faculty = facultyDAO.findById(facultyId);
		int budget = faculty.getBudgetPlaces();
		int total = faculty.getTotalPlaces();
		
		
		List<Application> applications = statementDAO.getApplicationFromStatement(facultyId);
		
		for(Application application : applications) {
			if(application.getIsBlocked()) {
				application.setEntryTypeId(EntryType.NOT_INCLUDED_IN_THE_STATEMENT.ordinal());
			}else if(budget > 0) {
				application.setEntryTypeId(EntryType.BUDGET.ordinal());
				budget--;
				total--;
			} else if (total > 0) {
				application.setEntryTypeId(EntryType.CONTRACT.ordinal());
				total--;
			} else {
				application.setEntryTypeId(EntryType.FAILED.ordinal());
			}
		}
		LOG.debug("Started editing statement");
		statementDAO.updateApplications(applications);
		LOG.debug("Finished editing statement");
		
		LOG.trace("Started sending emails");
		reportResults(applications);
		LOG.trace("Finished sending emails");
		return Path.COMMAND_LIST_FACULTY;
	}
	
	private static void reportResults(List<Application> applications) throws AppException {
		String lang;
		for(Application application: applications) {
			
			lang = application.getLang();
			
			switch (lang) {
			case "ru":
				reportResultRu(application);
				break;
			case "en":
				reportResultEn(application);
				break;
			default:
				break;
			}	
		}
	}
	
	/**
	 * Send message with result to users by English
	 * @param application
	 * @throws AppException
	 */
	private static void reportResultEn(Application application) throws AppException {
		String title = "Admission results";
		FacultyDAO facultyDAO = FacultyDAO.getInstance();
		Faculty faculty = facultyDAO.findById(application.getFacultyId());
		String facultyName = faculty.getNameEn();
		
		String fullName;
		String email;
		
		String entryType = null;
		email = application.getEmail();
		fullName = application.getFirstName() + " " + application.getLastName();
		
		switch (application.getEntryTypeId()) {
		case 2:
			entryType = "was enrolled in a budget form of study";
			break;
		case 3:
			entryType = "was enrolled in a paid form of study";
			break;
		case 4:
			entryType = "unfortunately failed the opening company";
			break;

		default:
			entryType ="not considered as a candidate";
			break;
		}
		String text = "For the results of admission to the faculty \""
		+ facultyName + "\". " + fullName + " " + entryType + ".";
		
		sender.send(title, text, ADMISSIONS_EMAIL, email);
	}
	
	/**
	 * Send message with result to users by Russian
	 * @param application
	 * @throws AppException
	 */
	private static void reportResultRu(Application application) throws AppException {
		String title = "Результаты вступительной компании";
		FacultyDAO facultyDAO = FacultyDAO.getInstance();
		Faculty faculty = facultyDAO.findById(application.getFacultyId());
		String facultyName = faculty.getNameRu();
		
		String fullName;
		String email;
		
		String entryType = null;
		email = application.getEmail();
		fullName = application.getFirstName() + " " + application.getLastName();
		
		switch (application.getEntryTypeId()) {
		case 2:
			entryType = "был зачислен на бюджетную форму обучения";
			break;
		case 3:
			entryType = "был зачислен на платную форму обучения";
			break;
		case 4:
			entryType = "к сожалению, провалил вступительную кампанию";
			break;

		default:
			entryType ="не рассматривался как кандидат";
			break;
		}
		String text = "За результатами вступительной кампании на факультет \""
		+ facultyName + "\". " + fullName + " " + entryType + ".";
		
		sender.send(title, text, ADMISSIONS_EMAIL, email);
	}
}
