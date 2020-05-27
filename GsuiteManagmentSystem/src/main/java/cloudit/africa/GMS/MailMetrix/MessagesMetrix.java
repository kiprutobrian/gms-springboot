package cloudit.africa.GMS.MailMetrix;

import java.util.Date;

import cloudit.africa.GMS.Utilities.Utilities;

public class MessagesMetrix {

	private Date time;
	private String sender;
	private String recipient;
	private Integer reponseTime;
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getMonthAndYear() {
		return Utilities.getYearMonthOnly(time);
	}
	public Integer getReponseTime() {
		return reponseTime;
	}
	public void setReponseTime(Integer reponseTime) {
		this.reponseTime = reponseTime;
	}
	
	
	
}
