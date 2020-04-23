package ua.nure.bratchun.SummaryTask4.web.command.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.SummaryTask4.Path;
import ua.nure.bratchun.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bratchun.SummaryTask4.db.entity.Faculty;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.exception.DBException;
import ua.nure.bratchun.SummaryTask4.web.HttpMethod;
import ua.nure.bratchun.SummaryTask4.web.command.Command;
/**
 * View all faculties
 * @author D.Bratchun
 *
 */
public class ViewAllFacultiesCommand extends Command{

	private static final long serialVersionUID = 9188012295034506682L;
	private static final Logger LOG = Logger.getLogger(ViewAllFacultiesCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		LOG.debug("Start executing Command");
		String result = null;
		result = doGet(request, response);
		LOG.debug("Finished executing Command");
		return result;
	}
	
	private String doGet(HttpServletRequest request, HttpServletResponse response) throws DBException {
		FacultyDAO facultyDAO = FacultyDAO.getInstance();
		List<Faculty> faculties = null;
		
		int page = 1;
		int lines = 10;
		if(request.getParameter("page")!=null ) {	
			page = Integer.parseInt(request.getParameter("page").replace("/D", ""));
		}
		if (page <1){
			page = 1;
		} 
		if(request.getParameter("lines")!=null) {	
			lines = Integer.parseInt(request.getParameter("lines").replace("/D", ""));
		}
		if (lines <1){
			lines = 10;
		}
		
		String orderBy = request.getParameter("orederBy")==null? "id": request.getParameter("orederBy");
		String direction = request.getParameter("direction")==null?"DESC": request.getParameter("direction");
		
		faculties = facultyDAO.findAllOrderBy(orderBy, direction,  (page-1)*lines ,lines);

		while(faculties.isEmpty() && page>1) {
			page--;
			faculties = facultyDAO.findAllOrderBy(orderBy, direction,  (page-1)*lines ,lines);
		}
		request.setAttribute("lines", lines);
		request.setAttribute("page", page);
		request.setAttribute("faculties", faculties);
		
		return Path.PAGE_LIST_ALL_FACULTIES;
	}

}
