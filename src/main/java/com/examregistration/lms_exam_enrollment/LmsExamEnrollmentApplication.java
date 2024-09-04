package com.examregistration.lms_exam_enrollment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.boot.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.PrintStream;
@SpringBootApplication

public class LmsExamEnrollmentApplication {

	private static final Logger logger = LoggerFactory.getLogger(LmsExamEnrollmentApplication.class);

		public static void main(String[] args) {
			SpringApplication.run(LmsExamEnrollmentApplication.class, args);
		}

	}