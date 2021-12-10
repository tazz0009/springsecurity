package com.tazz009.springsec;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tazz009.springsec.domain.Student;
import com.tazz009.springsec.service.StudentService;

@SpringBootApplication
public class SpringsecApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecApplication.class, args);
	}

	@Bean
	CommandLineRunner run(StudentService studentService) {
		return args -> {
			studentService.saveStudent(Student.builder().name("James Bond").build());
			studentService.saveStudent(Student.builder().name("Maria Jones").build());
			studentService.saveStudent(Student.builder().name("Anna Smith").build());
		};
	}
	
}
