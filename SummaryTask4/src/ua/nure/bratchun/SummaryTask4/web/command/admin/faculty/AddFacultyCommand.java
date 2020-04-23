package ua.nure.bratchun.SummaryTask4.web.command.admin.faculty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.Path;
import ua.nure.bratchun.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bratchun.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Faculty;
import ua.nure.bratchun.SummaryTask4.db.entity.Subject;
import ua.nure.bratchun.SummaryTask4.db.validation.FacultyValidation;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.exception.DBException;
import ua.nure.bratchun.SummaryTask4.exception.Messages;
import ua.nure.bratchun.SummaryTask4.web.HttpMethod;
import ua.nure.bratchun.SummaryTask4.web.command.Command;

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
			result = doPost(request, response);
		} else {
			result = doGet(request, response);
		}
		LOG.debug("Command finished");
		return result;
	}
	
	private String doGet(HttpServletRequest request, HttpServletResponse response) throws AppException {
		SubjectDAO subjectDAO;
		List<Subject> diplomaSubjects = null;
		try {
			subjectDAO = SubjectDAO.getInstance();
			diplomaSubjects = subjectDAO.findAll();
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_SUBJECTS, e);
			throw new AppException(Messages.ERR_CANNOT_GET_SUBJECTS,e);
		}
		request.setAttribute("subjects", diplomaSubjects);
		
		return Path.PAGE_ADD_FACULTY;
	}
	
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException{
		
		String nameRu = request.getParameter("nameRu");
		String nameEn = request.getParameter("nameEn");

		if(request.getParameter("budgetPlaces").isEmpty() && request.getParameter("totalPlaces").isEmpty()) {			
			request.getSession().setAttribute("addFacultyErrorMessage", "admin.faculties.add_faculty_jsp.error.incorrect_places");
			return Path.COMMAND_ADD_FACULTY;
		}
			int budgetPlaces = Integer.parseInt(request.getParameter("budgetPlaces"));
			int totalPlaces = Integer.parseInt(request.getParameter("totalPlaces"));
		
		if(!FacultyValidation.validationNameEn(nameEn) || !FacultyValidation.validationNameRu(nameRu)) {
			request.getSession().setAttribute("addFacultyErrorMessage", "admin.faculties.add_faculty_jsp.error.incorrect_name");
			return Path.COMMAND_ADD_FACULTY;
		}
		if(!FacultyValidation.validationPlaces(totalPlaces, budgetPlaces)) {
			request.getSession().setAttribute("addFacultyErrorMessage", "admin.faculties.add_faculty_jsp.error.incorrect_places");
			return Path.COMMAND_ADD_FACULTY;
		}
		if(FacultyValidation.hasName(nameEn) || FacultyValidation.hasName(nameRu)) {
			request.getSession().setAttribute("addFacultyErrorMessage", "admin.faculties.add_faculty_jsp.error.no_unique_name");
			return Path.COMMAND_ADD_FACULTY;
		}
		Faculty faculty = new Faculty(nameRu, nameEn, totalPlaces, budgetPlaces);
		try {
			FacultyDAO facultyDAO = FacultyDAO.getInstance();
			facultyDAO.insert(faculty);
			facultyDAO.addAllPriliminaryByFacultyId(faculty.getId(), getNewPreliminarySubjectsId(request, "preliminary"));
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_INSERT_FACULTY, e);
		}
		
		return Path.COMMAND_VIEW_PAGE_ADMIN;
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
