package app.tools;

import javax.mail.internet.InternetAddress;

import app.entities.RankType;
import app.forms.KarticaForm;
import app.forms.RankForm;
import app.forms.RegistrationForm;

public class CustomValidation {

	public static boolean isOnlyNumbers(String... str) {
		for (String s : str) {
			if (!s.matches("[0-9]+"))
				return false;
		}
		return true;
	}

	public static boolean isOnlyLetters(String... str) {
		for (String s : str) {
			if (!s.matches("[a-zA-Z ]+"))
				return false;
		}
		return true;
	}
	
	public static boolean validateEmail(String email) {
		if (email.length() <= 0)
			return false;
		try {
			InternetAddress e = new InternetAddress(email);
			e.validate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateUser(RegistrationForm rf) {
		if (isOnlyLetters(rf.getIme(), rf.getPrezime()) && validateEmail(rf.getEmail()) && rf.getPasos().length() > 0 && rf.getPassword().length() > 0)
			return true;
		return false;
	}

	public static boolean validateRank(RankForm rf) {
		if (rf.getNaziv().equals(RankType.BRONZA) && rf.getPoeni() < 1000)
			return true;
		if (rf.getNaziv().equals(RankType.SREBRO) && rf.getPoeni() >= 1000 && rf.getPoeni() < 10000)
			return true;
		if (rf.getNaziv().equals(RankType.ZLATO) && rf.getPoeni() >= 10000)
			return true;
		return false;
	}
	
	public static String getRank(long poeni) {
		if (poeni < 1000)
			return RankType.BRONZA;
		if (poeni >= 1000 && poeni < 10000)
			return RankType.SREBRO;
		if (poeni >= 10000)
			return RankType.ZLATO;
		return null;
	}

	public static boolean validateCard(KarticaForm kf) {
		if (isOnlyLetters(kf.getIme(), kf.getPrezime()) && isOnlyNumbers(kf.getBrojKartice(), kf.getSigurnosniBroj()) && kf.getSigurnosniBroj().length() == 3)
			return true;
		return false;
	}
}
