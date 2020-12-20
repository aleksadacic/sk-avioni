package app.forms;

public class KarticaForm {
	
	private String ime;
	private String prezime;
	private String brojKartice;
	private String sigurnosniBroj;
	
	public KarticaForm() {
		
	}

	public KarticaForm(String ime, String prezime, String brojKartice, String sigurnosniBroj) {
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
	
}
