package ua.nure.bratchun.summary_task4;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag class with create footer text
 * @author D.Bratchun
 *
 */
public class CreateFooterTextTag extends TagSupport{
	
	private static final long serialVersionUID = 9100500997878679225L;

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print("EPAM's University Program 2020. Bratchun Dmitry group 4.1");
		} catch (IOException ioException) {
			throw new JspException("Error: " + ioException.getMessage());
		}
		return SKIP_BODY;
	}
}
