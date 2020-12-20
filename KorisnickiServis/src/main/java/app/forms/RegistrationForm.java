package app.forms;

public class RegistrationForm {
	private String ime;
	private String prezime;
	private String email;
	private String password;
	private String pasos;

	public RegistrationForm(String ime, String prezime, String email, String password, String pasos) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.password = password;
		this.pasos = pasos;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}
	
}
