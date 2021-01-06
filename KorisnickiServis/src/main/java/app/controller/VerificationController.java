package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.repository.AdminRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/verify")
public class VerificationController {
	
	AdminRepository adminRepo;
	UserRepository userRepo;
	
	@Autowired
	public VerificationController(AdminRepository adminRepo, UserRepository userRepo) {
		this.adminRepo = adminRepo;
		this.userRepo = userRepo;
	}

	@PostMapping("")
	public ResponseEntity<Boolean> verifyUser(@RequestHeader(value = HEADER_STRING) String token, @RequestBody String role) {
		try {
			String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			boolean verify = false;
			if (role.equalsIgnoreCase("admin")) {
				verify = adminRepo.existsByEmail(user);
			}
			else if (role.equalsIgnoreCase("user")) {
				verify = userRepo.existsByEmail(user);
			}
			return new ResponseEntity<Boolean>(verify, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}