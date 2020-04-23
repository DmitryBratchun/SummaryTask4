package ua.nure.bratchun.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author D.Bratchun
 * 
 */
public final class Path {
	
	private Path(){}
	
	// pages
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_ERROR = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_CLIENT_REGISRTATION = "/registration.jsp";
	public static final String PAGE_ADMIN = "/WEB-INF/jsp/admin/profile/view.jsp";
	public static final String PAGE_CLIENT = "/WEB-INF/jsp/client/profile/view.jsp";
	public static final String PAGE_USER_SETTINGS ="/WEB-INF/jsp/user/settings.jsp";
	public static final String PAGE_LIST_ALL_FACULTIES ="/WEB-INF/jsp/user/faculties_list.jsp";
	public static final String PAGE_ENTRY_FACULTY = "/WEB-INF/jsp/client/entry/entry_faculty.jsp";
	public static final String PAGE_EDIT_FACULTY = "/WEB-INF/jsp/admin/faculties/edit_faculty.jsp";
	public static final String PAGE_ADD_FACULTY = "/WEB-INF/jsp/admin/faculties/add_faculty.jsp";
	public static final String PAGE_LIST_ALL_SUBJECTS = "/WEB-INF/jsp/admin/subject/subjects_list.jsp";
	public static final String PAGE_ADD_SUBJECT ="/WEB-INF/jsp/admin/subject/add_subject.jsp";
	public static final String PAGE_FACULTY_ENTRANTS ="/WEB-INF/jsp/admin/faculties/view_faculty.jsp";
	public static final String PAGE_VIEW_STATEMENT ="/WEB-INF/jsp/admin/faculties/view_statement.jsp";
	public static final String PAGE_VIEW_BLOCKED_ENTRANTS = "/WEB-INF/jsp/admin/blocked_entrants.jsp";
	
	// commands
	public static final String COMMAND_VIEW_PAGE_ADMIN = "controller?command=viewAdminPage";
	public static final String COMMAND_VIEW_PAGE_CLIENT = "controller?command=viewClientPage";
	public static final String COMMAND_VIEW_LOGIN_PAGE = "controller?command=login";
	public static final String COMMAND_VIEW_REGISTRATION_PAGE = "controller?command=registration";
	public static final String COMMAND_LOGOUT = "controller?command=logout";
	public static final String COMMAND_VIEW_ERROR_PAGE ="controller?command=viewErrorPage";
	public static final String COMMAND_SETTINGS_USER = "controller?command=userSettings";
	public static final String COMMAND_VIEW_FACULTY = "controller?command=viewFaculty";
	public static final String COMMAND_LIST_FACULTY = "controller?command=viewAllFaculties";
	public static final String COMMAND_ADD_FACULTY = "controller?command=addFaculty";
	public static final String COMMAND_LIST_SUBJECTS = "controller?command=editSubjects";
	public static final String COMMAND_ADD_SUBJECT = "controller?command=addSubject";
	public static final String COMMAND_BLOCK_ENTRANT = "controller?command=blockEntrant";
	public static final String COMMAND_VIEW_STATEMENT = "controller?command=viewStatement";
	public static final String COMMAND_VIEW_BLOCKED_ENTRANTS = "controller?command=viewBlockedEntrants";

}