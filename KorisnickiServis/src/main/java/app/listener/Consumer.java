package app.listener;

import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.controller.UserController;
import app.entities.RankKorisnika;
import app.entities.UserClient;
import app.forms.DeleteFlightListenerForm;
import app.forms.RankForm;
import app.repository.AdminRepository;
import app.repository.UserRepository;
import app.tools.CustomValidation;

@Component
public class Consumer {
	
	@Autowired UserController userController;
	
	@Autowired UserRepository userRepo;
	
	@Autowired AdminRepository adminRepo;

	@JmsListener(destination = "korisnicki.queue") //naziv queue-a
	public void consume(String json) {
		try {
			System.out.println(json);
			ObjectMapper om = new ObjectMapper();
			DeleteFlightListenerForm form = om.readValue(json, DeleteFlightListenerForm.class);
			
			String admin = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(form.getToken().replace(TOKEN_PREFIX, "")).getSubject();
			if (!adminRepo.existsByEmail(admin))
				return;
			
			//user email ne vadimo iz tokena jer je admin logovan u ovoj sesiji, saljemo userid preko jsona
			UserClient user = userRepo.findById(form.getUser());
			RankKorisnika rank = userRepo.findRankKorisnika(user);
			long poeni = rank.getPoeni() - form.getDuzina();			
			RankForm rf = new RankForm(CustomValidation.getRank(poeni), poeni);
			userController.rankUpdater(rank, rf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
