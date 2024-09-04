package com.examregistration.lms_exam_enrollment.service;
import com.examregistration.lms_exam_enrollment.entity.Subject;
import com.examregistration.lms_exam_enrollment.entity.Student;
import com.examregistration.lms_exam_enrollment.dto.SubjectDTO;

import com.examregistration.lms_exam_enrollment.exceptionHandler.ResourceNotFoundException;
import com.examregistration.lms_exam_enrollment.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());

        subjectRepository.save(subject);
        return subjectDTO;
    }

    public SubjectDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
        return convertToDTO(subject);
    }

    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));

        subject.setSubjectName(subjectDTO.getSubjectName());
        subjectRepository.save(subject);

        return convertToDTO(subject);
    }

    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));

        subjectRepository.delete(subject);
    }

    private SubjectDTO convertToDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectName(subject.getSubjectName());
        subjectDTO.setEnrolledStudents(subject.getEnrolledStudents()
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList()));
        return subjectDTO;
    }
}

