package com.tazz009.springsec.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tazz009.springsec.domain.Role;
import com.tazz009.springsec.domain.User;
import com.tazz009.springsec.repository.RoleRepository;
import com.tazz009.springsec.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;

	public User saveUser(User user) {
		log.info("saveUser {}", user);
		return userRepo.save(user);
	}

	public Optional<User> getUser(String username) {
		log.info("getUser {}", username);
		return userRepo.findByUsername(username);
	}

	public List<User> getUsers() {
		log.info("getUsers");
		return userRepo.findAll();
	}

	public Role saveRole(Role role) {
		log.info("saveRole {}", role);
		return roleRepo.save(role);
	}

	public void addRoleToUser(String username, String roleName) {
		log.info("addRoleToUser {} , {}", username, roleName);
		User user = userRepo.findByUsername(username).get();
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
		userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not fount", username)));

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
