package cloudit.africa.GMS.Model;

import cloudit.africa.GMS.Entity.UserApp;

public class AuthenticationValidater {

	private UserApp userApp;
	private boolean active;
	private boolean session;
	private boolean systemRegisterd;
	private String message;
	
	
	public UserApp getUserApp() {
		return userApp;
	}
	public void setUserApp(UserApp userApp) {
		this.userApp = userApp;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isSession() {
		return session;
	}
	public void setSession(boolean session) {
		this.session = session;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSystemRegisterd() {
		return systemRegisterd;
	}
	public void setSystemRegisterd(boolean systemRegisterd) {
		this.systemRegisterd = systemRegisterd;
	}
	
	
	
}
