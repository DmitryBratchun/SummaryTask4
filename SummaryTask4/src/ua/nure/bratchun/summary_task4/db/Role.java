package ua.nure.bratchun.summary_task4.db;

import ua.nure.bratchun.summary_task4.db.entity.User;

/**
 * Role entity.
 * 
 * @author D.Bratchun
 * 
 */

public enum Role {
	ADMIN, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}

