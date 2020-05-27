package cloudit.africa.GMS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GmsFile {

	
	@Id
	@GeneratedValue
	private Integer id;
	private String fileId;
	private String fileName;
	private String permisionId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "GmsFile [id=" + id + ", fileId=" + fileId + ", fileName=" + fileName + "]";
	}
	public String getPermisionId() {
		return permisionId;
	}
	public void setPermisionId(String permisionId) {
		this.permisionId = permisionId;
	}
	
}
