package ua.nure.bratchun.SummaryTask4.db.entity;

public class Application {
	private Entrant entrant;
	private double diplomaScore;
	private double preliminaryScore;
	private int entryTypeId;
	private int facultyId;
	
	public int getEntryTypeId() {
		return entryTypeId;
	}

	public void setEntryTypeId(int entryTypeId) {
		this.entryTypeId = entryTypeId;
	}

	public Application(Entrant entrant, int facultyId) {
		this.entrant = entrant;
		this.facultyId = facultyId;
	}
	
	public String getLogin() {
		return entrant.getLogin();
	}
	public String getFirstName() {
		return entrant.getFirstName();
	}

	public String getLastName() {
		return entrant.getLastName();
	}
	public String getEmail() {
		return entrant.getEmail();
	}
	
	public int getEntrantId() {
		return entrant.getId();
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public double getPreliminaryScore() {
		return preliminaryScore;
	}

	public void setPreliminaryScore(double preliminaryScore) {
		this.preliminaryScore = preliminaryScore;
	}

	public double getDiplomaScore() {
		return diplomaScore;
	}

	public void setDiplomaScore(double diplomaScore) {
		this.diplomaScore = diplomaScore;
	}
	
	public boolean getIsBlocked() {
		return entrant.isBlocked();
	}
	public void setIsBlocked(boolean isBlocked) {
		entrant.setIsBlocked(isBlocked);
	}
	
	public String getLang() {
		return entrant.getLang();
	}


}
