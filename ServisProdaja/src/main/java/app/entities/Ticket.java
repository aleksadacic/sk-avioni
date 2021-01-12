package app.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long flightId;
	private Long userId;
	
	@Temporal(TemporalType.DATE)
	private Date purchaseDate;
	
	private Double price;
	

	public Ticket(Long flightId, Long userId, Date purchaseDate, Double price) {
		super();
		this.flightId = flightId;
		this.userId = userId;
		this.purchaseDate = purchaseDate;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public Long getFlightId() {
		return flightId;
	}

	public Long getUserId() {
		return userId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public Double getPrice() {
		return price;
	}

}
