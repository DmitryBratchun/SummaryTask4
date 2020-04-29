package ua.nure.bratchun.summary_task4.exception;

/**
 * Error message
 * @author D.Bratchun
 *
 */
public class Messages {
	
	private Messages() {}
	
	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";
	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from pool";
	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close connection";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain user by login";
	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close statement";
	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close resultset";
	public static final String ERR_CANNOT_CREATE_USER ="Cannot create a new user";
	public static final String ERR_CANNOT_CHECK_EMAIL ="An error occurred while checking the email";
	public static final String ERR_CANNOT_GET_ENTRANT_ID_BY_USER_ID = "Cannot get entrant id by user id";
	
	public static final String ERR_CANNOT_GET_SUBJECTS ="Cannot get subjects";
	public static final String ERR_CANNOT_GET_FACULTY ="Cannot get faculty";
	public static final String ERR_CANNOT_DELETE_FACULTY ="Cannot dlete faculty";
	public static final String ERR_CANNOT_INSERT_FACULTY ="Cannot insert faculty";
	public static final String ERR_CANNOT_DELETE_SUBJECT ="Cannot dlete subject";
	public static final String ERR_CANNOT_INSERT_SUBJECT ="Cannot insert subject";
	
}
