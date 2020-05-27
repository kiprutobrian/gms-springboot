package cloudit.africa.GMS.Model;

import com.google.api.services.admin.reports.model.Activity.Events;

public class DownloadDriveReport {
	private String owner;
	private String filename;
	private Events events;
	
	public DownloadDriveReport(String owner, String filename, Events events) {
		super();
		this.owner = owner;
		this.filename = filename;
		this.events = events;
	}

	public DownloadDriveReport() {
	}
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Events getEvents() {
		return events;
	}

	public void setEvents(Events events) {
		this.events = events;
	}
	
	
	
	
	

}
