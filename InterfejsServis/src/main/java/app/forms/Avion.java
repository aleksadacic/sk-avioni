package app.forms;

public class Avion {

	private long id;
	
	private String naziv;
	private int kapacitet;
	
	public Avion() {}
	
	public Avion(String naziv, int kapacitet) {
		super();
		this.naziv = naziv;
		this.kapacitet = kapacitet;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	
	@Override
	public String toString() {
		return naziv;
	}
	
}
