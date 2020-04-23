package ua.nure.bratchun.SummaryTask4.db;

import ua.nure.bratchun.SummaryTask4.db.entity.Grade;
/**
 * Exam type
 * 
 * @author D.Bratchun
 *
 */
public enum ExamType {
	DIPLOMA, PRELIMINARY;
	
	public static ExamType getExamType(Grade grade) {
		int examTypeId = grade.getExamTypeId();
		return ExamType.values()[examTypeId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
