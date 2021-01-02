package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Let {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Avion avion;
	private String pocetnaDestinacija;
	private String krajnjaDestincija;
	private String duzinaLeta;
	private String cena;
	
	public Let() {}
	
	public Let(Avion avion, String pocetnaDestinacija, String krajnjaDestincija, String duzinaLeta, String cena) {
		super();
		this.avion = avion;
		this.pocetnaDestinacija = pocetnaDestinacija;
		this.krajnjaDestincija = krajnjaDestincija;
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

	public String getKrajnjaDestincija() {
		return krajnjaDestincija;
	}

	public void setKrajnjaDestincija(String krajnjaDestincija) {
		this.krajnjaDestincija = krajnjaDestincija;
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

}
