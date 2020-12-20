package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KreditnaKartica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String ime;
	private String prezime;
	private String brojKartice;
	private String sigurnosniBroj; //sigurnosni broj od 3 cifre
	
	public KreditnaKartica() {
		
	}

	public KreditnaKartica(String ime, String prezime, String brojKartice, String sigurnosniBroj) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.brojKartice = brojKartice;
		this.sigurnosniBroj = sigurnosniBroj;
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

	public String getBrojKartice() {
		return brojKartice;
	}

	public void setBrojKartice(String brojKartice) {
		this.brojKartice = brojKartice;
	}

	public String getSigurnosniBroj() {
		return sigurnosniBroj;
	}

	public void setSigurnosniBroj(String sigurnosniBroj) {
		this.sigurnosniBroj = sigurnosniBroj;
	}
	
	
}
