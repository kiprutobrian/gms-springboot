package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Company {
	
	@GeneratedValue
	@Id
	private String companyId;
	private String email;
	private String phoneNumber;
	private String name;
	private String domain;
	private Integer licensedNumberOfSeats;
	private Integer maximumNumberOfSeats;
	private Integer numberOfSeats;
	private String resourcUrl;
	private boolean packageActive = false;
	private boolean customerDomainVerified = false;
	private String logo;
	private String countryCode;
	private String locality;
	private String postalCode;
	private String region;
	private boolean serviceAccountPresent = false;
	private boolean underSupport=false;
	private String subscriptionPlan="NA";
	private Date createdDate;
	private Integer createdBy;
	private Date updateDate;
	private Integer updateBy;
	private String registerdBy;
	

	@ManyToOne
	private Package packages;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCustomerDomainVerified() {
		return customerDomainVerified;
	}

	public void setCustomerDomainVerified(boolean customerDomainVerified) {
		this.customerDomainVerified = customerDomainVerified;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public Package getPackages() {
		return packages;
	}

	public void setPackages(Package packages) {
		this.packages = packages;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isPackageActive() {
		return packageActive;
	}

	public void setPackageActive(boolean packageActive) {
		this.packageActive = packageActive;
	}

	public boolean isServiceAccountPresent() {
		return serviceAccountPresent;
	}

	public void setServiceAccountPresent(boolean serviceAccountPresent) {
		this.serviceAccountPresent = serviceAccountPresent;
	}

	public Integer getLicensedNumberOfSeats() {
		return licensedNumberOfSeats;
	}

	public void setLicensedNumberOfSeats(Integer licensedNumberOfSeats) {
		this.licensedNumberOfSeats = licensedNumberOfSeats;
	}

	public Integer getMaximumNumberOfSeats() {
		return maximumNumberOfSeats;
	}

	public void setMaximumNumberOfSeats(Integer maximumNumberOfSeats) {
		this.maximumNumberOfSeats = maximumNumberOfSeats;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getResourcUrl() {
		return resourcUrl;
	}

	public void setResourcUrl(String resourcUrl) {
		this.resourcUrl = resourcUrl;
	}

	public boolean isUnderSupport() {
		return underSupport;
	}

	public void setUnderSupport(boolean underSupport) {
		this.underSupport = underSupport;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", email=" + email + ", phoneNumber=" + phoneNumber + ", name="
				+ name + ", domain=" + domain + ", licensedNumberOfSeats=" + licensedNumberOfSeats
				+ ", maximumNumberOfSeats=" + maximumNumberOfSeats + ", numberOfSeats=" + numberOfSeats
				+ ", resourcUrl=" + resourcUrl + ", packageActive=" + packageActive + ", customerDomainVerified="
				+ customerDomainVerified + ", logo=" + logo + ", countryCode=" + countryCode + ", locality=" + locality
				+ ", postalCode=" + postalCode + ", region=" + region + ", serviceAccountPresent="
				+ serviceAccountPresent + ", underSupport=" + underSupport + ", subscriptionPlan=" + subscriptionPlan
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updateDate=" + updateDate
				+ ", updateBy=" + updateBy + ", packages=" + packages + "]";
	}

	public String getRegisterdBy() {
		return registerdBy;
	}

	public void setRegisterdBy(String registerdBy) {
		this.registerdBy = registerdBy;
	}

	
	
}
