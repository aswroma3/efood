package asw.efood.consumerservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConsumerResponse {

	private Long consumerId;
	
	private String firstName; 
	private String lastName;

}

