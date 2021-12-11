package com.tazz009.springsec.auth;

import java.util.Optional;

public interface ApplicationUserDao {

	public Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
