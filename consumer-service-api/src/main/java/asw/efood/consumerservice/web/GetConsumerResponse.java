package asw.efood.consumerservice.web;

public class GetConsumerResponse {

	private String consumerId;
	
	private String firstName; 
	private String lastName;

	public GetConsumerResponse() {
	}

	public GetConsumerResponse(String consumerId, String firstName, String lastName) {
		this.consumerId = consumerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

