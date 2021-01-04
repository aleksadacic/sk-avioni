package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Admin;
import app.forms.AvionForm;
import app.repository.AdminRepository;
import app.utils.UtilsMethods;
import jdk.javadoc.doclet.Reporter;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private BCryptPasswordEncoder encoder;
	private AdminRepository adminRepo;

	@Autowired
	public AdminController(BCryptPasswordEncoder encoder, AdminRepository adminRepo) {
		this.encoder = encoder;
		this.adminRepo = adminRepo;
		
		if(!adminRepo.existsByUsername("admin")) {
			Admin admin = new Admin("admin", encoder.encode("1"));
			adminRepo.saveAndFlush(admin);			
		}
	}
	
	
	@PostMapping("/dodajAvion") //mozda da izbrisem avionform da ne postoji
	public ResponseEntity<Object> dodajAvion(@RequestBody AvionForm form) {
		try {
			return UtilsMethods.sendPost("http://localhost:8081/letovi/dodajAvion", form);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/obrisiAvion/{avion}")
	public ResponseEntity<Object> obrisiAvion(@PathVariable long avion) {
		try {
			return UtilsMethods.sendGet("http://localhost:8081/letovi/obrisiAvion/" + avion, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
