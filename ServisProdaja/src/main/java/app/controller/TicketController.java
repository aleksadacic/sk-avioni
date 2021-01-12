package app.controller;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.entities.Ticket;
import app.forms.FlightForm;
import app.repository.TicketRepository;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("")
public class TicketController {
	private TicketRepository ticketRepo;
	
	@Autowired
	public TicketController(TicketRepository ticketRepo) {
		this.ticketRepo = ticketRepo;
	}
	
	@PostMapping("/buyTicket")
	public ResponseEntity<Boolean> buyTicket(@RequestHeader(value = "Authorization") String token, @RequestBody FlightForm body) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			if (!flightExists(token, body.getFlightId()) && isFull(token, body.getFlightId())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			ResponseEntity<Object> res = UtilsMethods.sendGet("http://localhost:8081/letovi/findPrice" + body.getFlightId(), Map.of("Authorization", token));
			Double price = (Double) res.getBody();
			
			switch(body.getUserRank()) {
			case "Srebro":
				price = price-10/100*price;
				break;
			case "Zlato":
				price = price-20/100*price;
				break;
			}
			
			Ticket t = new Ticket(body.getFlightId(), body.getUserId(), new Date(), price);
			ResponseEntity<Object> update = UtilsMethods.sendGet("http://localhost:8081/letovi/updateMiles/" + body.getPoints(), Map.of("Authorization", token));
			
			ticketRepo.saveAndFlush(t);
			return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/buyTicket")
	public ResponseEntity<Boolean> listAllTickets(@RequestHeader(value = "Authorization") String token) {
		try {
			if (!verifyUser(token, "user"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//			Long userId = 
			// leksa kidamo

			return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean flightExists(String token, Long flightId) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendGet("http://localhost:8081/letovi/exists/" + flightId, Map.of("Authorization", token));
			
			return (boolean) res.getBody();
		} catch(Exception e) {
			return false;
		}
	}
	
	private boolean isFull(String token, Long flightId) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendGet("http://localhost:8081/letovi/isFull/" + flightId, Map.of("Authorization", token));
			
			return (boolean) res.getBody();
		} catch(Exception e) {
			return false;
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
