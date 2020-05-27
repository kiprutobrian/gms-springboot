package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CalenderAppointmentSlot {
	
	@Id
	private String id;
	@ManyToOne
	private Company company;
	private Boolean appointmentActive;
	@ManyToOne
	private UserApp updatedBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt=new Date();
	
	
	public Company getCompany() {
		return company;
	}
	public void setCompanyId(Company company) {
		this.company = company;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Boolean getAppointmentActive() {
		return appointmentActive;
	}
	public void setAppointmentActive(Boolean appointmentActive) {
		this.appointmentActive = appointmentActive;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	

}
