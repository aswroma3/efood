package asw.efood.consumerservice.domain;

import javax.persistence.*;

@Entity
public class Consumer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String firstName; 
	private String lastName; 
	
	private Consumer() {}
	
	public Consumer(String firstName, String lastName) {
		this.firstName = firstName; 
		this.lastName = lastName;
		/* se necessario, assegna l'id */
		/* this.id = "CUST-" + UUID.randomUUID().toString(); */
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Consumer consumer = (Consumer) o;

		return id != null ? id.equals(consumer.id) : consumer.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public static Consumer create(String firstName, String lastName) {
		return new Consumer(firstName, lastName);
	}

}

