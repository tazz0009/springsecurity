package com.tazz009.springsec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tazz009.springsec.domain.Student;
import com.tazz009.springsec.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

	@Autowired
	private StudentService studentService; 

	@GetMapping
	public List<Student> getAllStudents() {
		return studentService.findAll();
	}

	@GetMapping(path = "{id}")
	public Optional<Student> getStudent(@PathVariable("id") Long id) {
		return studentService.findById(id);
	}
	
}
