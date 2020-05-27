package cloudit.africa.GMS.Model;

public class myUser {

	private String familyName;
	private String otherName;
	private String emailName;
	private String phoneNumberOne;
	private String phoneNumberTwo;
	private String jobPosition;
	private String defaultPassword;
	private Boolean changePasswordAtNextLogin;
	private String cretedById;
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getPhoneNumberOne() {
		return phoneNumberOne;
	}

	public void setPhoneNumberOne(String phoneNumberOne) {
		this.phoneNumberOne = phoneNumberOne;
	}

	public String getPhoneNumberTwo() {
		return phoneNumberTwo;
	}

	public void setPhoneNumberTwo(String phoneNumberTwo) {
		this.phoneNumberTwo = phoneNumberTwo;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public Boolean getChangePasswordAtNextLogin() {
		return changePasswordAtNextLogin;
	}

	public void setChangePasswordAtNextLogin(Boolean changePasswordAtNextLogin) {
		this.changePasswordAtNextLogin = changePasswordAtNextLogin;
	}

	public String getCretedById() {
		return cretedById;
	}

	public void setCretedById(String cretedById) {
		this.cretedById = cretedById;
	}

}
