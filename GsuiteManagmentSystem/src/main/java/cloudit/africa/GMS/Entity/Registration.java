package cloudit.africa.GMS.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Registration {

	@Id
	private String domain;
	private String fileName;
	private String fileType;
	@Column(length = 10485760)
	private String fileContent;
	@Column(length = 10485760)
	private String fileJsonBase64;
	@Column(length = 10485760)
	private String filep12Base64;
	private String serviceAccountEmail;
	private String projectName;
	private String uploader;
	@OneToOne
	private Package packageSubscription;
	@OneToOne
	private Company company;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getServiceAccountEmail() {
		return serviceAccountEmail;
	}
	public void setServiceAccountEmail(String serviceAccountEmail) {
		this.serviceAccountEmail = serviceAccountEmail;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public Package getPackageSubscription() {
		return packageSubscription;
	}
	public void setPackageSubscription(Package packageSubscription) {
		this.packageSubscription = packageSubscription;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getFileJsonBase64() {
		return fileJsonBase64;
	}
	public void setFileJsonBase64(String fileJsonBase64) {
		this.fileJsonBase64 = fileJsonBase64;
	}
	public String getFilep12Base64() {
		return filep12Base64;
	}
	public void setFilep12Base64(String filep12Base64) {
		this.filep12Base64 = filep12Base64;
	}


}
