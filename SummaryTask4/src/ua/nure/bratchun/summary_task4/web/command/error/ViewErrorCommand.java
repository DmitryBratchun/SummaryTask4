package ua.nure.bratchun.summary_task4.web.command.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.bratchun.summary_task4.Path;
import ua.nure.bratchun.summary_task4.exception.AppException;
import ua.nure.bratchun.summary_task4.web.HttpMethod;
import ua.nure.bratchun.summary_task4.web.command.Command;

public class ViewErrorCommand extends Command{

	private static final long serialVersionUID = -3695555195703848425L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
			throws IOException, ServletException, AppException {
		return Path.PAGE_ERROR;
	}

}
