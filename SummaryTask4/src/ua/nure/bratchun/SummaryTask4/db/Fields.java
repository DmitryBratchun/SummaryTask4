package ua.nure.bratchun.SummaryTask4.db;
/**
 * Fields holder (stores the name of the columns from the database).
 * 
 * @author D.Bratchun
 *
 */
public final class Fields {
	
	private Fields() {}
	
	public static final String ENTITY_ID ="id";
	
	public static final String USER_LOGIN ="login";
	public static final String USER_PASSWORD ="password";
	public static final String USER_FIRST_NAME ="first_name";
	public static final String USER_LAST_NAME ="last_name";
	public static final String USER_EMAIL ="email";
	public static final String USER_LANG ="lang";
	public static final String USER_ROLE ="role_id";
	
	public static final String ENTRANTS_CITY ="city";
	public static final String ENTRANTS_REGION ="region";
	public static final String ENTRANTS_SCHOOL ="school";
	public static final String ENTRANTS_IS_BLOCKED ="isBlocked";
	
	public static final String SUBJECTS_NAME_RU ="name_ru";
	public static final String SUBJECTS_NAME_EN ="name_en";
	
	public static final String GRADES_ENTRANT_ID="entrant_id";
	public static final String GRADES_VALUE="value";
	public static final String GRADES_SUBJECT_ID = "subject_id";
	public static final String GRADES_FACULTY_ID="faculty_id";
	public static final String GRADES_EXAM_TYPE_ID="exam_type_id";
	
	public static final String FACULTY_NAME_RU="name_ru";
	public static final String FACULTY_NAME_EN="name_en";
	public static final String FACULTY_TOTAL_PLACES="total_places";
	public static final String FACULTY_BUDGET_PLACES="budget_places";
	
	public static final String APPLICATION_ENTRY_TYPE_ID = "entry_type_id";
	public static final String APPLICATION_DIPLOMA_SCORE = "diploma_score";
	public static final String APPLICATION_PRELIMINARY_SCORE = "preliminary_score";

}
