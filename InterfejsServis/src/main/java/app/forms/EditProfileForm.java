package app.forms;

public class EditProfileForm {
	
	private String ime;
	private String prezime;
	private String password;
	private String pasos;
	private String email;
	
	public EditProfileForm() {}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}
	
	

}
