package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class CalenderApiServiceImpl implements CalenderApiService {

	
	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Calendar gooogleCalendarApi;
	
	
	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	
	public Calendar getMessages() {
		gooogleCalendarApi = new Calendar.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return gooogleCalendarApi;
	}
	@Override
	public List<Event> getEvents() {
		
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		rightNow.add(java.util.Calendar.DATE, 368);
		long nextDayInMillis = rightNow.getTimeInMillis();
		DateTime endDate = new DateTime(nextDayInMillis);
		DateTime now = new DateTime(System.currentTimeMillis());

		List<Event> items=null;
		
		try {
			com.google.api.services.calendar.model.Events events = getMessages().events().list("primary")
					.setMaxResults(10).setTimeMin(now).setTimeMax(endDate).setOrderBy("startTime").setSingleEvents(true)
					.execute();
			items = events.getItems();
			
			return items;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return null;
	}

}

