package cloudit.africa.GMS.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrganizationMembers implements Serializable {

	@Id
	private String memberId;
	private String emailAdress;
	private Integer company;
	@GeneratedValue
	private String fileId; 
	

	
	public OrganizationMembers() {
	}


	public OrganizationMembers(String memberId, String emailAdress, Integer company) {
		super();
		this.memberId = memberId;
		this.emailAdress = emailAdress;
		this.company = company;
	}

	public String getMemberId() {
		return memberId.replace("\"", "");
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEmailAdress() {
		return emailAdress.replace("\"", "");
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	@Override
	public String toString() {
		return "OrganizationMembers [memberId=" + memberId + ", emailAdress=" + emailAdress + ", company=" + company
				+ ", fileId=" + fileId + "]";
	}
	
	

}
