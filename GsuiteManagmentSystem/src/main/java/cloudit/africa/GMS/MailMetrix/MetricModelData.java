package cloudit.africa.GMS.MailMetrix;

import java.util.List;

import com.google.api.services.gmail.model.Message;

public class MetricModelData {

	private Integer responseTime;
	private List<MessagesMetrix> allmessage;
	
	
	public Integer getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Integer responseTime) {
		this.responseTime = responseTime;
	}
	public List<MessagesMetrix> getAllmessage() {
		return allmessage;
	}
	public void setAllmessage(List<MessagesMetrix> allmessage) {
		this.allmessage = allmessage;
	}
	
	
	
}
