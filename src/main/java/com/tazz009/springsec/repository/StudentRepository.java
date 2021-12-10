package com.tazz009.springsec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tazz009.springsec.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
