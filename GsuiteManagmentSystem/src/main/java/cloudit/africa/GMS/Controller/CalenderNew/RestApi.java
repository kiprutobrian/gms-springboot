package cloudit.africa.GMS.Controller.CalenderNew;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import cloudit.africa.GMS.Model.MyEvents;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.HtmlTemplates;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController
public class RestApi {

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:MM:ss");
	public static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm-dd-yyyy HH:MM:ss");
	public static SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-YYYY");

	public static SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss ZZZZ", Locale.ENGLISH);

	@RequestMapping("getDateUpdate/{myEmailAddress}/{date}/")
	public List<myCalender> getResponse(@PathVariable("date") Long dateValues,
			@PathVariable("myEmailAddress") String myEmailAddress, Model model) {
		Date da = new Date(dateValues);
		List<myCalender> cx = null;
		try {
			cx = getEmptySlots(getAppointmentSlots(da), da, myEmailAddress);
			return cx;
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cx;

	}

	@RequestMapping("/makeAppointment/{emailAddress}/{data}")
	public Booking bookAppointment(@PathVariable("emailAddress") String myEmailAddress, @RequestBody Booking booking) {

		Long date = booking.getSelectedDate();
		String description = HtmlTemplates.templateReturn(booking.getTitile(), booking.getName(),
				booking.getPhoneNumber(), "NAIROBI", booking.getEmail(), booking.getOrganisation(),
				booking.getDescription());

		Date selectedDate = new Date(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectedDate);
		calendar.add(Calendar.MINUTE, 30);
		TimeZone timeZone = calendar.getTimeZone();
		DateTime endDateTime = new DateTime(calendar.getTime());
		DateTime startDateTime = new DateTime(selectedDate);

		Event event = new Event().setSummary(booking.getTitile()).setDescription(description);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone(timeZone.getID());
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone(timeZone.getID());

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(1), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));

		event.setEnd(end);
		event.setStart(start);
		event.setReminders(reminders);
		String calendarId = "primary";

		try {
			com.google.api.services.calendar.Calendar service = ServiceAccount.getCalenderService(myEmailAddress);
			event = service.events().insert(calendarId, event).execute();

		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(endDateTime);

		return booking;
	}

	public List<myCalender> getAppointmentSlots(Date newDate) {

		Calendar instance = Calendar.getInstance();
		Calendar curentDateinstance = Calendar.getInstance();
		curentDateinstance.setTime(newDate);
		curentDateinstance.setTimeZone(TimeZone.getTimeZone("Africa/Nairobi"));
		List<myCalender> appointmentSlots = new ArrayList<myCalender>();
		instance.setTime((newDate));
		instance.setTimeZone(TimeZone.getTimeZone("Africa/Nairobi"));

		Date ds = new Date();
//		for (int hrs = 0; hrs < 48; hrs++) {
//			instance.add(Calendar.MINUTE, 30);
//			myCalender mycalender = new myCalender();
//			
//			if (hrs > 20 && hrs <= 40) {
//				mycalender.setDay("" + instance.getTime().toLocaleString());
//				mycalender.setFrom(instance.getTime());
//				if (hrs>28) {
//					mycalender.setIsPm(true);
//					appointmentSlots.add(mycalender);
//				} else {
//					mycalender.setIsPm(false);
//					appointmentSlots.add(mycalender);
//				}
//				
//			}
//			System.out.println(mycalender);
//		}

		int i = 1;
		while (i++ != 49) {
			instance.add(Calendar.MINUTE, 30);
			myCalender mycalender = new myCalender();
			mycalender.setIsPm(false);
			mycalender.setDay("" + instance.getTime().toLocaleString());
			mycalender.setFrom(instance.getTime());

			if (instance.getTime().getHours() <= 17) {
				if ((ds.getDate() == instance.getTime().getDate())
						&& (ds.getMonth() == instance.getTime().getMonth())) {
					if (instance.getTime().getHours() >= ds.getHours()) {
						if (instance.getTime().getHours() >= 12) {
							mycalender.setIsPm(true);
						}
						appointmentSlots.add(mycalender);
						if (instance.getTime().getHours() == 17)
							break;
					}
				} else {
					if (instance.getTime().getHours() >= 8) {
						if (instance.getTime().getHours() >= 12) {
							mycalender.setIsPm(true);
						}
						appointmentSlots.add(mycalender);
						if (instance.getTime().getHours() == 17)
							break;
					}
				}
			} else {
				break;
			}

		}

		return appointmentSlots;
	}

	public List<myCalender> getEmptySlots(List<myCalender> timeslots, Date myinstance, String email)
			throws GeneralSecurityException, IOException {

		List<MyEvents> list = new ArrayList<MyEvents>();
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		rightNow.setTime(myinstance);

		rightNow.add(java.util.Calendar.DATE, 1); // add one day
		long nextDayInMillis = rightNow.getTimeInMillis();

		DateTime endDate = new DateTime(nextDayInMillis);
		DateTime startDate = new DateTime(myinstance);

		com.google.api.services.calendar.Calendar service = ServiceAccount.getCalenderService(email);
		com.google.api.services.calendar.model.Events events1 = service.events().list("primary").setMaxResults(100)
				.setTimeMin(startDate).setTimeMax(endDate).setOrderBy("startTime").setSingleEvents(true).execute();

		List<Event> events = events1.getItems();
		List<myCalender> time = timeslots;

		for (int e = 0; e < events.size(); e++) {
			Event event = events.get(e);
			Calendar eventInstance = Calendar.getInstance();

			if (event.getStart().getDateTime() != null) {
				eventInstance.setTime(new Date(event.getStart().getDateTime().getValue()));
				for (int times = 0; times < time.size(); times++) {
					Calendar instance = Calendar.getInstance();
					instance.setTime(time.get(times).getFrom());
					if (instance.getTime().equals(eventInstance.getTime())) {
						Date startingDate = new Date(event.getStart().getDateTime().getValue());
						Date endingDate = new Date(event.getEnd().getDateTime().getValue());
						int minuteDiff = Integer.parseInt(Utilities.getDateDiffInMinutes(startingDate, endingDate));
						int diff = minuteDiff / 30;
						int removetime = (diff - 1);
						while (removetime >= 0) {
							time.remove((times + removetime));
							removetime--;
						}
					}
				}
			} else {
				time.clear();
			}
			
		}
		return timeslots;
	}
}
