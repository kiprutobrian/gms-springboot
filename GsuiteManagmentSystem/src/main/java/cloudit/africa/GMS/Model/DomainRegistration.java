package cloudit.africa.GMS.Model;


public class DomainRegistration {

	private String userId;
	private String packageId;
	private String base64JsonFile;
	private String base64P12File;
	private String companyId;
	private String domain;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getBase64JsonFile() {
		return base64JsonFile;
	}
	public void setBase64JsonFile(String base64JsonFile) {
		this.base64JsonFile = base64JsonFile;
	}
	public String getBase64P12File() {
		return base64P12File;
	}
	public void setBase64P12File(String base64p12File) {
		base64P12File = base64p12File;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
