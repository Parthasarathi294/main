package com.examregistration.lms_exam_enrollment.dto;

import java.util.List;

public class StudentDTO {
    private String registrationId;
    private String name;
    private List<String> enrolledSubjects;
    private List<String> registeredExams;

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public void setEnrolledSubjects(List<String> enrolledSubjects) {
        this.enrolledSubjects = enrolledSubjects;
    }

    public List<String> getRegisteredExams() {
        return registeredExams;
    }

    public void setRegisteredExams(List<String> registeredExams) {
        this.registeredExams = registeredExams;
    }
}
