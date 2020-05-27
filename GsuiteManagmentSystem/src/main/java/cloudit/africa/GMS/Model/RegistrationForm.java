package cloudit.africa.GMS.Model;

import cloudit.africa.GMS.Entity.Company;

public class RegistrationForm {

	private String userId;
	private String packageName;
	private String base64File;
	private String projectName;
	private String serviceAccountEmail;
	private Company company;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getBase64File() {
		return base64File;
	}
	public void setBase64File(String base64File) {
		this.base64File = base64File;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getServiceAccountEmail() {
		return serviceAccountEmail;
	}
	public void setServiceAccountEmail(String serviceAccountEmail) {
		this.serviceAccountEmail = serviceAccountEmail;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
}
