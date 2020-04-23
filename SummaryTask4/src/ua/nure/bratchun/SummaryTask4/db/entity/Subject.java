package ua.nure.bratchun.SummaryTask4.db.entity;

public class Subject extends Entity{
	private static final long serialVersionUID = 7478712165594836356L;
	private String nameRu;
	private String nameEn;
	
	public Subject() {}
	
	public Subject(String nameRu, String nameEn) {
		this.nameEn = nameEn;
		this.nameRu = nameRu;
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
	
	public String toString() {
		return "Subject [id:" + getId() +", name en:" + nameEn + ", name ru:" + nameRu + "]";	
	}
}
