package app.forms;

public class AvionForm {
	private String naziv;
	private int kapacitet;
	
	public AvionForm(String naziv, int kapacitet) {
		this.naziv = naziv;
		this.kapacitet = kapacitet;
	}
	
	public AvionForm() {}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}
	
	
}
