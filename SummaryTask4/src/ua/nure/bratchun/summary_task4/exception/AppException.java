package ua.nure.bratchun.summary_task4.exception;

/**
 * Application exception
 * @author D.Bratchun
 *
 */
public class AppException  extends Exception{

	private static final long serialVersionUID = 1L;

	public AppException() {
		super();
	}
	
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}
}
