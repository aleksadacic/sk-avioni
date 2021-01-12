package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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

import app.entities.KreditnaKartica;
import app.entities.RankKorisnika;
import app.entities.RankType;
import app.entities.User;
import app.entities.UserClient;
import app.forms.FlightForm;
import app.forms.KarticaForm;
import app.forms.RankForm;
import app.forms.RegistrationForm;
import app.repository.KarticaRepository;
import app.repository.RankRepository;
import app.repository.UserRepository;
import app.tools.CustomValidation;
import app.utils.EmailServiceImpl;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("")
public class UserController {

	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	private RankRepository rankRepo;
	private KarticaRepository karticaRepo;
	private EmailServiceImpl esi;

	@Autowired
	public SimpleMailMessage template;

	@Autowired
	public UserController(BCryptPasswordEncoder encoder, UserRepository userRepo, RankRepository rankRepo,
			KarticaRepository karticaRepo, EmailServiceImpl esi) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.rankRepo = rankRepo;
		this.karticaRepo = karticaRepo;
		this.esi = esi;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {
		if (userRepo.existsByEmail(registrationForm.getEmail())) {
			return new ResponseEntity<String>("used email", HttpStatus.BAD_REQUEST);
		}
		if (!CustomValidation.validateUser(registrationForm))
			return new ResponseEntity<String>("invalid parameters", HttpStatus.BAD_REQUEST);

		try {
			RankKorisnika rank = new RankKorisnika();
			rankRepo.saveAndFlush(rank);

			UserClient user = new UserClient(registrationForm.getEmail(),
					encoder.encode(registrationForm.getPassword()), registrationForm.getIme(),
					registrationForm.getPrezime(), registrationForm.getPasos(), rank);
			userRepo.saveAndFlush(user);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			String text = String.format(template.getText(), new Object());
			esi.sendSimpleMessage(registrationForm.getEmail(), "Confirm your email | RAF Airlines", text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
	}

	@PostMapping("/editProfile")
	public ResponseEntity<String> editProfile(@RequestHeader(value = HEADER_STRING) String token,
			@RequestBody RegistrationForm registrationForm) {
		if (!CustomValidation.validateUser(registrationForm))
			return new ResponseEntity<String>("invalid parameters", HttpStatus.BAD_REQUEST);

		boolean mailchanged = false;
		try {

			String stari = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			UserClient user = (UserClient) userRepo.findByEmail(stari);
			
			if (user == null)
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);

			if (registrationForm.getEmail().equalsIgnoreCase(stari)
					|| (userRepo.findByEmail(registrationForm.getEmail()) != null
							&& !userRepo.findByEmail(registrationForm.getEmail()).getEmail().equalsIgnoreCase(stari)))
				return new ResponseEntity<String>("email exists", HttpStatus.BAD_REQUEST);
			

			if (!registrationForm.getEmail().equalsIgnoreCase(stari))
				mailchanged = true;

			// iz frontenda saljemo podatke tako da su stare vrednosti popujnene u formi pa
			// mogu da se edituju
			user.setEmail(registrationForm.getEmail());
			user.setIme(registrationForm.getIme());
			user.setPrezime(registrationForm.getPrezime());
			user.setPasos(registrationForm.getPasos());
			user.setSifra(encoder.encode(registrationForm.getPassword()));

			userRepo.saveAndFlush(user);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (mailchanged) {
			try {
				String text = String.format(template.getText(), new Object());
				esi.sendSimpleMessage(registrationForm.getEmail(), "Confirm your email | RAF Airlines", text);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
	}

	@PostMapping("/addCreditCard")
	public ResponseEntity<Boolean> dodajKarticu(@RequestHeader(value = HEADER_STRING) String token,
			@RequestBody KarticaForm karticaForm) {
		try {
			if (!CustomValidation.validateCard(karticaForm))
				throw new Exception();

			String stari = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			UserClient user = (UserClient) userRepo.findByEmail(stari);
			if (user == null)
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);

			KreditnaKartica kartica = new KreditnaKartica(karticaForm.getIme(), karticaForm.getPrezime(),
					karticaForm.getBrojKartice(), karticaForm.getSigurnosniBroj());
			user.getKreditneKartice().add(kartica);

			karticaRepo.saveAndFlush(kartica);
			userRepo.save(user);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/updateMiles/{poeni}")
	public ResponseEntity<String> updateMiles(@RequestHeader(value = HEADER_STRING) String token,
			@PathVariable Integer points) {
		try {
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);

			String stari = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			UserClient user = userRepo.findByEmail(stari);
			RankKorisnika rank = userRepo.findRankKorisnika(user);
			
			if (user == null)
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);

			rank.setPoeni(rank.getPoeni() + points);
			if (rank.getPoeni() < 1000)
				rank.setNaziv(RankType.BRONZA);
			if (rank.getPoeni() >= 1000 && rank.getPoeni() < 10000)
				rank.setNaziv(RankType.SREBRO);
			if (rank.getPoeni() >= 10000)
				rank.setNaziv(RankType.ZLATO);
			
			rankRepo.save(rank);

			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/updateRank")
	public ResponseEntity<String> updateRank(@RequestHeader(value = HEADER_STRING) String token,
			@RequestBody RankForm rankForm) {
		try {
			if (!CustomValidation.validateRank(rankForm))
				throw new Exception();

			String stari = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			UserClient user = userRepo.findByEmail(stari);
			RankKorisnika rank = userRepo.findRankKorisnika(user);
			
			if (user == null)
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);

			rankUpdater(rank, rankForm);
			
			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public void rankUpdater(RankKorisnika rank, RankForm rankForm) {
		rank.setNaziv(rankForm.getNaziv());
		rank.setPoeni(rankForm.getPoeni());
		rankRepo.save(rank);
	}

	@GetMapping("/findFlight/{query}")
	public ResponseEntity<Object> pretragaLetova(@RequestHeader(value = HEADER_STRING) String token, @PathVariable String query) {
		try {
			
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			ResponseEntity<Object> response = UtilsMethods.sendGet("http://localhost:8081/letovi/pretraga/" + query,
					null);
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/sviLetovi")
	public ResponseEntity<Object> sviLetovi(@RequestHeader(value = HEADER_STRING) String token) {
		try {
			
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			ResponseEntity<Object> response = UtilsMethods.sendGet("http://localhost:8081/letovi/sviLetovi",
					Map.of("Authorization", token));
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findKartice")
	public ResponseEntity<Object> findKartice(@RequestHeader(value = HEADER_STRING) String token) {
		try {
			
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			UserClient user = userRepo.findByEmail(email);
			List<KreditnaKartica> response = karticaRepo.findByUser(user);
			
			return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean authorityCheck(String token) {
		String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
				.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
		if (userRepo.existsByEmail(user))
			return true;
		return false;
	}
	
	@PostMapping("/buyTicket")
	public ResponseEntity<Object> buyTicket(@RequestHeader(value = HEADER_STRING) String token, @RequestBody FlightForm flightForm) {
		try {
			if (!authorityCheck(token))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			String mail = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			User user = userRepo.findByEmail(mail);
			Long userId = user.getId();
			RankKorisnika rank = userRepo.findRankKorisnika((UserClient) user);
			String userRank = rank.getNaziv();
			
			if (flightForm.getCardId() == null || flightForm.getCardId() == 0)
				return new ResponseEntity<Object>("Please choose your credit card.", HttpStatus.BAD_REQUEST);
			
			FlightForm body = flightForm;
			flightForm.setUserId(userId);
			flightForm.setUserRank(userRank);
			
			return UtilsMethods.sendPost("http://localhost:8082/buyTicket", body, Map.of("Authorization", token));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
