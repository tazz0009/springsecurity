package com.tazz009.springsec;

import org.springframework.boot.CommandLineRunner;

import static com.tazz009.springsec.security.ApplicationUserRole.*;

import java.util.Set;

import static com.tazz009.springsec.security.ApplicationUserPermission.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tazz009.springsec.domain.Role;
import com.tazz009.springsec.domain.Student;
import com.tazz009.springsec.domain.User;
import com.tazz009.springsec.repository.RoleRepository;
import com.tazz009.springsec.service.StudentService;
import com.tazz009.springsec.service.UserService;

@SpringBootApplication
public class SpringsecApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecApplication.class, args);
	}

	@Bean
	CommandLineRunner run(StudentService studentService, UserService userService, PasswordEncoder passwordEncoder, RoleRepository authorityRepository) {
		return args -> {
			studentService.saveStudent(Student.builder().name("James Bond").build());
			studentService.saveStudent(Student.builder().name("Maria Jones").build());
			studentService.saveStudent(Student.builder().name("Anna Smith").build());
			
			userService.saveRole(Role.builder().name("ROLE_STUDENT").build());
			userService.saveRole(Role.builder().name("ROLE_ADMIN").build());
			userService.saveRole(Role.builder().name("ROLE_ADMINTRAINEE").build());
			userService.saveRole(Role.builder().name("STUDENT_READ").build());
			userService.saveRole(Role.builder().name("STUDENT_WRITE").build());
			userService.saveRole(Role.builder().name("COURSE_READ").build());
			userService.saveRole(Role.builder().name("COURSE_WRITE").build());
			
			userService.saveUser(User.builder().username("tazz001").password(passwordEncoder.encode("1234qwer")).build());
			userService.saveUser(User.builder().username("admin01").password(passwordEncoder.encode("1234qwer")).build());
			userService.saveUser(User.builder().username("admin02").password(passwordEncoder.encode("1234qwer")).build());
			
			userService.addRoleToUser("tazz001", "ROLE_STUDENT");
			userService.addRoleToUser("admin01", "ROLE_ADMIN");
			userService.addRoleToUser("admin01", "STUDENT_WRITE");
			userService.addRoleToUser("admin01", "STUDENT_READ");
			userService.addRoleToUser("admin02", "ROLE_ADMINTRAINEE");
			userService.addRoleToUser("admin02", "STUDENT_READ");
		};
	}
	
}
