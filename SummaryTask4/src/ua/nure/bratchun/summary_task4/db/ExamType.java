package ua.nure.bratchun.summary_task4.db;

import ua.nure.bratchun.summary_task4.db.entity.Grade;
/**
 * Exam type. 
 * 
 * @author D.Bratchun
 *
 */
public enum ExamType {
	DIPLOMA, PRELIMINARY;
	
	/**
	 * Get type of exam by grade
	 * @param grade
	 * @return exam type
	 */
	public static ExamType getExamType(Grade grade) {
		int examTypeId = grade.getExamTypeId();
		return ExamType.values()[examTypeId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
