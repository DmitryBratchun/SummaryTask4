package ua.nure.bratchun.SummaryTask4.db.entity;

public class Faculty extends Entity{
	
	private static final long serialVersionUID = 6055277065314021986L;
	
	
	private String nameRu;
	private String nameEn;
	private int totalPlaces;
	private int budgetPlaces;
	
	public Faculty() {}
	
	public Faculty(String nameRu, String nameEn, int totalPlaces, int budgetPlaces) {
		this.nameRu = nameRu;
		this.nameEn = nameEn;
		this.totalPlaces = totalPlaces;
		this.budgetPlaces = budgetPlaces;
	}
	
	
	public String getNameRu() {
		return nameRu;
	}
	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public int getTotalPlaces() {
		return totalPlaces;
	}
	public void setTotalPlaces(int totalPlaces) {
		this.totalPlaces = totalPlaces;
	}
	public int getBudgetPlaces() {
		return budgetPlaces;
	}
	public void setBudgetPlaces(int budgetPlaces) {
		this.budgetPlaces = budgetPlaces;
	}

}
