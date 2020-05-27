package cloudit.africa.GMS.Model;

public class myContact {

	private String emailAddress;
	private String displayName;
	
	
	public myContact(String emailAddress, String displayName) {
		super();
		this.emailAddress = emailAddress;
		this.displayName = displayName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
		
}
