package models;

import java.util.List;

public class Doctor {
	int id;  // Patient ID
	int patientId;
	String email;
	String password;
	String lastName;
	String firstName;
	String workAddress;
	String certification;
	String phone;
	List<Message>messages;
	public Doctor() {}
	
	public Doctor (int id, String email, String password, String lastName, String firstName,
				String workAddress, String certification) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.workAddress = workAddress;
		this.certification = certification;
	}
	public void setMessages(List<Message>messages)
	{
		this.messages=messages;
	}
	public List<Message>getMessages()
	{
		return messages;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getWorkAddress() {
		return this.workAddress;
	}
	
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	
	public String getCertification() {
		return this.certification;
	}
	
	public void setCertification(String certification) {
		this.certification = certification;
	}
	
	@Override
	public String toString() {
		return "Doctor [id="+id+", email=" + email + ", password=" + password + ", lastName=" + lastName + ", firstName="
				+ firstName + ", workAddress=" + workAddress + ", certification=" + certification + "]";
	}
	
}
