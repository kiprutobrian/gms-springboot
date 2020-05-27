package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoleAccess {
	
	@Id
	@GeneratedValue
	private Integer id;
	private boolean signature; 
	private boolean markertingBranding;
	private boolean mailDelegation;
	private boolean driveAnalysis;
	private boolean calenderAppointment;
	private boolean emailAnalysis;
	private boolean userManegment;
	private boolean registration;
	private boolean setting;
	private boolean dataMigration;
	private boolean reporting;
	private boolean hr;
	private boolean billing;
	private Date createdDate;
	private Integer createdBy;
	private Date updateDate;
	private Integer updateBy;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isSignature() {
		return signature;
	}
	public void setSignature(boolean signature) {
		this.signature = signature;
	}
	public boolean isMarkertingBranding() {
		return markertingBranding;
	}
	public void setMarkertingBranding(boolean markertingBranding) {
		this.markertingBranding = markertingBranding;
	}
	public boolean isMailDelegation() {
		return mailDelegation;
	}
	public void setMailDelegation(boolean mailDelegation) {
		this.mailDelegation = mailDelegation;
	}
	public boolean isDriveAnalysis() {
		return driveAnalysis;
	}
	public void setDriveAnalysis(boolean driveAnalysis) {
		this.driveAnalysis = driveAnalysis;
	}
	public boolean isCalenderAppointment() {
		return calenderAppointment;
	}
	public void setCalenderAppointment(boolean calenderAppointment) {
		this.calenderAppointment = calenderAppointment;
	}
	public boolean isEmailAnalysis() {
		return emailAnalysis;
	}
	public void setEmailAnalysis(boolean emailAnalysis) {
		this.emailAnalysis = emailAnalysis;
	}
	public boolean isUserManegment() {
		return userManegment;
	}
	public void setUserManegment(boolean userManegment) {
		this.userManegment = userManegment;
	}
	public boolean isSetting() {
		return setting;
	}
	public void setSetting(boolean setting) {
		this.setting = setting;
	}
	public boolean isHr() {
		return hr;
	}
	public void setHr(boolean hr) {
		this.hr = hr;
	}
	public boolean isBilling() {
		return billing;
	}
	public void setBilling(boolean billing) {
		this.billing = billing;
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
	public boolean isRegistration() {
		return registration;
	}
	public void setRegistration(boolean registration) {
		this.registration = registration;
	}
	public boolean isDataMigration() {
		return dataMigration;
	}
	public void setDataMigration(boolean dataMigration) {
		this.dataMigration = dataMigration;
	}
	public boolean isReporting() {
		return reporting;
	}
	public void setReporting(boolean reporting) {
		this.reporting = reporting;
	}	

}
