package app.forms;

import app.entities.RankKorisnika;

public class UserInfo {
	
	private String ime;
	private String prezime;
	private RankKorisnika rankKorisnika;

	public UserInfo() {
	}

	public UserInfo(String ime, String prezime, RankKorisnika rankKorisnika) {
		this.ime = ime;
		this.prezime = prezime;
		this.rankKorisnika = rankKorisnika;
	}
	
	public RankKorisnika getRankKorisnika() {
		return rankKorisnika;
	}

	public void setRankKorisnika(RankKorisnika rankKorisnika) {
		this.rankKorisnika = rankKorisnika;
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
}
