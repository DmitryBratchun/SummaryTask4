package ua.nure.bratchun.summary_task4.web.command.admin.faculty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.FacultyDAO;
import ua.nure.bratchun.summary_task4.db.dao.SubjectDAO;
import ua.nure.bratchun.summary_task4.db.entity.Faculty;
import ua.nure.bratchun.summary_task4.db.entity.Subject;
import ua.nure.bratchun.summary_task4.db.validation.FacultyValidation;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.AttributeNames;
import ua.nure.bratchun.summary_task4.web.command.Command;
import ua.nure.bratchun.summary_task4.web.command.ParameterNames;

/**
 * Add faculty command
 * 
 * @author D.Bratchun
 *
 */
public class AddFacultyCommand extends Command{

	private static final long serialVersionUID = 240258530591489901L;
	private static final Logger LOG = Logger.getLogger(AddFacultyCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = doPost(request);
		} else {
			result = doGet(request);
		}
		LOG.debug("Command finished");
		return result;
	}
	
	private String doGet(HttpServletRequest request) throws AppException {
		SubjectDAO subjectDAO;
		List<Subject> diplomaSubjects = null;
		try {
			subjectDAO = SubjectDAO.getInstance();
			diplomaSubjects = subjectDAO.findAll();
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_SUBJECTS, e);
			throw new AppException(Messages.ERR_CANNOT_GET_SUBJECTS,e);
		}
		request.setAttribute(AttributeNames.SUBJECTS, diplomaSubjects);
		
		return Path.PAGE_ADD_FACULTY;
	}
	
	private String doPost(HttpServletRequest request) throws AppException{
		
		String nameRu = request.getParameter(ParameterNames.NAME_RU);
		String nameEn = request.getParameter(ParameterNames.NAME_EN);

		if(request.getParameter(ParameterNames.BUDGET_PLACES).isEmpty() && request.getParameter(ParameterNames.TOTAL_PLACES).isEmpty()) {			
			request.getSession().setAttribute(AttributeNames.ADD_FACULTY_ERROR_MESSAGE, "admin.faculties.add_faculty_jsp.error.incorrect_places");
			return Path.COMMAND_ADD_FACULTY;
		}
			int budgetPlaces = Integer.parseInt(request.getParameter(ParameterNames.BUDGET_PLACES));
			int totalPlaces = Integer.parseInt(request.getParameter(ParameterNames.TOTAL_PLACES));
		
		if(!FacultyValidation.validationNameEn(nameEn) || !FacultyValidation.validationNameRu(nameRu)) {
			request.getSession().setAttribute(AttributeNames.ADD_FACULTY_ERROR_MESSAGE, "admin.faculties.add_faculty_jsp.error.incorrect_name");
			return Path.COMMAND_ADD_FACULTY;
		}
		if(!FacultyValidation.validationPlaces(totalPlaces, budgetPlaces)) {
			request.getSession().setAttribute(AttributeNames.ADD_FACULTY_ERROR_MESSAGE, "admin.faculties.add_faculty_jsp.error.incorrect_places");
			return Path.COMMAND_ADD_FACULTY;
		}
		if(FacultyValidation.hasName(nameEn) || FacultyValidation.hasName(nameRu)) {
			request.getSession().setAttribute(AttributeNames.ADD_FACULTY_ERROR_MESSAGE, "admin.faculties.add_faculty_jsp.error.no_unique_name");
			return Path.COMMAND_ADD_FACULTY;
		}
		Faculty faculty = new Faculty(nameRu, nameEn, totalPlaces, budgetPlaces);
		try {
			FacultyDAO facultyDAO = FacultyDAO.getInstance();
			facultyDAO.insert(faculty);
			facultyDAO.addAllPriliminaryByFacultyId(faculty.getId(), getNewPreliminarySubjectsId(request, ParameterNames.PRELIMINARY));
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_INSERT_FACULTY, e);
		}
		
		return Path.COMMAND_LIST_FACULTY;
	}
	
	private List<Integer> getNewPreliminarySubjectsId(HttpServletRequest request, String parameter) {
		List<Integer> preliminarySubjectsId = new ArrayList<>();
		Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if(parameterName.matches(parameter + "\\d+")) {
            	parameterName = parameterName.replaceAll("\\D", "");
            	preliminarySubjectsId.add(Integer.parseInt(parameterName));
            }
        }
        return preliminarySubjectsId;
	}
}
