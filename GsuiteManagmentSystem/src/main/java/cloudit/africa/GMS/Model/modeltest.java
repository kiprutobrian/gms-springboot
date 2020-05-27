package cloudit.africa.GMS.Model;

import java.io.Serializable;

public class modeltest implements Serializable{

	private String id;
	private String email;
	private String fileId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	@Override
	public String toString() {
		return "modeltest [id=" + id + ", email=" + email + ", fileId=" + fileId + "]";
	}
	
	
	
	
	
	
}
