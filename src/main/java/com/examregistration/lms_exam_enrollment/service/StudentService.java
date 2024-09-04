package com.examregistration.lms_exam_enrollment.service;

import com.examregistration.lms_exam_enrollment.entity.Exam;
import com.examregistration.lms_exam_enrollment.entity.Student;
import com.examregistration.lms_exam_enrollment.entity.Subject;

import com.examregistration.lms_exam_enrollment.dto.StudentDTO;

import com.examregistration.lms_exam_enrollment.exceptionHandler.ResourceNotFoundException;
import com.examregistration.lms_exam_enrollment.repository.ExamRepository;
import com.examregistration.lms_exam_enrollment.repository.StudentRepository;
import com.examregistration.lms_exam_enrollment.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;


    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setRegistrationId(studentDTO.getRegistrationId());
        student.setName(studentDTO.getName());

        studentRepository.save(student);
        return studentDTO;
    }

    public StudentDTO getStudentById(String registrationId) {
        Student student = studentRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + registrationId));
        return convertToDTO(student);
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        student.setName(studentDTO.getName());
        studentRepository.save(student);

        return convertToDTO(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        studentRepository.delete(student);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setRegistrationId(student.getRegistrationId());
        studentDTO.setName(student.getName());

        // Safely handle null lists
        List<String> enrolledSubjectNames = new ArrayList<>();
        if (student.getEnrolledSubjects() != null) {
            for (Subject subject : student.getEnrolledSubjects()) {
                enrolledSubjectNames.add(subject.getSubjectName());
            }
        }
        studentDTO.setEnrolledSubjects(enrolledSubjectNames);

        List<String> registeredExamSubjectNames = new ArrayList<>();
        if (student.getRegisteredExams() != null) {
            for (Exam exam : student.getRegisteredExams()) {
                registeredExamSubjectNames.add(exam.getSubject().getSubjectName());
            }
        }
        studentDTO.setRegisteredExams(registeredExamSubjectNames);

        return studentDTO;
    }





}
