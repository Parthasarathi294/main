package com.examregistration.lms_exam_enrollment.dto;

import java.util.List;

public class SubjectDTO {
    private String subjectName;
    private List<String> enrolledStudents;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
