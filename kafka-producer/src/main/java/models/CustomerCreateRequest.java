package models;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerCreateRequest {
	@NotBlank(message = "first name cannot be blank")
	private String firstName;

	@NotBlank(message = "first name cannot be blank")
	private String lastName;

	@NotBlank(message = "email cannot be blank")
	private String email;
	
	@NotBlank(message = "address cannot be blank")
	private String address;
	
	@NotBlank(message = "address cannot be blank")
	private String gender;
	
	@NotNull(message = "Tanggal tidak boleh null")
	private Date dob;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public CustomerCreateRequest(String firstName, String lastName, String email, String address, String gender,
			Date dob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
	}

	public CustomerCreateRequest() {

	}

	@Override
	public String toString() {
		return "CustomerCreateRequest [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", address=" + address + ", gender=" + gender + ", dob=" + dob + "]";
	}

}
