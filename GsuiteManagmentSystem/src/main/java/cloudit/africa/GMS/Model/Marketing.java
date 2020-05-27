package cloudit.africa.GMS.Model;

import java.util.Date;

public class Marketing {
	
	private Integer id;
	private String marketingTemplate;
	private String marketingName;
	private Date createdDate;
	private String createdBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMarketingTemplate() {
		return marketingTemplate;
	}
	public void setMarketingTemplate(String marketingTemplate) {
		this.marketingTemplate = marketingTemplate;
	}
	public String getMarketingName() {
		return marketingName;
	}
	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
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
	
	

}
