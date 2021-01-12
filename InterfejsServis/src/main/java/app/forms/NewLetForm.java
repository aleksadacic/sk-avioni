package app.forms;

public class NewLetForm {

	private long id;
	
	private String pocetnaDestinacija;
	private String krajnjaDestinacija;
	private String duzinaLeta;
	private String avionid;
	private String cena;
	
	public NewLetForm() {}
	
	public NewLetForm(String avionid, String pocetnaDestinacija, String krajnjaDestinacija, String duzinaLeta, String cena) {
		super();
		this.avionid = avionid;
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

	public String getAvionid() {
		return avionid;
	}

	public void setAvionid(String avionid) {
		this.avionid = avionid;
	}

	public String getPocetnaDestinacija() {
		return pocetnaDestinacija;
	}

	public void setPocetnaDestinacija(String pocetnaDestinacija) {
		this.pocetnaDestinacija = pocetnaDestinacija;
	}

	public String getDuzinaLeta() {
		return duzinaLeta;
	}

	public void setDuzinaLeta(String duzinaLeta) {
		this.duzinaLeta = duzinaLeta;
	}

	public String getCena() {
		return cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}
	
	public String getKrajnjaDestinacija() {
		return krajnjaDestinacija;
	}

	public void setKrajnjaDestinacija(String krajnjaDestinacija) {
		this.krajnjaDestinacija = krajnjaDestinacija;
	}

}
