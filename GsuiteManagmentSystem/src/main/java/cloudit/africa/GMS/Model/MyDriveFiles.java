package cloudit.africa.GMS.Model;

public class MyDriveFiles {

	private String id;
	private String fileName;
	private String fileType;
	private String fileIcone;
	private String fileSize;
	private String createdDate;

	private String mordifiedDate;
	private String sharedWithEmailAdress;
	private String emailAddress;
	private String owner;
	private String permissionId;

	public MyDriveFiles() {
	}

	public MyDriveFiles(String id, String fileName, String fileType, String fileIcone, String fileSize,
			String createdDate,String permissionId) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.createdDate = createdDate;
		this.fileType = fileType;
		this.fileIcone = fileIcone;
		this.fileSize = fileSize;
		this.setPermissionId(permissionId);

	}


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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "MyDriveFiles [id=" + id + ", fileName=" + fileName + ", createdDate=" + createdDate + ", mordifiedDate="
				+ mordifiedDate + ", owner=" + owner + "]";
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

	public String getFileType() {

		String[] filetypeApp = fileType.split("application/");

//		System.out.println("1 " + filetypeApp[0]);
//		System.out.println("2 " + filetypeApp[1]);

//		if (filetypeApp[1].length() > 16) {
//
//			System.out.println("3 " + filetypeApp[1].split("vnd.google-apps.")[0]);
//			return "" + filetypeApp[1].substring(16);
//		} else {
//			return filetypeApp[1];
//		}
		
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

		if (fileSize.isEmpty()) {
			return " ";
		} else
			return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

}
