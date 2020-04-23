package ua.nure.bratchun.SummaryTask4.db;

import ua.nure.bratchun.SummaryTask4.db.entity.Application;
/**
 * University admission status
 * 
 * @author D.Bratchun
 *
 */
public enum EntryType {
	NOT_INCLUDED_IN_THE_STATEMENT, UNDER_CONSIDERATION, BUDGET, CONTRACT, FAILED;
	
	public static EntryType getEntryType(Application application) {
		int entryTypeId = application.getEntryTypeId();
		return EntryType.values()[entryTypeId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
