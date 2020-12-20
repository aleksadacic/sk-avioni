package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Admin;
import app.repository.AdminRepository;

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
	

//	@PostMapping("/dodavanjeLetova")
//	public ResponseEntity<String> dodajLet(@RequestBody LetForm letForm) {
//		try {
//			
//			
//			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
//	@DeleteMapping("/brisanjeLetova/{id}")
//	public ResponseEntity<String> obrisiLet(@RequestBody LetForm letForm) {
//		try {
//			
//			
//			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
}
