package ua.nure.bratchun.summary_task4.exception;

/**
 * Application exception
 * @author D.Bratchun
 *
 */
public class AppException  extends Exception{

	private static final long serialVersionUID = -5733037831413544616L;

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
