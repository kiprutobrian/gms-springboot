package cloudit.africa.GMS.Model;

import java.io.Serializable;

public class Appointments implements Serializable {
	
	private String id;
	private String email;
	private Boolean appointmentSlot;
	
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
	public Boolean getAppointmentSlot() {
		return appointmentSlot;
	}
	public void setAppointmentSlot(Boolean appointmentSlot) {
		this.appointmentSlot = appointmentSlot;
	}
	@Override
	public String toString() {
		return "Appointments [id=" + id + ", email=" + email + "]";
	}
	
	

}
