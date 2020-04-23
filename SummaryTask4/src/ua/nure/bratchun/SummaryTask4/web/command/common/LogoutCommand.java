package ua.nure.bratchun.SummaryTask4.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.bratchun.SummaryTask4.Path;
import ua.nure.bratchun.SummaryTask4.exception.AppException;
import ua.nure.bratchun.SummaryTask4.web.HttpMethod;
import ua.nure.bratchun.SummaryTask4.web.command.Command;

public class LogoutCommand extends Command{

	private static final long serialVersionUID = 6389772507392407802L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		
		HttpSession session= request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("userRole");
		
		return Path.PAGE_LOGIN;
	}

}
