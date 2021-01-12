package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Let {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="avion")
	private Avion avion;
	
	private String pocetnaDestinacija;
	private String krajnjaDestinacija;
	private Double duzinaLeta;
	private Double cena;
	
	//dodatni parametri
	private int prodateKarte;
	private boolean ponisten;
	
	public Let() {}
	
	public Let(Avion avion, String pocetnaDestinacija, String krajnjaDestinacija, Double duzinaLeta, double cena) {
		super();
		this.avion = avion;
		this.pocetnaDestinacija = pocetnaDestinacija;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.duzinaLeta = duzinaLeta;
		this.cena = cena;
		this.prodateKarte = 0;
		this.ponisten = false;
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
	public int getProdateKarte() {
		return prodateKarte;
	}
	public void setProdateKarte(int prodateKarte) {
		this.prodateKarte = prodateKarte;
	}
	public boolean isPonisten() {
		return ponisten;
	}
	public void setPonisten(boolean ponisten) {
		this.ponisten = ponisten;
	}

	@Override
	public String toString() {
		return pocetnaDestinacija + "-" + krajnjaDestinacija;
	}
	
}
