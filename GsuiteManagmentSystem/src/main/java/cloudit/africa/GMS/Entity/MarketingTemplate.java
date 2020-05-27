package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MarketingTemplate {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@Column(length = 10485760)
	private String marketingBody;
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
	public String getMarketingBody() {
		return marketingBody;
	}
	public void setMarketingBody(String marketingBody) {
		this.marketingBody = marketingBody;
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
		return isDeletable;
	}
	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	
	
}
