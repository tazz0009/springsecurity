package com.tazz009.springsec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// RequestMatcher 구현(예: URL 패턴을 통해)을 사용하여
				// HttpServletRequest를 기반으로 액세스를 제한할 수 있습니다.
				.authorizeHttpRequests()
				// 패턴 등록
				.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
				// 모든 요청을 매핑합니다.
				.anyRequest()
				// 인증된 모든 사용자가 URL을 허용하도록 지정합니다.
				.authenticated()
				// AuthorizeHttpRequestsConfigurer를 사용하여 완료되면
				// HttpSecurityBuilder를 반환합니다.
				.and()
				// HTTP 기본 인증을 구성합니다.
				.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user1 = User.builder()
				.username("tazz009")
				.password(passwordEncoder.encode("1234qwer"))
				.roles("STUDENT")
				.build();
		return new InMemoryUserDetailsManager(user1);
	}

}
