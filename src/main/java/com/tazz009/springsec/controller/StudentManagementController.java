package com.tazz009.springsec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tazz009.springsec.domain.Student;
import com.tazz009.springsec.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

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
	
	@PostMapping
	public void registerNewStudent(@RequestBody Student student) {
		log.info("registerNewStudent {}", student);
	}

	@DeleteMapping(path = "{id}")
	public void deleteStudent(@PathVariable("id") Long id) {
		log.info("deleteStudent {}", id);
	}

	@PutMapping(path = "{id}")
	public void updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
		log.info("updateStudent {}, {}", id, student);
	}
	
}