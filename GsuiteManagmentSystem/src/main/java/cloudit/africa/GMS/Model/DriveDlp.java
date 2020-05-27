package cloudit.africa.GMS.Model;

public class DriveDlp {
	private String itemName;
	private String  eventDescription;
	private String  user;
	private String  date;
	private String  eventName;
	private String  itemId;
	private String itemType;
	private String owner;
	private String priorVisibility;
	private String visibility;
	private String iPAddress;
	private String lastViewIp;
	private String viewDate;
	
	
	public DriveDlp(String itemName, String eventDescription, String user, String date, String eventName, String itemId,
			String itemType, String owner, String priorVisibility, String visibility, String iPAddress,
			String lastViewIp, String viewDate) {
		super();
		this.itemName = itemName;
		this.eventDescription = eventDescription;
		this.user = user;
		this.date = date;
		this.eventName = eventName;
		this.itemId = itemId;
		this.itemType = itemType;
		this.owner = owner;
		this.priorVisibility = priorVisibility;
		this.visibility = visibility;
		this.iPAddress = iPAddress;
		this.lastViewIp = lastViewIp;
		this.viewDate = viewDate;
	}



	public DriveDlp(String itemName, String eventDescription, String user, String date, String eventName, String itemId,
			String itemType, String owner, String priorVisibility, String visibility, String iPAddress) {
		super();
		this.itemName = itemName;
		this.eventDescription = eventDescription;
		this.user = user;
		this.date = date;
		this.eventName = eventName;
		this.itemId = itemId;
		this.itemType = itemType;
		this.owner = owner;
		this.priorVisibility = priorVisibility;
		this.visibility = visibility;
		this.iPAddress = iPAddress;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPriorVisibility() {
		return priorVisibility;
	}
	public void setPriorVisibility(String priorVisibility) {
		this.priorVisibility = priorVisibility;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getiPAddress() {
		return iPAddress;
	}
	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}
	public String getLastViewIp() {
		return lastViewIp;
	}
	public void setLastViewIp(String lastViewIp) {
		this.lastViewIp = lastViewIp;
	}
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}	
}
