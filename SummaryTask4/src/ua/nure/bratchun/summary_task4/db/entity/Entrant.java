package ua.nure.bratchun.summary_task4.db.entity;

/**
 * Client user, entrant
 * @author brath
 *
 */
public class Entrant extends User{
	
	private static final long serialVersionUID = -7973524321960481376L;
	public Entrant() {}
	
	public Entrant(User user) {

		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setLang(user.getLang());
		this.setLogin(user.getLogin());
		this.setPassword(user.getPassword());
		this.setRoleId(user.getRoleId());
	}
	

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	private String city;
	private String region;
	private String school;
	private boolean isBlocked;
	
	public String toString() {
		return "[id: "  + getId() + ", login: " + getLogin() + "]";
	}
}
