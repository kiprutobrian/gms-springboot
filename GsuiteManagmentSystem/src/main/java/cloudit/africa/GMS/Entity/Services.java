package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Services {

	@Id
	@GeneratedValue
	private Integer Id;
	private boolean signature;
	private boolean marketingBranding;
	private boolean maildelegation;
	private boolean driveAnalysis;
	private boolean calenderApointment;
	private boolean emailAnalysis;
	private boolean userManegment;
	private boolean setting;
	private boolean dataMigration;
	private boolean reporting;
	private boolean hr;
	private boolean registration;
	private boolean billing;
	private boolean businessCom;
	private Date createdDate;
	private Integer createdBy;
	private Date updateDate;
	private Integer updateBy;

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

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public boolean isSignature() {
		return signature;
	}

	public void setSignature(boolean signature) {
		this.signature = signature;
	}

	public boolean isMarketingBranding() {
		return marketingBranding;
	}

	public void setMarketingBranding(boolean marketingBranding) {
		this.marketingBranding = marketingBranding;
	}

	public boolean isMaildelegation() {
		return maildelegation;
	}

	public void setMaildelegation(boolean maildelegation) {
		this.maildelegation = maildelegation;
	}

	public boolean isDriveAnalysis() {
		return driveAnalysis;
	}

	public void setDriveAnalysis(boolean driveAnalysis) {
		this.driveAnalysis = driveAnalysis;
	}

	public boolean isCalenderApointment() {
		return calenderApointment;
	}

	public void setCalenderApointment(boolean calenderApointment) {
		this.calenderApointment = calenderApointment;
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

	public boolean isBusinessCom() {
		return businessCom;
	}

	public void setBusinessCom(boolean businessCom) {
		this.businessCom = businessCom;
	}

	public boolean isReporting() {
		return reporting;
	}

	public void setReporting(boolean reporting) {
		this.reporting = reporting;
	}

	public boolean isDataMigration() {
		return dataMigration;
	}

	public void setDataMigration(boolean dataMigration) {
		this.dataMigration = dataMigration;
	}

	@Override
	public String toString() {
		return "Services [Id=" + Id + ", signature=" + signature + ", marketingBranding=" + marketingBranding
				+ ", maildelegation=" + maildelegation + ", driveAnalysis=" + driveAnalysis + ", calenderApointment="
				+ calenderApointment + ", emailAnalysis=" + emailAnalysis + ", userManegment=" + userManegment
				+ ", setting=" + setting + ", dataMigration=" + dataMigration + ", reporting=" + reporting + ", hr="
				+ hr + ", registration=" + registration + ", billing=" + billing + ", businessCom=" + businessCom
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updateDate=" + updateDate
				+ ", updateBy=" + updateBy + "]";
	}
	
	
	
	
	
	
}
