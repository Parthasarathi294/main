package com.examregistration.lms_exam_enrollment;


import com.examregistration.lms_exam_enrollment.dto.ExamDTO;
import com.examregistration.lms_exam_enrollment.dto.StudentDTO;
import com.examregistration.lms_exam_enrollment.dto.SubjectDTO;
import com.examregistration.lms_exam_enrollment.entity.Student;
import com.examregistration.lms_exam_enrollment.repository.ExamRepository;
import com.examregistration.lms_exam_enrollment.repository.SubjectRepository;
import com.examregistration.lms_exam_enrollment.service.ExamService;
import com.examregistration.lms_exam_enrollment.service.StudentService;
import com.examregistration.lms_exam_enrollment.repository.StudentRepository;
import com.examregistration.lms_exam_enrollment.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class LmsExamEnrollmentApplicationTests {

	// For service tests
	@Mock
	private StudentRepository studentRepository;

	@Mock
	private ExamRepository examRepository;

	@Mock
	private SubjectRepository subjectRepository;

	@InjectMocks
	private StudentService studentService;

	@InjectMocks
	private ExamService examService;

	@InjectMocks
	private SubjectService subjectService;

	// For controller tests
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService mockStudentService;

	@MockBean
	private ExamService mockExamService;

	@MockBean
	private SubjectService mockSubjectService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Service Layer Tests

	@Test
	void testCreateStudent() {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setRegistrationId("123");
		studentDTO.setName("John Doe");

		Student student = new Student();
		student.setRegistrationId(studentDTO.getRegistrationId());
		student.setName(studentDTO.getName());

		when(studentRepository.save(any(Student.class))).thenReturn(student);

		StudentDTO result = studentService.createStudent(studentDTO);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
	}

	@Test
	void testGetStudentById() {
		Student student = new Student();
		student.setRegistrationId("123");
		student.setName("John Doe");

		when(studentRepository.findByRegistrationId("123")).thenReturn(Optional.of(student));

		StudentDTO result = studentService.getStudentById("123");

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
	}

	@Test
	void testUpdateStudent() {
		Student student = new Student();
		student.setRegistrationId("123");
		student.setName("John Doe");

		StudentDTO updatedDTO = new StudentDTO();
		updatedDTO.setRegistrationId("123");
		updatedDTO.setName("Jane Doe");

		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
		when(studentRepository.save(any(Student.class))).thenReturn(student);

		StudentDTO result = studentService.updateStudent(1L, updatedDTO);

		assertNotNull(result);
		assertEquals("Jane Doe", result.getName());
	}







	@Test
	void testCreateStudentController() throws Exception {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setRegistrationId("123");
		studentDTO.setName("John Doe");

		when(mockStudentService.createStudent(any(StudentDTO.class))).thenReturn(studentDTO);

		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"registrationId\": \"123\", \"name\": \"John Doe\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("John Doe")));
	}



	@Test
	void testGetAllStudentsController() throws Exception {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setRegistrationId("123");
		studentDTO.setName("John Doe");

		when(mockStudentService.getAllStudents()).thenReturn(Collections.singletonList(studentDTO));

		mockMvc.perform(get("/students")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("John Doe")));
	}

	@Test
	void testRegisterStudentForExamController() throws Exception {
		ExamDTO examDTO = new ExamDTO();
		examDTO.setId(1L);
		examDTO.setSubjectName("Math");

		when(mockExamService.registerStudentForExam(any(Long.class), any(String.class))).thenReturn(examDTO);

		mockMvc.perform(post("/exams/1/register?registrationId=123")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.subjectName", is("Math")));
	}

	@Test
	void testCreateSubjectController() throws Exception {
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setSubjectName("Math");

		when(mockSubjectService.createSubject(any(SubjectDTO.class))).thenReturn(subjectDTO);

		mockMvc.perform(post("/subjects")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"subjectName\": \"Math\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.subjectName", is("Math")));
	}

	@Test
	void testGetSubjectByIdController() throws Exception {
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setSubjectName("Math");

		when(mockSubjectService.getSubjectById(any(Long.class))).thenReturn(subjectDTO);

		mockMvc.perform(get("/subjects/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.subjectName", is("Math")));
	}
}