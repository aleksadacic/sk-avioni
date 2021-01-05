package app.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UserClient extends User {

	private String pasos;
	private String ime;
	private String prezime;

	@OneToOne
	@JoinColumn(name = "rankKorisnika")
	private RankKorisnika rankKorisnika;

	@OneToMany(targetEntity = KreditnaKartica.class)
	private List<KreditnaKartica> kreditneKartice;

	public UserClient() {
	}

	public UserClient(String email, String sifra, String ime, String prezime, String pasos, RankKorisnika rank) {
		super(email, sifra);
		this.ime = ime;
		this.prezime = prezime;
		this.pasos = pasos;
		this.rankKorisnika = rank;
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

	public List<KreditnaKartica> getKreditneKartice() {
		return kreditneKartice;
	}

	public void setKreditneKartice(List<KreditnaKartica> kreditneKartice) {
		this.kreditneKartice = kreditneKartice;
	}

	public RankKorisnika getRankKorisnika() {
		return rankKorisnika;
	}

	public void setRankKorisnika(RankKorisnika rankKorisnika) {
		this.rankKorisnika = rankKorisnika;
	}

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}

}
