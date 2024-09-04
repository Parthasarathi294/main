package com.examregistration.lms_exam_enrollment.repository;

import com.examregistration.lms_exam_enrollment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRegistrationId(String registrationId);
}
