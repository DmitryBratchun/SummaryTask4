package ua.nure.bratchun.SummaryTask4.web.command.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.Path;
import ua.nure.bratchun.SummaryTask4.db.ExamType;
import ua.nure.bratchun.SummaryTask4.db.dao.GradeDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Grade;
import ua.nure.bratchun.SummaryTask4.db.entity.User;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.web.HttpMethod;
import ua.nure.bratchun.SummaryTask4.web.command.Command;

public class EntryFacultyCommand extends Command{
	
	private static final long serialVersionUID = -1612401430741620434L;
	private static final Logger LOG = Logger.getLogger(EntryFacultyCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String result = null;
		
		if(method == HttpMethod.POST) {
			result = doPost(request, response);
		} else {
			result = null;
		}
		LOG.debug("Command finished");
		return result;
	}
	
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException {
		User user = (User) request.getSession().getAttribute("user");
		int entrantId = user.getId();
		int facultyId = Integer.parseInt(request.getParameter("facultyId"));
		
		GradeDAO gradeDAO = GradeDAO.getInstance();
		if(gradeDAO.hasGrades(facultyId, entrantId)) {
			request.getSession().setAttribute("entryFacultyErrorMessage", "client.entry.entry_faculty_jsp.already_registered");
			return Path.COMMAND_VIEW_FACULTY + "&facultyId=" + facultyId;
		}
		
		
		// Parse all subjects id
		LOG.debug(user.getLogin() + " try to registration for entry diploma subjects" + 
				request.getParameter("diplomaSubjects"));
		List<Integer>diplomaSubjectsId = getSubjectsIdByParameter(request, "diploma");
		
		LOG.debug(user.getLogin() + " try to registration for entry preliminary subjects " + 
				request.getParameter("preliminarySubjects"));
		List<Integer>preliminarySubjectsId = getSubjectsIdByParameter(request, "preliminary");
		// Validate form filling
		if(!validateEntryForm(diplomaSubjectsId, preliminarySubjectsId, request)) {
			request.getSession().setAttribute("entryFacultyErrorMessage", "client.entry.entry_faculty_jsp.incorrectInput");
			return Path.COMMAND_VIEW_FACULTY + "&facultyId=" + facultyId;
		}
		if(!validateUnicueEntry(facultyId, entrantId)) {
			request.getSession().setAttribute("entryFacultyErrorMessage", "client.entry.entry_faculty_jsp.already_registered");
			return Path.COMMAND_VIEW_FACULTY + "&facultyId=" + facultyId;
		}
		// Compose all grades
		List<Grade> grades = composeGrades(diplomaSubjectsId, preliminarySubjectsId, entrantId, facultyId, request);
		// insert all grades in DB
		gradeDAO.insertAll(grades);
		return Path.COMMAND_LIST_FACULTY;
	}
	
	/**
	 * This method extracts subjects id from request
	 * 
	 * @param request
	 * @param parameter to extracting subject id from request
	 * @return
	 */
	private List<Integer> getSubjectsIdByParameter(HttpServletRequest request, String parameter) {
		List<Integer> subjectsId = new ArrayList<>();
		Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if(parameterName.matches(parameter + "\\d+")) {
            	parameterName = parameterName.replaceAll("\\D", "");
            	subjectsId.add(Integer.parseInt(parameterName));
            }
        }
        return subjectsId;
	}
	
	/**
	 * This method validate entry form
	 * 
	 * @param diplomaSubjectsId
	 * @param preliminarySubjectsId
	 * @param request
	 * @return
	 */
	private boolean validateEntryForm(List<Integer> diplomaSubjectsId, 
			List<Integer> preliminarySubjectsId, HttpServletRequest request) {
		
		int grade = 0;
		for(int id : diplomaSubjectsId) {
			if(request.getParameter("diploma" + id) == null || request.getParameter("diploma" + id).isEmpty()) {
				return false;
			}
			grade = Integer.parseInt(request.getParameter("diploma" + id));
			if(grade < 1 || grade > 12) {
				return false;
			}
		}
		for(int id : preliminarySubjectsId) {
			if(request.getParameter("preliminary" + id) == null || request.getParameter("preliminary" + id).isEmpty()) {
				return false;
			}
			grade = Integer.parseInt(request.getParameter("preliminary" + id));
			if(grade < 1 || grade > 12) {
				return false;
			}
		}
		return true;	
	}
	private boolean validateUnicueEntry(int facultyId, int entrantId) throws AppException {
		boolean result = false;
		if(!GradeDAO.getInstance().hasGrades(facultyId, entrantId)) {
			result = true;
		}
		return result;
	}
	/**
	 * This method composes grades
	 * 
	 * @param diplomaSubjectsId
	 * @param preliminarySubjectsId
	 * @param entrantId
	 * @param facultyId
	 * @param request
	 * @return
	 */
	private List<Grade> composeGrades(List<Integer> diplomaSubjectsId, 
			List<Integer> preliminarySubjectsId, int entrantId, int facultyId, HttpServletRequest request) {
		
		List<Grade> grades = new ArrayList<>();
		int value = 0;
		
		for(int id : diplomaSubjectsId) {
			value = Integer.parseInt(request.getParameter("diploma" + id));
			grades.add(new Grade(entrantId, facultyId, id, value, ExamType.DIPLOMA.ordinal()));
			
			
		}
		for(int id : preliminarySubjectsId) {
			value = Integer.parseInt(request.getParameter("preliminary" + id));
			grades.add(new Grade(entrantId, facultyId, id, value, ExamType.PRELIMINARY.ordinal()));
		}
		return grades;
	}
}
