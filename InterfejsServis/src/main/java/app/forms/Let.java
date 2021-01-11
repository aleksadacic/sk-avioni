package app.forms;


public class Let {
	
	private long id;
	
	private Avion avion;
	
	private String pocetnaDestinacija;
	private String krajnjaDestinacija;
	private long duzinaLeta;
	private double cena;
	
	public Let() {}
	
	public Let(Avion avion, String pocetnaDestinacija, String krajnjaDestinacija, long duzinaLeta, double cena) {
		super();
		this.avion = avion;
		this.pocetnaDestinacija = pocetnaDestinacija;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.duzinaLeta = duzinaLeta;
		this.cena = cena;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public String getPocetnaDestinacija() {
		return pocetnaDestinacija;
	}

	public void setPocetnaDestinacija(String pocetnaDestinacija) {
		this.pocetnaDestinacija = pocetnaDestinacija;
	}

	public long getDuzinaLeta() {
		return duzinaLeta;
	}

	public void setDuzinaLeta(long duzinaLeta) {
		this.duzinaLeta = duzinaLeta;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public String getKrajnjaDestinacija() {
		return krajnjaDestinacija;
	}

	public void setKrajnjaDestinacija(String krajnjaDestinacija) {
		this.krajnjaDestinacija = krajnjaDestinacija;
	}

	@Override
	public String toString() {
		return pocetnaDestinacija + "-" + krajnjaDestinacija;
	}
}