package cloudit.africa.GMS.Model;

public class CalenderAppointment {
	
	private String  id;
	private String  email;
	private String  name;
	private String orgUnit;
	private boolean active;
	private String updateDate;
	
	public CalenderAppointment(String id, String email, String name, boolean active, String updateDate,String orgUnit) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.active = active;
		this.updateDate = updateDate;
		this.orgUnit=orgUnit;
	}
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}


	@Override
	public String toString() {
		return "CalenderAppointment [id=" + id + ", email=" + email + ", name=" + name + ", active=" + active
				+ ", updateDate=" + updateDate + "]";
	}


	public String getOrgUnit() {
		return orgUnit;
	}


	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}
	
	

}
