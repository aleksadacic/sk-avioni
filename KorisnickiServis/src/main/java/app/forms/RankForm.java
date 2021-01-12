package app.forms;

public class RankForm {
	
	private String naziv;
	private int poeni;
	
	public RankForm() {
		
	}
	
	public RankForm(String naziv, int poeni) {
		this.naziv = naziv;
		this.poeni = poeni;
	}

	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getPoeni() {
		return poeni;
	}
	public void setPoeni(int poeni) {
		this.poeni = poeni;
	}

}
