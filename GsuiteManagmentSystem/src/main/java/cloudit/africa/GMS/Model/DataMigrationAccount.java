package cloudit.africa.GMS.Model;

public class DataMigrationAccount {

	private String accountId;
	private String emailAddress;
	private boolean email;
	private boolean drive;
	private String backupaccount;
	
	
	public boolean isEmail() {
		return email;
	}
	public void setEmail(boolean email) {
		this.email = email;
	}
	public boolean isDrive() {
		return drive;
	}
	public void setDrive(boolean drive) {
		this.drive = drive;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getEmailAddress() {
		return emailAddress.replaceAll("^\"|\"$", "");
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getBackupaccount() {
		return backupaccount;
	}
	public void setBackupaccount(String backupaccount) {
		this.backupaccount = backupaccount;
	}
	@Override
	public String toString() {
		return "DataMigrationAccount [accountId=" + accountId + ", emailAddress=" + emailAddress + ", email=" + email
				+ ", drive=" + drive + ", backupaccount=" + backupaccount + "]";
	}
	
	
	
}
