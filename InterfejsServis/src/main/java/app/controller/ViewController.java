package app.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.forms.Avion;
import app.forms.CreditCard;
import app.forms.EditProfileForm;
import app.forms.KupiLet;
import app.forms.Let;
import app.forms.LoginForm;
import app.forms.NewLetForm;
import app.forms.UserInfo;
import app.utils.CustomTools;
import app.utils.UtilsMethods;

@Controller
@RequestMapping("")
public class ViewController {
	
	public static final String GATEWAY_URL = "http://localhost:8762/korisnicki-servis";
//	public static final String ROUTES_URL = GATEWAY_URL + "/actuator/routes";
	
	private boolean admin = false;

	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	private String token = "";
	
	@PostMapping("")
	public String loginEnter(@RequestParam String email, @RequestParam String password) {
		try {
			LoginForm loginform = new LoginForm(email, password);
			ResponseEntity<Object> res = UtilsMethods.sendPost(GATEWAY_URL + "/login", loginform, null);
			if (res != null) {
				token = res.getHeaders().getValuesAsList("Authorization").get(0);
				return "redirect:";
			}
			return "redirect:login";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:login";
		}
	}
	
	@GetMapping("")
	public String homepage(Model model) {
		try {
			UserInfo info = fillUserInfo(token);
			if (info.getEmail().equals("admin")) {
				admin = true;
				List<Let> letovi = getFlights(token);
				model.addAttribute("flights", letovi);
				List<Avion> avioni = getPlanes(token);
				model.addAttribute("planes", avioni);
				model.addAttribute("noviLet", new NewLetForm());
				model.addAttribute("noviAvion", new Avion());
				model.addAttribute("flight", new String());
				return "adminindex";
			}
			else {
				List<Let> letovi = getFlights(token);
				model.addAttribute("flights", letovi);
				model.addAttribute("fullname", info.getIme() + " " + info.getPrezime());
				List<CreditCard> kartice = getCreditCards(token);
				model.addAttribute("cards", kartice);
				int discount = CustomTools.calculateDiscount(info.getRankNaziv()); //mozda alya radi
				model.addAttribute("discount", discount);
				model.addAttribute("kupilet", new KupiLet());
				return "index";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:login";
		}
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}
	
	@GetMapping("/editProfile")
	public String editProfilePage(Model model) {
		UserInfo info = fillUserInfo(token);
		EditProfileForm epf = new EditProfileForm();
		epf.setIme(info.getIme());
		epf.setPrezime(info.getPrezime());
		epf.setEmail(info.getEmail());
		epf.setPasos(info.getPasos());
		model.addAttribute("rank", info.getRankNaziv());
		model.addAttribute("points", info.getRankPoeni());
		model.addAttribute("updated", epf);
		CreditCard cc = new CreditCard();
		model.addAttribute("kartica", cc);
		model.addAttribute("cards", getCreditCards(token));
		return "editprofile";
	}
	
	@PostMapping("/submitKartica")
	public String newCreditCard(@ModelAttribute CreditCard kartica) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendPost(GATEWAY_URL + "/addCreditCard", kartica, Map.of("Authorization", token));
			if (!res.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				throw new Exception();
			}
			return "redirect:editProfile";			
		} catch (Exception e) {
			e.printStackTrace();
			return "editProfile"; //errors
		}		
	}
	
	
	@PostMapping("/submitEditProfile")
	public String submitProfile(@ModelAttribute EditProfileForm updated) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendPost(GATEWAY_URL + "/editProfile", updated, Map.of("Authorization", token));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:login";
	}
	
	
	@PostMapping("/addNewFlight")
	public String addNewFlight(@ModelAttribute NewLetForm noviLet) {
		try {
			System.out.println("view " + noviLet.getAvionid());
			ResponseEntity<Object> res = UtilsMethods.sendPost(GATEWAY_URL + "/admin/dodajLet", noviLet, Map.of("Authorization", token));
			if (!res.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				throw new Exception();
			}
			return "redirect:";
		} catch (Exception e) {
			return "redirect:";
		}
	}
	
	@GetMapping("/deleteFlight/{flight}")
	public String deleteFlight(@PathVariable String flight) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendGet(GATEWAY_URL + "/admin/obrisiLet/" + flight, Map.of("Authorization", token));
			if (!res.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				throw new Exception();
			}
			return "redirect:../";
		} catch (Exception e) {
			return "redirect:../";
		}
	}
	
	@PostMapping("/addNewPlane")
	public String addNewPlane(@ModelAttribute Avion noviAvion) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendPost(GATEWAY_URL + "/admin/dodajAvion", noviAvion, Map.of("Authorization", token));
			if (!res.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				throw new Exception();
			}
			return "redirect:";
		} catch (Exception e) {
			return "redirect:";
		}
	}
	
	@PostMapping("/buyTicketSubmit")
	public String buyTicket(@ModelAttribute KupiLet kupilet) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendPost(GATEWAY_URL + "/buyTicket", kupilet, Map.of("Authorization", token));
			if (!res.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				throw new Exception();
			}
			return "redirect:";
		} catch (Exception e) {
			return "index";
		}
	}
	
	@GetMapping("/deletePlane/{plane}")
	public String deletePlane(@PathVariable String plane) {
		try {
			ResponseEntity<Object> res = UtilsMethods.sendGet(GATEWAY_URL + "/admin/obrisiAvion/" + plane, Map.of("Authorization", token));
			if (!res.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				throw new Exception();
			}
			return "redirect:../";
		} catch (Exception e) {
			return "redirect:../";
		}
	}
	
	@SuppressWarnings("unchecked")
	private UserInfo fillUserInfo(String token) {
		ResponseEntity<Object> type = UtilsMethods.sendGet(GATEWAY_URL + "/verify/usertype", Map.of("Authorization", token)); //error
		System.out.println(type.getBody().toString());
		if (!type.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			return null;
		}
		LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>) type.getBody();
		UserInfo info = new UserInfo();
		for (Entry<String, String> entry : lhm.entrySet()) {
			if (entry.getKey().equals("email"))
				info.setEmail(entry.getValue());
			if (entry.getKey().equals("ime"))
				info.setIme(entry.getValue());
			if (entry.getKey().equals("prezime"))
				info.setPrezime(entry.getValue());
			if (entry.getKey().equals("pasos"))
				info.setPasos(entry.getValue());
			if (entry.getKey().equals("rankPoeni"))
				info.setRankPoeni(entry.getValue());
			if (entry.getKey().equals("rankNaziv"))
				info.setRankNaziv(entry.getValue());
			if (entry.getKey().equals("id"))
				info.setId(Long.parseLong(entry.getValue()));
		}
		return info;
	}
	
	@SuppressWarnings("unchecked")
	private List<Let> getFlights(String token) {
		ResponseEntity<Object> get = null;
		if (admin) {
			get = UtilsMethods.sendGet(GATEWAY_URL + "/admin/sviLetoviAdmin", Map.of("Authorization", token));
		}
		else {
			get = UtilsMethods.sendGet(GATEWAY_URL + "/sviLetovi", Map.of("Authorization", token));	
		}
		if (get == null || !get.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			return null;
		}
		List<Object> svi = (List<Object>)get.getBody();
		List<Let> letovi = new ArrayList<Let>();
		for (Object s : svi) {
			LinkedHashMap<String, Object> str = (LinkedHashMap<String, Object>) s;
			Let let = new Let();
			for (Entry<String, Object> entry : str.entrySet()) {
				if (entry.getValue() instanceof LinkedHashMap<?, ?>) {
					Avion a = new Avion();
					for (Entry<String, Object> ug : ((LinkedHashMap<String, Object>)entry.getValue()).entrySet()) {
						if (ug.getKey().equals("naziv"))
							a.setNaziv((String) ug.getValue());
						if (ug.getKey().equals("kapacitet"))
							a.setKapacitet((int) ug.getValue());
					}
					let.setAvion(a);
				}
				if (entry.getKey().equals("pocetnaDestinacija"))
					let.setPocetnaDestinacija((String) entry.getValue());
				if (entry.getKey().equals("krajnjaDestinacija"))
					let.setKrajnjaDestinacija((String) entry.getValue());
				if (entry.getKey().equals("duzinaLeta"))
					let.setDuzinaLeta(((Double) entry.getValue()).longValue());
				if (entry.getKey().equals("cena"))
					let.setCena((double) entry.getValue());
				if (entry.getKey().equals("id"))
					let.setId(Long.parseLong(entry.getValue().toString()));
			}
			letovi.add(let);
		}
		return letovi;	
	}
	
	@SuppressWarnings("unchecked")
	private List<Avion> getPlanes(String token) {
		ResponseEntity<Object> get = UtilsMethods.sendGet(GATEWAY_URL + "/admin/sviAvioni", Map.of("Authorization", token));
		if (!get.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			return null;
		}
		List<Object> svi = (List<Object>)get.getBody();
		List<Avion> avioni = new ArrayList<Avion>();
		for (Object s : svi) {
			Avion avion = new Avion();
			LinkedHashMap<String, Object> str = (LinkedHashMap<String, Object>) s;
			for (Entry<String, Object> ug :  str.entrySet()) {
				if (ug.getKey().equals("naziv"))
					avion.setNaziv((String) ug.getValue());
				if (ug.getKey().equals("kapacitet"))
					avion.setKapacitet((int) ug.getValue());
				if (ug.getKey().equals("id"))
					avion.setId(Long.parseLong(ug.getValue().toString()));
			}
			avioni.add(avion);
		}
		return avioni;	
	}
	
	@SuppressWarnings("unchecked")
	private List<CreditCard> getCreditCards(String token) {
		ResponseEntity<Object> get = UtilsMethods.sendGet(GATEWAY_URL + "/findKartice", Map.of("Authorization", token));
		if (!get.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			return null;
		}
		List<Object> svi = (List<Object>)get.getBody();
		List<CreditCard> kartice = new ArrayList<CreditCard>();
		for (Object s : svi) {
			LinkedHashMap<String, Object> str = (LinkedHashMap<String, Object>) s;
			CreditCard kartica = new CreditCard();
			for (Entry<String, Object> entry : str.entrySet()) {
				if (entry.getKey().equals("ime"))
					kartica.setIme((String) entry.getValue());
				if (entry.getKey().equals("prezime"))
					kartica.setPrezime((String) entry.getValue());
				if (entry.getKey().equals("sigurnosniBroj"))
					kartica.setSigurnosniBroj((String) entry.getValue());
				if (entry.getKey().equals("brojKartice"))
					kartica.setBrojKartice((String) entry.getValue());
				if (entry.getKey().equals("id"))
					kartica.setId(((Integer)entry.getValue()).longValue());
			}
			kartice.add(kartica);
		}
		return kartice;
	}
	
	ObjectMapper om = new ObjectMapper();
	@SuppressWarnings("unused")
	private String toJson(Object o) {
		try {
			return om.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
