package cloudit.africa.GMS.Model;

import java.util.Date;

public class DataLossPreventionFilter {
	
	public String company;
	public String userAccount;
	public String ipAddress;
	public String keyword;
	private Date startingDate;
	private Date endingDate;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}
	public Date getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	
	
	

}
