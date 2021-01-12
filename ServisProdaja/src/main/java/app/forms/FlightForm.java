package app.forms;

public class FlightForm {
	private Long userId;
	private String userRank;
	private Long flightId;
	private Long cardId;
	private Double points;
	
	public FlightForm(Long userId, String userRank, Long flightId, Double points) {
		super();
		this.userId = userId;
		this.userRank = userRank;
		this.flightId = flightId;
		this.points = points;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserRank() {
		return userRank;
	}
	public void setUserRank(String userRank) {
		this.userRank = userRank;
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
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	
}
