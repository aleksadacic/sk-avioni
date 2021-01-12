package app.forms;

public class KupiLet {

	private Long flightId;
	private Long cardId;
	
	public KupiLet() {
		// TODO Auto-generated constructor stub
	}
	
	public KupiLet(Long flightId, Long cardId) {
		super();
		this.flightId = flightId;
		this.cardId = cardId;
	}
	
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

}
