package com.examregistration.lms_exam_enrollment.controller;

import com.examregistration.lms_exam_enrollment.dto.ExamDTO;
import com.examregistration.lms_exam_enrollment.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping("/register")
    public ResponseEntity<ExamDTO> registerForExam(@PathVariable Long examId, @RequestParam String registrationId) {
        ExamDTO examDTO = examService.registerStudentForExam(examId, registrationId);
        return ResponseEntity.ok(examDTO);
    }

}

