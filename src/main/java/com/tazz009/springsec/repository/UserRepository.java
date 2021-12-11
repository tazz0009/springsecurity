package com.tazz009.springsec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tazz009.springsec.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
}
