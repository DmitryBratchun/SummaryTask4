package ua.nure.bratchun.summary_task4.web.command.admin.subject;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.SubjectDAO;
import ua.nure.bratchun.summary_task4.db.entity.Subject;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.Command;

public class EditSubjectsCommand extends Command{

	private static final long serialVersionUID = 1674095436016740066L;
	private static final Logger LOG = Logger.getLogger(EditSubjectsCommand.class);

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
		List<Subject> subjects = null;
		
		String orderBy = request.getParameter("orederBy");
		String direction = request.getParameter("direction");
		try {
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			if(orderBy == null || orderBy.isEmpty() || direction == null || direction.isEmpty()) {
				subjects = subjectDAO.findAll();
			} else {
				subjects = subjectDAO.findAllOrderBy(orderBy, direction);
			}
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_GET_SUBJECTS, e);
			throw new AppException(Messages.ERR_CANNOT_GET_SUBJECTS, e);
		}
		request.getSession().setAttribute("subjects", subjects);
		return Path.PAGE_LIST_ALL_SUBJECTS;
	}
	
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException {
		int subjectId = getDeliteSubjectsId(request, "subject");
		try {
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			subjectDAO.delete(subjectId);
		} catch (DBException e) {
			LOG.error(Messages.ERR_CANNOT_DELETE_SUBJECT, e);
			throw new AppException(Messages.ERR_CANNOT_DELETE_SUBJECT, e);
		}
		request.getSession().setAttribute("subjects", subjectId);
		return Path.COMMAND_LIST_SUBJECTS;
	}
	
	private int getDeliteSubjectsId(HttpServletRequest request, String parameter) {
		int subjectId = -1;
		Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            if(parameterName.matches(parameter + "\\d+")) {
            	parameterName = parameterName.replaceAll("\\D", "");
            	subjectId = Integer.parseInt(parameterName);
            }
        }
        return subjectId;
	}
}
