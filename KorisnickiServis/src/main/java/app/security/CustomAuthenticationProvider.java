package app.security;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.entities.Admin;
import app.entities.User;
import app.repository.AdminRepository;
import app.repository.UserRepository;
import app.tools.CustomValidation;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private PasswordEncoder encoder;
	private UserRepository userRepo;

	@Autowired
	public CustomAuthenticationProvider(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String email = auth.getName();
		String password = auth.getCredentials().toString();
		
		User user = userRepo.findByEmail(email);
		if (user == null)
			throw new BadCredentialsException("Authentication failed");
		// proveri sifru
		if (encoder.matches(password, user.getSifra())) {
			return new UsernamePasswordAuthenticationToken(email, password, emptyList());
		}
		
		
		throw new BadCredentialsException("Authentication failed");
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
}
