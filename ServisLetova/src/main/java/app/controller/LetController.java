package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Let;
import app.repository.LetRepository;

@RestController
@RequestMapping("/letovi")
public final class LetController {
	
	private LetRepository letRepo;
	
	@Autowired
	public LetController(LetRepository letRepo) {
		this.letRepo = letRepo;
	}
	
	@GetMapping("/sviLetovi")
	public ResponseEntity<List<Let>> sviLetovi() {
		try {
			List<Let> letovi = letRepo.findNotFull();
			return new ResponseEntity<List<Let>>(letovi, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/pretraga/{query}")
	public ResponseEntity<List<Let>> pretraga(@PathVariable String query) {
		try {
			List<Let> letovi = letRepo.findByAny(query);
			return new ResponseEntity<List<Let>>(letovi, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		}
	}
}
