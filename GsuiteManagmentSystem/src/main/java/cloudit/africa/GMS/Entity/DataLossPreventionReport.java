package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DataLossPreventionReport {

	@Id
	@GeneratedValue
	private Integer id;
	private String keyValue;
	private String reportAddress;
	private Boolean active = false;
	private Date lastReport;
	private String updatedBy;
	private Date updatedOn;
	private Date createdOn;
	@ManyToOne
	private DataLossPreventionReportType dataLossPreventionReportType;
	@ManyToOne
	private Company company;
	private boolean daily;
	private boolean weekly;
	private boolean monthly;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getReportAddress() {
		return reportAddress;
	}
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getLastReport() {
		return lastReport;
	}
	public void setLastReport(Date lastReport) {
		this.lastReport = lastReport;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public DataLossPreventionReportType getDataLossPreventionReportType() {
		return dataLossPreventionReportType;
	}
	public void setDataLossPreventionReportType(DataLossPreventionReportType dataLossPreventionReportType) {
		this.dataLossPreventionReportType = dataLossPreventionReportType;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public boolean isDaily() {
		return daily;
	}
	public void setDaily(boolean daily) {
		this.daily = daily;
	}
	public boolean isWeekly() {
		return weekly;
	}
	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}
	public boolean isMonthly() {
		return monthly;
	}
	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}
	@Override
	public String toString() {
		return "DataLossPreventionReport [id=" + id + ", keyValue=" + keyValue + ", reportAddress=" + reportAddress
				+ ", active=" + active + ", lastReport=" + lastReport + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", createdOn=" + createdOn + ", dataLossPreventionReportType="
				+ dataLossPreventionReportType + ", company=" + company + ", daily=" + daily + ", weekly=" + weekly
				+ ", monthly=" + monthly + ", getId()=" + getId() + ", getKeyValue()=" + getKeyValue()
				+ ", getReportAddress()=" + getReportAddress() + ", getActive()=" + getActive() + ", getLastReport()="
				+ getLastReport() + ", getUpdatedBy()=" + getUpdatedBy() + ", getUpdatedOn()=" + getUpdatedOn()
				+ ", getCreatedOn()=" + getCreatedOn() + ", getDataLossPreventionReportType()="
				+ getDataLossPreventionReportType() + ", getCompany()=" + getCompany() + ", isDaily()=" + isDaily()
				+ ", isWeekly()=" + isWeekly() + ", isMonthly()=" + isMonthly() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
