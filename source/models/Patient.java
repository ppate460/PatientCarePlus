package models;

import java.util.List;

public class Patient {
	int id;
	String username;
	String email;
	String password;
	String lastName;
	String firstName;
	String emergencyPhone;
	String emergencyContact;
	String doctorsNotes;
	List<Message>messages;
	public Patient() {}
	public Patient(int id,String email, String password, String lastName, String firstName, String emergencyPhone,
			String emergencyContact, String doctorsNotes) {
		super();
		this.id=id;
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.emergencyPhone = emergencyPhone;
		this.emergencyContact = emergencyContact;
		this.doctorsNotes = doctorsNotes;
	}
	public void setMessages(List<Message>messages)
	{
		this.messages=messages;
	}
	public List<Message>getMessages()
	{
		return messages;
	}
	public void setId (int id)
	{
		this.id=id;
	}
	public int getId() {
		return this.id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getEmergencyPhone() {
		return emergencyPhone;
	}
	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getDoctorsNotes() {
		return doctorsNotes;
	}
	public void setDoctorsNotes(String doctorsNotes) {
		this.doctorsNotes = doctorsNotes;
	}
	@Override
	public String toString() {
		return "Patient [id="+id+", email=" + email + ", password=" + password + ", lastName=" + lastName + ", firstName="
				+ firstName + ", emergencyPhone=" + emergencyPhone + ", emergencyContact=" + emergencyContact
				+ ", doctorsNotes=" + doctorsNotes + "]";
	}
	
}
