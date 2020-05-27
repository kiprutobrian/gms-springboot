package cloudit.africa.GMS.Model;

import java.util.Date;

public class Signature {
	
	private Integer Id;
	private String signatureTemplate;
	private String signatureName;
	private Date createdDate;
	private String createdBy; 
	
	
	public Signature() {}
	
	
	public Signature(String signatureTemplate) {
		super();
		this.signatureTemplate = signatureTemplate;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getSignatureTemplate() {
		return signatureTemplate;
	}
	public void setSignatureTemplate(String signatureTemplate) {
		this.signatureTemplate = signatureTemplate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getSignatureName() {
		return signatureName;
	}


	public void setSignatureName(String signatureName) {
		this.signatureName = signatureName;
	}
	
	

}
