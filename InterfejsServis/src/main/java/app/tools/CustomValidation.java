package app.tools;

import app.forms.CreditCard;
import app.forms.RegisterForm;

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

	public static boolean validateUser(RegisterForm rf) {
		if (isOnlyLetters(rf.getIme(), rf.getPrezime()) && rf.getPasos().length() > 0 && rf.getPassword().length() > 0)
			return true;
		return false;
	}

	public static boolean validateCard(CreditCard kf) {
		if (isOnlyLetters(kf.getIme(), kf.getPrezime()) && isOnlyNumbers(kf.getBrojKartice(), kf.getSigurnosniBroj()) && kf.getSigurnosniBroj().length() == 3)
			return true;
		return false;
	}
}
