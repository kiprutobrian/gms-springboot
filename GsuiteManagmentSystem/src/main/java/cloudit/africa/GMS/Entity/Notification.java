package cloudit.africa.GMS.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue
	private Integer Id;
	@ManyToOne
	private UserApp userApp;
	private String fromUser;
	private String title;
	private String message;
	
	private String messageHtmlBody;
	private Date createdDate;
	private Integer createdBy;
	private Date updateDate;
	private Integer updateBy;
	
	
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageHtmlBody() {
		return messageHtmlBody;
	}
	public void setMessageHtmlBody(String messageHtmlBody) {
		this.messageHtmlBody = messageHtmlBody;
	}
	public UserApp getUserApp() {
		return userApp;
	}
	public void setUserApp(UserApp userApp) {
		this.userApp = userApp;
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
	
	
	

}
