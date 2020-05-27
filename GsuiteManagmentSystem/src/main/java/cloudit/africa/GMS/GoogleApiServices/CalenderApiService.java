package cloudit.africa.GMS.GoogleApiServices;

import java.util.List;

import com.google.api.services.calendar.model.Event;

public interface CalenderApiService {

	List<Event> getEvents();

}
