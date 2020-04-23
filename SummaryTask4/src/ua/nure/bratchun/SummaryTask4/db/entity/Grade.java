package ua.nure.bratchun.SummaryTask4.db.entity;

public class Grade extends Entity{
	
	public Grade() {}
	
	public Grade(int entrantId, int facultyId, int subjectId, int value, int examTypeId) {
		setEntrantId(entrantId);
		setFacultyId(facultyId);
		setSubjectId(subjectId);
		setValue(value);
		setExamTypeId(examTypeId);
	}
	
	private static final long serialVersionUID = 5162658840620821191L;
	private int entrantId;
	private int facultyId;
	private int subjectId;
	private int value;
	private int examTypeId;
	public int getEntrantId() {
		return entrantId;
	}
	public void setEntrantId(int entrantId) {
		this.entrantId = entrantId;
	}
	public int getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getExamTypeId() {
		return examTypeId;
	}
	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}
}
