package ua.nure.bratchun.summary_task4.db;

import ua.nure.bratchun.summary_task4.db.entity.Application;
/**
 * University admission status. After creating the statement, all users included in the statement receive the status.
 * 
 * @author D.Bratchun
 *
 */
public enum EntryType {
	NOT_INCLUDED_IN_THE_STATEMENT, UNDER_CONSIDERATION, BUDGET, CONTRACT, FAILED;
	
	/**
	 * Get type of entry by application
	 * @param application
	 * @return
	 */
	public static EntryType getEntryType(Application application) {
		int entryTypeId = application.getEntryTypeId();
		return EntryType.values()[entryTypeId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
