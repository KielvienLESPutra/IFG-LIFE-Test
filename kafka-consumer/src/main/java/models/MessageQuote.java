package models;

public class MessageQuote {
	private Integer customerId;
	private Integer temporaryId;

	public MessageQuote(Integer customerId, Integer temporaryId) {
		this.customerId = customerId;
		this.temporaryId = temporaryId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getTemporaryId() {
		return temporaryId;
	}

	public void setTemporaryId(Integer temporaryId) {
		this.temporaryId = temporaryId;
	}
}
