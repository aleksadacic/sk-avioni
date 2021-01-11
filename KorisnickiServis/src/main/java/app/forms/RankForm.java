package app.forms;

public class RankForm {
	
	private String naziv;
	private long poeni;
	
	public RankForm() {
		
	}
	
	public RankForm(String naziv, long poeni) {
		this.naziv = naziv;
		this.poeni = poeni;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public long getPoeni() {
		return poeni;
	}

	public void setPoeni(long poeni) {
		this.poeni = poeni;
	}

}
