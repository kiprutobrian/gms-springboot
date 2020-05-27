package cloudit.africa.GMS.Model;

public class Appointment {
	
	private String myEmail;
	private String title;
	private String fromDate;
	private String todate;
	private String firstName;
	private String lastName;
	private String company;
	private String email;
	private String phonenumber;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	@Override
	public String toString() {
		return "Appointment [title=" + title + ", fromDate=" + fromDate + ", todate=" + todate + ", firstName="
				+ firstName + ", lastName=" + lastName + ", company=" + company + ", email=" + email + ", phonenumber="
				+ phonenumber + "]";
	}
	public String getMyEmail() {
		return myEmail;
	}
	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}
	
	

}
