package app.security;

import static app.security.SecurityConstants.LOGIN_PATH;
import static app.security.SecurityConstants.REGISTRATION_PATH;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import app.repository.AdminRepository;
import app.repository.UserRepository;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private CustomAuthenticationProvider customAuthenticationProvider;
	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	private AdminRepository adminRepo;

	
	@Autowired
	public WebSecurity(CustomAuthenticationProvider customAuthenticationProvider, UserRepository userRepo,
			BCryptPasswordEncoder encoder, AdminRepository adminRepo) {
		super();
		this.customAuthenticationProvider = customAuthenticationProvider;
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.adminRepo = adminRepo;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(LOGIN_PATH, REGISTRATION_PATH).permitAll()
//				.antMatchers(USER_PATH).hasRole("USER")
//				.antMatchers(ADMIN_PATH).hasRole("ADMIN")
				.anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), userRepo, adminRepo)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		customAuthenticationProvider.setEncoder(encoder);
		auth.authenticationProvider(customAuthenticationProvider);
	}

}