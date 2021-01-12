package app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.entities.Avion;
import app.entities.Let;
import app.forms.AvionForm;
import app.forms.LetForm;
import app.repository.AvionRepository;
import app.repository.LetRepository;
import app.utils.UtilsMethods;

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
	public ResponseEntity<Object> sviLetovi(@RequestHeader(value = "Authorization") String token) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			List<Let> letovi = letRepo.findNotFull();
			return new ResponseEntity<Object>(letovi, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/sviLetoviAdmin")
	public ResponseEntity<List<Let>> sviLetoviAdmin(@RequestHeader(value = "Authorization") String token) {
		try {
			if (!verifyUser(token, "admin"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			List<Let> letovi = letRepo.findAll();
			return new ResponseEntity<List<Let>>(letovi, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/sviAvioni")
	public ResponseEntity<List<Avion>> sviAvioni(@RequestHeader(value = "Authorization") String token) {
		try {
			if (!verifyUser(token, "admin"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			
			List<Avion> letovi = avionRepo.findAll();
			return new ResponseEntity<List<Avion>>(letovi, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	//mzoda posenbno svi parametri..
	@GetMapping("/pretraga/{query}")
	public ResponseEntity<List<Let>> pretraga(@RequestHeader(value = "Authorization") String token, @PathVariable String query) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			List<Let> letovi = null;
			if (!query.isEmpty())
				letovi = letRepo.findByAny(query);
			else
				letovi = letRepo.findAll();
			
			return new ResponseEntity<List<Let>>(letovi, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		}
	}
	
	@GetMapping("/findPrice/{query}")
	public ResponseEntity<Double> findPrice(@RequestHeader(value = "Authorization") String token, @PathVariable String query) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			List<Let> letovi = null;
			if (!query.isEmpty())
				letovi = letRepo.findByAny(query);
			
			if (letovi != null)
				return new ResponseEntity<Double>(letovi.get(0).getCena(), HttpStatus.ACCEPTED);
			
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		}
	}
	
	@GetMapping("/exists/{query}")
	public ResponseEntity<Boolean> exists(@RequestHeader(value = "Authorization") String token, @PathVariable String query) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			List<Let> letovi = null;
			if (!query.isEmpty())
				letovi = letRepo.findByAny(query);
			
			if (letovi != null)
				return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		}
	}
	
	
	@GetMapping("/isFull/{query}")
	public ResponseEntity<Boolean> isFull(@RequestHeader(value = "Authorization") String token, @PathVariable String query) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			List<Let> letovi = null;
			if (!query.isEmpty())
				letovi = letRepo.findNotFull();
			else
				letovi = letRepo.findAll();
			
			for(Let l : letovi) {
				if (l.getId() == Long.parseLong(query)) {
					return new ResponseEntity<Boolean>(false, HttpStatus.ACCEPTED);
				}
			}
			return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
		}

	}
	
	@PostMapping("/dodajAvion")
	public ResponseEntity<Avion> dodajAvion(@RequestHeader(value = "Authorization") String token, @RequestBody AvionForm form) {
		try {
			if (!verifyUser(token, "admin"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Avion avion = new Avion(form.getNaziv(), form.getKapacitet());
			avion = avionRepo.save(avion);
			return new ResponseEntity<Avion>(avion, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/obrisiAvion/{avion}")
	public ResponseEntity<Long> obrisiAvion(@RequestHeader(value = "Authorization") String token, @PathVariable long avion) {
		try {
			if (!verifyUser(token, "admin"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
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
	
	@PostMapping("/dodajLet")
	public ResponseEntity<Let> dodajLet(@RequestHeader(value = "Authorization") String token, @RequestBody LetForm form) {
		try {
			if (!verifyUser(token, "admin"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Avion avion = avionRepo.findById(form.getAvionid());
			Let let = new Let(avion, form.getPocetnaDestinacija(), form.getKrajnjaDestinacija(), (double) form.getDuzinaLeta(), form.getCena());
			let = letRepo.save(let);
			return new ResponseEntity<Let>(let, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// mesidj broker
	@Autowired JmsTemplate jmsTemplate;
	@Autowired Queue korisnickiQueue;
	
	@GetMapping("/obrisiLet/{let}")
	public ResponseEntity<Long> obrisiLet(@RequestHeader(value = "Authorization") String token, @PathVariable long let) {
		try {
			if (!verifyUser(token, "admin"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			Let l = letRepo.findById(let);
			if (l.getProdateKarte() > 0) {
				ObjectMapper om = new ObjectMapper();
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("duzina", l.getDuzinaLeta());
				hm.put("token", token);
//				for (long user : users) {
//					hm.put("user", value)
//					String json = om.writeValueAsString(hm);
//					jmsTemplate.convertAndSend(korisnickiQueue, json);
//				}
				//servisu 3 isto
			}
			
			letRepo.delete(l);
			return new ResponseEntity<Long>(let, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean verifyUser(String token, String role) {
		try {
			ResponseEntity<Boolean> res = UtilsMethods.verifyUser(Map.of("Authorization", token), role);
			if (res.getBody())
				return true;
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
