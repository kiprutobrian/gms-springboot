package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SignatureTemplate {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@Column(length = 10485760)
	private String signatureBody;
	private Date createdDate;
	private String createdBy;
	private Date updateDate;
	private String updateBy;
	@ManyToOne
	private Company company;
	private Boolean isActive;
	private Boolean isDeletable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignatureBody() {
		return signatureBody;
	}

	public void setSignatureBody(String signatureBody) {
		this.signatureBody = signatureBody;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeletable() {
		if (isActive) {
			return false;
		} else {
			return true;
		}

	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

}
