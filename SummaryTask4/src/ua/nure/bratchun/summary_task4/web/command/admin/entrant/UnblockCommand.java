package ua.nure.bratchun.summary_task4.web.command.admin.entrant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.db.dao.EntrantDAO;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.Command;

public class UnblockCommand extends Command{
	private static final long serialVersionUID = -8757258173320501954L;
	private static final Logger LOG = Logger.getLogger(UnblockCommand.class);

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
		LOG.debug("Command finished");
		return result;
	}
	private String doPost(HttpServletRequest request, HttpServletResponse response) throws AppException {
		int entrantId = Integer.parseInt(request.getParameter("entrantId"));
		
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
		request.setAttribute("lines", lines);
		request.setAttribute("page", page);
		
		EntrantDAO entrantDAO = EntrantDAO.getInstance();
		entrantDAO.unblockById(entrantId);
		return Path.COMMAND_VIEW_BLOCKED_ENTRANTS+"&page=" + page + "&lines=" + lines ;
	}
}
