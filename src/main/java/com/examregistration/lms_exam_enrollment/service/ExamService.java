package com.examregistration.lms_exam_enrollment.service;

import com.examregistration.lms_exam_enrollment.dto.ExamDTO;
import com.examregistration.lms_exam_enrollment.entity.Exam;
import com.examregistration.lms_exam_enrollment.entity.Student;
import com.examregistration.lms_exam_enrollment.exceptionHandler.BusinessException;
import com.examregistration.lms_exam_enrollment.exceptionHandler.ResourceNotFoundException;
import com.examregistration.lms_exam_enrollment.repository.ExamRepository;
import com.examregistration.lms_exam_enrollment.repository.StudentRepository;
import com.examregistration.lms_exam_enrollment.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    public ExamDTO registerStudentForExam(Long examId, String registrationId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id " + examId));

        Student student = studentRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with registrationId " + registrationId));

        if (!student.getEnrolledSubjects().contains(exam.getSubject())) {
            throw new BusinessException("Student must be enrolled in the subject before registering for the exam.");
        }

        exam.getEnrolledStudents().add(student);
        student.getRegisteredExams().add(exam);

        examRepository.save(exam);
        studentRepository.save(student);

        return convertToDTO(exam);
    }

    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(exam.getId());
        examDTO.setSubjectName(exam.getSubject().getSubjectName());
        examDTO.setEnrolledStudents(exam.getEnrolledStudents()
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList()));
        return examDTO;
    }
}

