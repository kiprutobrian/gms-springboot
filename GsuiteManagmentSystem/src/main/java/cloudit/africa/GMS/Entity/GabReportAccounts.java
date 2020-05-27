package cloudit.africa.GMS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GabReportAccounts {
	
	
	@Id
	private String id;
	private String emailAddress;
	
	protected GabReportAccounts() {
	}
	public GabReportAccounts(String id, String emailAddress) {
		super();
		this.id = id;
		this.emailAddress = emailAddress;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	

}
