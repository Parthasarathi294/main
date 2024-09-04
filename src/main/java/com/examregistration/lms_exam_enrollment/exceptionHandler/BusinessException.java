package com.examregistration.lms_exam_enrollment.exceptionHandler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
