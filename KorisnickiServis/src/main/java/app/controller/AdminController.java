package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.entities.Admin;
import app.forms.AvionForm;
import app.repository.AdminRepository;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private BCryptPasswordEncoder encoder;
	private AdminRepository adminRepo;

	@Autowired
	public AdminController(BCryptPasswordEncoder encoder, AdminRepository adminRepo) {
		this.encoder = encoder;
		this.adminRepo = adminRepo;

		if (!adminRepo.existsByEmail("admin")) {
			Admin admin = new Admin("admin", encoder.encode("1"));
			adminRepo.saveAndFlush(admin);
		}
	}

	@PostMapping("/dodajAvion")
	public ResponseEntity<Object> dodajAvion(@RequestHeader(value = HEADER_STRING) String token, @RequestBody AvionForm form) {
		try {
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			return UtilsMethods.sendPost("http://localhost:8081/letovi/dodajAvion", form);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/obrisiAvion/{avion}")
	public ResponseEntity<Object> obrisiAvion(@RequestHeader(value = HEADER_STRING) String token, @PathVariable long avion) {
		try {
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			return UtilsMethods.sendGet("http://localhost:8081/letovi/obrisiAvion/" + avion, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean authorityCheck(String token) {
		String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
				.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
		if (adminRepo.existsByEmail(user))
			return true;
		return false;
	}

}
