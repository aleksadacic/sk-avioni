package app.forms;


public class CreditCard {
	
	private Long id;
	private String ime;
	private String prezime;
	private String brojKartice;
	private String sigurnosniBroj; //sigurnosni broj od 3 cifre
	
	public CreditCard() {
		
	}

	public CreditCard(String ime, String prezime, String brojKartice, String sigurnosniBroj) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.brojKartice = brojKartice;
		this.sigurnosniBroj = sigurnosniBroj;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
