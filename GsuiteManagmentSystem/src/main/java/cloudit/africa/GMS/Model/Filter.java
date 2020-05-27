package cloudit.africa.GMS.Model;

public class Filter {

	private String id;
	private String ip;
	private String user;
	private String viewDate;

	public Filter(String id, String ip, String user, String viewDate) {
		super();
		this.id = id;
		this.ip = ip;
		this.user = user;
		this.viewDate=viewDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getViewDate() {
		return viewDate;
	}

	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}

}
