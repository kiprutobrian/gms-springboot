package cloudit.africa.GMS.Controller.CalenderNew;

public class Booking {

	private Integer id;
	private String myEmail;
	private Long selectedDate;
	private String selectedTo;
	private String titile;
	private String email;
	private String name;	
	private String phoneNumber;
	private String organisation;
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMyEmail() {
		return myEmail;
	}
	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}
	public Long getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(Long selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getSelectedTo() {
		return selectedTo;
	}
	public void setSelectedTo(String selectedTo) {
		this.selectedTo = selectedTo;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		return "Booking [Id=" + id + ", myEmail=" + myEmail + ", selectedDate=" + selectedDate + ", selectedTo="
				+ selectedTo + ", titile=" + titile + ", email=" + email + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", description=" + description + "]";
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

}
