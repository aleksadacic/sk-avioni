package app.forms;

public class LetForm {
	
	private long avionid;
	private String pocetnaDestinacija;
	private String krajnjaDestinacija;
	private Double duzinaLeta;
	private double cena;

	public LetForm() {}
	
	public LetForm( String pocetnaDestinacija, String krajnjaDestinacija, Double duzinaLeta, double cena) {
		this.pocetnaDestinacija = pocetnaDestinacija;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.duzinaLeta = duzinaLeta;
		this.cena = cena;
	}
	
	public String getPocetnaDestinacija() {
		return pocetnaDestinacija;
	}

	public void setPocetnaDestinacija(String pocetnaDestinacija) {
		this.pocetnaDestinacija = pocetnaDestinacija;
	}

	public Double getDuzinaLeta() {
		return duzinaLeta;
	}

	public void setDuzinaLeta(Double duzinaLeta) {
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

	public long getAvionid() {
		return avionid;
	}

	public void setAvionid(long avionid) {
		this.avionid = avionid;
	}

}
