package cloudit.africa.GMS.Model;

public class OrgDriveFiles {

	private String id;
	private String fileName;
	private String fileType;
	private String fileIcone;
	private String fileSize;
	private String createdDate;
	private String mordifiedDate;
	private String sharedWithEmailAdress;
	private boolean sharedOutside; 
	private String emailAddress;
	private String owner;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFileIcone() {
		return fileIcone;
	}
	public void setFileIcone(String fileIcone) {
		this.fileIcone = fileIcone;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getMordifiedDate() {
		return mordifiedDate;
	}
	public void setMordifiedDate(String mordifiedDate) {
		this.mordifiedDate = mordifiedDate;
	}
	public String getSharedWithEmailAdress() {
		return sharedWithEmailAdress;
	}
	public void setSharedWithEmailAdress(String sharedWithEmailAdress) {
		this.sharedWithEmailAdress = sharedWithEmailAdress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public boolean isSharedOutside() {
		return sharedOutside;
	}
	public void setSharedOutside(boolean sharedOutside) {
		this.sharedOutside = sharedOutside;
	}

}
