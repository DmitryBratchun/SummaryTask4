package ua.nure.bratchun.summary_task4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.bratchun.summary_task4.web.command.admin.CommandViewAdminPage;
import ua.nure.bratchun.summary_task4.web.command.admin.entrant.BlockCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.entrant.UnblockCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.entrant.ViewBlockedEntrants;
import ua.nure.bratchun.summary_task4.web.command.admin.faculty.AddFacultyCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.faculty.AddToStatementCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.faculty.EditFacultyCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.faculty.FileStatementCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.faculty.ViewStatementCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.subject.AddSubjectCommand;
import ua.nure.bratchun.summary_task4.web.command.admin.subject.EditSubjectsCommand;
import ua.nure.bratchun.summary_task4.web.command.client.CommandViewClientPage;
import ua.nure.bratchun.summary_task4.web.command.client.EntryFacultyCommand;
import ua.nure.bratchun.summary_task4.web.command.registration.CommandRegistrationClient;
import ua.nure.bratchun.summary_task4.web.command.common.LoginCommand;
import ua.nure.bratchun.summary_task4.web.command.common.LogoutCommand;
import ua.nure.bratchun.summary_task4.web.command.common.NoCommand;
import ua.nure.bratchun.summary_task4.web.command.common.UserSettingsCommand;
import ua.nure.bratchun.summary_task4.web.command.common.ViewAllFacultiesCommand;
import ua.nure.bratchun.summary_task4.web.command.common.ViewFacultyCommand;
import ua.nure.bratchun.summary_task4.web.command.error.ViewErrorCommand;

/**
 * Holder for all commands.
 * 
 * @author D.Bratchun
 * 
 */
public class CommandContainer {
	
	private CommandContainer(){}
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<>();
	
	static {
		
		// admin commands 
		commands.put("viewAdminPage", new CommandViewAdminPage());
		commands.put("editFaculty", new EditFacultyCommand());
		commands.put("addFaculty", new AddFacultyCommand());
		commands.put("editSubjects", new EditSubjectsCommand());
		commands.put("addSubject", new AddSubjectCommand());
		commands.put("viewStatement", new ViewStatementCommand());
		commands.put("addToStatement", new AddToStatementCommand());
		commands.put("fileStatement", new FileStatementCommand());
		commands.put("blockEntrant", new BlockCommand());
		commands.put("viewBlockedEntrants", new ViewBlockedEntrants());
		commands.put("unblockEntrant", new UnblockCommand());
		
		// client commands
		commands.put("viewClientPage", new CommandViewClientPage());
		commands.put("entryFaculty", new EntryFacultyCommand());
		
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("registration", new CommandRegistrationClient());
		commands.put("logout", new LogoutCommand());
		commands.put("viewErrorPage", new ViewErrorCommand());
		commands.put("userSettings", new UserSettingsCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("viewAllFaculties", new ViewAllFacultiesCommand());
		commands.put("viewFaculty", new ViewFacultyCommand());
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}