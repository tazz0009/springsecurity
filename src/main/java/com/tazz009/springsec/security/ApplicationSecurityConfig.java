package com.tazz009.springsec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tazz009.springsec.service.UserService;

import static com.tazz009.springsec.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

import static com.tazz009.springsec.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// Cross Site Request Forgery(????????? ??? ?????? ??????)
				.csrf().disable()
//				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and()
				// RequestMatcher ??????(???: URL ????????? ??????)??? ????????????
				// HttpServletRequest??? ???????????? ???????????? ????????? ??? ????????????.
				.authorizeRequests()
				// ?????? ??????
				.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(STUDENT.name())
//				.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//				.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//				.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//				.antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
				// ?????? ????????? ???????????????.
				.anyRequest()
				// ????????? ?????? ???????????? URL??? ??????????????? ???????????????.
				.authenticated()
				// AuthorizeHttpRequestsConfigurer??? ???????????? ????????????
				// HttpSecurityBuilder??? ???????????????.
				.and()
				// HTTP ?????? ????????? ???????????????.
				//.httpBasic();
				.formLogin()
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/courses", true)
					.passwordParameter("password")
					.usernameParameter("username")
				.and()
				// default 2 weeks
					.rememberMe()
					.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)).key("somethingverysecured")
					.rememberMeParameter("remember-me")
				.and()
				.logout()
					.logoutUrl("/logout")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID", "remember-me")
					.logoutSuccessUrl("/login");
	}

//	@Bean
//	public DaoAuthenticationProvider daoAuthenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder);
//		provider.setUserDetailsService(userService);
//		return provider;
//	}
	
//	@Override
//	protected UserDetailsService userDetailsService() {
//		return userService;
//	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
