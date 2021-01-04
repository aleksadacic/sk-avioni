package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Avion;
import app.entities.Let;
import app.forms.AvionForm;
import app.repository.AvionRepository;
import app.repository.LetRepository;

@RestController
@RequestMapping("/letovi")
public final class LetController {
	
	private LetRepository letRepo;
	private AvionRepository avionRepo;
	
	@Autowired
	public LetController(LetRepository letRepo, AvionRepository avionRepo) {
		this.letRepo = letRepo;
		this.avionRepo = avionRepo;
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
	
	@PostMapping("/dodajAvion")
	public ResponseEntity<Avion> dodajAvion(@RequestBody AvionForm form) {
		try {
			Avion avion = new Avion(form.getNaziv(), form.getKapacitet());
			avion = avionRepo.save(avion);
			return new ResponseEntity<Avion>(avion, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/obrisiAvion/{avion}")
	public ResponseEntity<Long> obrisiAvion(@PathVariable long avion) {
		try {
			List<Let> letovi = letRepo.findByAvion(avion);
			if (letovi == null || letovi.isEmpty() && avionRepo.existsById(avion)) {
				avionRepo.deleteById(avion);
				return new ResponseEntity<Long>(avion, HttpStatus.ACCEPTED);
			}
			throw new Exception();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
