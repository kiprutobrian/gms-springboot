package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Package {

	@Id
	@GeneratedValue
	private Integer Id;
	private String name;
	private String descrption;	
	private Date createdDate;
	private Integer createdBy;
	private Date updateDate;
	private Integer updateBy;
	@ManyToOne
	private Services Services;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Services getServices() {
		return Services;
	}
	public void setServices(Services services) {
		Services = services;
	}
	@Override
	public String toString() {
		return "Package [Id=" + Id + ", name=" + name + ", descrption=" + descrption + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", updateDate=" + updateDate + ", updateBy=" + updateBy + ", Services="
				+ Services + "]";
	}
	
	 
	
	
}
