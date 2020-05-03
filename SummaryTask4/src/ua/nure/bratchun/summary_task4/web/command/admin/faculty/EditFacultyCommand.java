package ua.nure.bratchun.summary_task4.web.command.admin.faculty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
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
 * Edit faculty command
 * 
 * @author D.Bratchun
 *
 */
public class EditFacultyCommand extends Command {

	private static final long serialVersionUID = 3849451275354002975L;
	private static final Logger LOG = Logger.getLogger(EditFacultyCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;

		if (method == HttpMethod.POST) {
			result = doPost(request);
		} else {
			result = doGet(request);
		}
		LOG.debug("Command finished");
		return result;
	}

	private String doGet(HttpServletRequest request) throws AppException {
		int facultyId = Integer.parseInt(request.getParameter(AttributeNames.FACULTY_ID));
		Faculty faculty = null;
		List<Subject> preliminarySubjects = null;
		List<Subject> diplomaSubjects = null;

		try {
			FacultyDAO facultyDAO = FacultyDAO.getInstance();
			faculty = facultyDAO.findById(facultyId);

			request.setAttribute(AttributeNames.FACULTY, faculty);
			LOG.debug("request faculty " + faculty);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_GET_FACULTY, e);
		}

		try {
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			diplomaSubjects = subjectDAO.findAll();
			preliminarySubjects = subjectDAO.getSubjectsByFacultyId(facultyId);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_SUBJECTS, e);
			throw new AppException(Messages.ERR_CANNOT_GET_SUBJECTS, e);
		}

		List<Subject> noPreliminarySubjects = diplomaSubjects;
		Iterator<Subject> iterator = null;
		for (Subject preliminary : preliminarySubjects) {
			iterator = noPreliminarySubjects.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getId() == preliminary.getId()) {
					iterator.remove();
				}
			}
		}
		request.setAttribute("noPreliminarySubjects", noPreliminarySubjects);
		LOG.debug("request no preliminary subjects " + noPreliminarySubjects);

		request.setAttribute(AttributeNames.PRELIMINARY_SUBJECTS, preliminarySubjects);
		LOG.debug("request preliminary subjects " + preliminarySubjects);

		return Path.PAGE_EDIT_FACULTY;
	}

	private String doPost(HttpServletRequest request) throws AppException {

		FacultyDAO facultyDAO = null;
		Faculty faculty = null;
		int facultyId = Integer.parseInt(request.getParameter(AttributeNames.FACULTY_ID));
		try {
			facultyDAO = FacultyDAO.getInstance();
			if (request.getParameter(ParameterNames.DELETE) != null) {
				facultyDAO.deleteByID(facultyId);
				return Path.COMMAND_LIST_FACULTY;
			}
			faculty = facultyDAO.findById(facultyId);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_GET_FACULTY, e);
		}
		try {
			if (request.getParameter(ParameterNames.DELETE) != null) {
				facultyDAO.deleteByID(facultyId);
				return Path.COMMAND_LIST_FACULTY;
			}
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_DELETE_FACULTY, e);
			throw new AppException(Messages.ERR_CANNOT_DELETE_FACULTY, e);
		}

		String newNameRu = request.getParameter(ParameterNames.NEW_NAME_RU);
		String newNameEn = request.getParameter(ParameterNames.NEW_NAME_EN);
		LOG.debug("Ru " + newNameRu + " En " + newNameEn);

		int newTotalPlaces = faculty.getTotalPlaces();
		int newBudgetPlaces = faculty.getBudgetPlaces();

		if (FacultyValidation.hasName(newNameEn) || FacultyValidation.hasName(newNameRu)) {
			request.getSession().setAttribute(AttributeNames.EDIT_FACULTY_ERROR_MESSAGE,
					"admin.faculties.edit_faculty_jsp.error.no_unique_name");
			return Path.COMMAND_EDIT_FACULTY + "&" + AttributeNames.FACULTY_ID + "=" + facultyId;
		}

		if (newNameEn != null && !newNameEn.isEmpty() && FacultyValidation.validationNameEn(newNameEn)) {
			faculty.setNameEn(newNameEn);
		}
		if (newNameRu != null && !newNameRu.isEmpty() && FacultyValidation.validationNameRu(newNameRu)) {
			faculty.setNameRu(newNameRu);
		}
		
		if (newNameEn != null && !newNameEn.isEmpty() && !FacultyValidation.validationNameEn(newNameEn)) {
			request.getSession().setAttribute(AttributeNames.EDIT_FACULTY_ERROR_MESSAGE,
					"admin.faculties.edit_faculty_jsp.error.incorrect_name");
			return Path.COMMAND_EDIT_FACULTY + "&" + AttributeNames.FACULTY_ID + "=" + facultyId;
		}
		if (newNameRu != null && !newNameRu.isEmpty() && !FacultyValidation.validationNameRu(newNameRu)) {
			request.getSession().setAttribute(AttributeNames.EDIT_FACULTY_ERROR_MESSAGE,
					"admin.faculties.edit_faculty_jsp.error.incorrect_name");
			return Path.COMMAND_EDIT_FACULTY + "&" + AttributeNames.FACULTY_ID + "=" + facultyId;
		}
		
		if (request.getParameter(ParameterNames.NEW_TOTAL_PLACES) != null
				&& !request.getParameter(ParameterNames.NEW_TOTAL_PLACES).isEmpty()) {
			newTotalPlaces = Integer.parseInt(request.getParameter(ParameterNames.NEW_TOTAL_PLACES));
		}
		if (request.getParameter(ParameterNames.NEW_BUDGET_PLACES) != null
				&& !request.getParameter(ParameterNames.NEW_BUDGET_PLACES).isEmpty()) {
			newBudgetPlaces = Integer.parseInt(request.getParameter(ParameterNames.NEW_BUDGET_PLACES));
		}
		if (!FacultyValidation.validationPlaces(newTotalPlaces, newBudgetPlaces)) {
			request.getSession().setAttribute(AttributeNames.EDIT_FACULTY_ERROR_MESSAGE,
					"admin.faculties.edit_faculty_jsp.error.incorrect_places");
			return Path.COMMAND_EDIT_FACULTY + "&"+ AttributeNames.FACULTY_ID+"=" + facultyId;
		} else {
			faculty.setBudgetPlaces(newBudgetPlaces);
			faculty.setTotalPlaces(newTotalPlaces);
		}
		facultyDAO.update(faculty);
		facultyDAO.deleteAllPriliminaryByFacultyId(facultyId);
		facultyDAO.addAllPriliminaryByFacultyId(facultyId, getNewPreliminarySubjectsId(request, ParameterNames.PRELIMINARY));

		return Path.COMMAND_LIST_FACULTY;
	}

	private List<Integer> getNewPreliminarySubjectsId(HttpServletRequest request, String parameter) {
		List<Integer> preliminarySubjectsId = new ArrayList<>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = enumeration.nextElement();
			if (parameterName.matches(parameter + "\\d+")) {
				parameterName = parameterName.replaceAll("\\D", "");
				preliminarySubjectsId.add(Integer.parseInt(parameterName));
			}
		}
		return preliminarySubjectsId;
	}
}
