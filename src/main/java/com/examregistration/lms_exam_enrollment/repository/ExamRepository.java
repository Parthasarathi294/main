package com.examregistration.lms_exam_enrollment.repository;

import com.examregistration.lms_exam_enrollment.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
