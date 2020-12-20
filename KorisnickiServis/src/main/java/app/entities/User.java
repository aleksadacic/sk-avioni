package app.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String ime;
	private String prezime;
	private String email;
	private String sifra;
	private String pasos;
	
	@OneToOne
	@JoinColumn(name = "rankKorisnika")
	private RankKorisnika rankKorisnika;

	@OneToMany(targetEntity = KreditnaKartica.class)
	private List<KreditnaKartica> kreditneKartice;

	public User() {

	}

	public User(String ime, String prezime, String email, String sifra, String pasos, RankKorisnika rankKorisnika) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.sifra = sifra;
		this.pasos = pasos;
		this.rankKorisnika = rankKorisnika;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}

}
