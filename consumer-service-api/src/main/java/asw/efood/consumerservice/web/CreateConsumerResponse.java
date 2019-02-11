package asw.efood.consumerservice.web;

public class CreateConsumerResponse {

	private String consumerId;

	public CreateConsumerResponse() {
	}

	public CreateConsumerResponse(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
}

