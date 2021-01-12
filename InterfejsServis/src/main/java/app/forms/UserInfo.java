package app.forms;

public class UserInfo {
	
	private String ime;
	private String prezime;
	private String rankNaziv;
	private String rankPoeni;
	private String email;
	private String pasos;
	private long id;
	
	public UserInfo() {
	}

	public UserInfo(String ime, String prezime, String rankNaziv, String rankPoeni, String email, String pasos) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.pasos = pasos;
		this.rankNaziv = rankNaziv;
		this.rankPoeni = rankPoeni;
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

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}

	public String getRankNaziv() {
		return rankNaziv;
	}

	public void setRankNaziv(String rankNaziv) {
		this.rankNaziv = rankNaziv;
	}

	public String getRankPoeni() {
		return rankPoeni;
	}

	public void setRankPoeni(String rankPoeni) {
		this.rankPoeni = rankPoeni;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
