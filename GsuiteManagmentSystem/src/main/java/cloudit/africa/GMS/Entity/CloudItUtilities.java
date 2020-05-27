package cloudit.africa.GMS.Entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CloudItUtilities {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String value;
	private String updatedBy;
	private Date updateOn;
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	
}
