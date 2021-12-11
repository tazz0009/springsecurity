package com.tazz009.springsec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.tazz009.springsec.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

import static com.tazz009.springsec.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// Cross Site Request Forgery(사이트 간 요청 위조)
				.csrf().disable()
//				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and()
				// RequestMatcher 구현(예: URL 패턴을 통해)을 사용하여
				// HttpServletRequest를 기반으로 액세스를 제한할 수 있습니다.
				.authorizeRequests()
				// 패턴 등록
				.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(STUDENT.name())
//				.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//				.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//				.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//				.antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
				// 모든 요청을 매핑합니다.
				.anyRequest()
				// 인증된 모든 사용자가 URL을 허용하도록 지정합니다.
				.authenticated()
				// AuthorizeHttpRequestsConfigurer를 사용하여 완료되면
				// HttpSecurityBuilder를 반환합니다.
				.and()
				// HTTP 기본 인증을 구성합니다.
				//.httpBasic();
				.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/courses", true)
				.and()
				// default 2 weeks
				.rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)).key("somethingverysecured");
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails stu01 = User.builder()
				.username("tazz001")
				.password(passwordEncoder.encode("1234qwer"))
//				.roles(STUDENT.name()) // ROLE_STUDENT
				.authorities(STUDENT.getGranAuthorities())
				.build();
		UserDetails admin01 = User.builder()
				.username("admin01")
				.password(passwordEncoder.encode("1234qwer"))
//				.roles(ADMIN.name()) // ROLE_ADMIN
				.authorities(ADMIN.getGranAuthorities())
				.build();
		UserDetails admin02 = User.builder()
				.username("admin02")
				.password(passwordEncoder.encode("1234qwer"))
//				.roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
				.authorities(ADMINTRAINEE.getGranAuthorities())
				.build();
		return new InMemoryUserDetailsManager(stu01, admin01, admin02);
	}

}
