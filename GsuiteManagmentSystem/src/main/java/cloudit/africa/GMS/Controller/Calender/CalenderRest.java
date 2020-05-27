package cloudit.africa.GMS.Controller.Calender;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Setting;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.OrganizationMembers;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GMSApiServices.CalenderService;
import cloudit.africa.GMS.Model.Appointment;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Repository.CalenderAppointmentSlotRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.HtmlTemplates;
import cloudit.africa.GMS.Utilities.ServiceResponse;

@RestController
public class CalenderRest {

	@Autowired
	UserAppRepositiry userRepository;
	@Autowired
	CalenderAppointmentSlotRepository calenderAppointmentRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	CalenderService calenderService;

	
	
	@Autowired
	AdminSettingService adminSetting;

	@RequestMapping("/createAppointments")
	public ServiceResponse getTesting(@RequestBody String appointment) throws IOException, GeneralSecurityException {
		ServiceResponse response = new ServiceResponse();
		response.setStatus("success");
		response.setData(appointment);
		return response;
	}

	@PostMapping("calenderppointment/{email}/createAppointment/{edwin}")
	public ServiceResponse postTesting(@PathVariable("email") String email, @PathVariable("edwin") String edwin,
			@RequestBody Appointment appointment) throws IOException, GeneralSecurityException {

		System.out.println("==== in postTesting ====");
		System.out.println("Event created:----------------" + email);
		System.out.println("Event created:----------------" + edwin);

		// Retrieve a single user setting
		Calendar service = ServiceAccount.getCalenderService(email);
		Setting setting = service.settings().get("timezone").execute();

		String myTimeZone = setting.getValue();
		TimeZone tz = TimeZone.getTimeZone(myTimeZone);
		int GMT = (((tz.getOffset(System.currentTimeMillis()) / 1000) / 60) / 60);
		System.out.println("GMT:----------------" + GMT);

		System.out.println("Event created:----------------" + appointment.toString());

		String description = HtmlTemplates.templateReturn(appointment.getTitle(),
				appointment.getFirstName() + " " + appointment.getLastName(), appointment.getPhonenumber(), "NAIROBI",
				appointment.getEmail(), appointment.getCompany(), appointment.getFromDate(), appointment.getTodate());

		System.out.println("Description-----------------------   " + description);

		Event event = new Event().setSummary(appointment.getTitle()).setDescription("" + description);

		DateTime startDateTime = new DateTime((((appointment.getFromDate())) + "+0" + GMT + ":00"));
		System.out.println("FromDate:----------------" + (startDateTime));

		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone(myTimeZone);
		event.setStart(start);

		DateTime endDateTime = new DateTime((((appointment.getTodate())) + "+0" + GMT + ":00"));
		System.out.println("ToDate:----------------" + (endDateTime));

		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone(myTimeZone);
		event.setEnd(end);

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(1), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		String calendarId = "primary";
		event = service.events().insert(calendarId, event).execute();
		System.out.printf("Event created: %s\n", event.getHtmlLink());

		ServiceResponse response = new ServiceResponse();
		response.setStatus("success");
		response.setData(appointment);
		return response;

	}

	@RequestMapping(value = "/login/BussnesComAfrica2")
	public String oauth2Callback(@RequestParam(value = "code") String code) {
		System.out.println("user" + code);

		return "login.html";
	}

	@RequestMapping("updateCalenderSignature")
	public List<OrganizationMembers> createLinksForAll(@RequestBody List<OrganizationMembers> emailsAdresses,
			Model model) {

		System.out.println("Users=====================" + emailsAdresses);
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("me");
		keyValue.setOrgUser(emailsAdresses);

		UserApp user = authenticationManager.getUserCredentials();

		Checker checker = adminSetting.getChecker("" + 4);

		System.out.println(checker.getId());
		boolean isPresent = false;
		try {
			if (checker.getId().equals(null)) {
			}
			isPresent = true;

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		if (isPresent) {
			if (checker.isActive()) {
				System.out.println("MARKER CHECKER iS ENABLED");
				if (checker.getChecker().getId().equals(user.getId())) {
					System.out.println("USER IS CHECKER");
					calenderService.updateAppointmentBookingSlot(keyValue);
				} else {
					System.out.println("" + keyValue);
					System.out.println("USER IS NOT CHECKER");
					authenticationManager.executeWorkFlowCheckerNew(checker, "UPDATE_APPOINTMENTBOOKING", keyValue);
				}
			} else {
				System.out.println("MARKER CHECKER iS DISABLED");
				calenderService.updateAppointmentBookingSlot(keyValue);
			}
		} else {
			System.out.println("MARKER CHECKER iS NOT PRESENT");
			calenderService.updateAppointmentBookingSlot(keyValue);
		}


		return emailsAdresses;

	}

	@RequestMapping("removeCalenderAppointment")
	public List<OrganizationMembers> deleteSignatureAppointment(@RequestBody List<OrganizationMembers> eamilsAddress,
			Model model) throws GeneralSecurityException, IOException, URISyntaxException {

		KeyValue keyValue = new KeyValue();
		keyValue.setKey("me");
		keyValue.setOrgUser(eamilsAddress);

		UserApp user = authenticationManager.getUserCredentials();
		
		Checker checker = adminSetting.getChecker("" + 4);

		System.out.println(checker.getId());
		boolean isPresent = false;
		try {
			if (checker.getId().equals(null)) {
			}
			isPresent = true;

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		if (isPresent) {
			if (checker.isActive()) {
				System.out.println("MARKER CHECKER iS ENABLED");
				if (checker.getChecker().getId().equals(user.getId())) {
					System.out.println("USER IS CHECKER");
					calenderService.deleteAppointmentBookingSlot(keyValue);
				} else {
					System.out.println("" + keyValue);
					System.out.println("USER IS NOT CHECKER");
					authenticationManager.executeWorkFlowCheckerNew(checker, "DELETE_APPOINTMENTBOOKING", keyValue);
				}
			} else {
				System.out.println("MARKER CHECKER iS DISABLED");
				calenderService.deleteAppointmentBookingSlot(keyValue);
			}
		} else {
			System.out.println("MARKER CHECKER iS NOT PRESENT");
			calenderService.deleteAppointmentBookingSlot(keyValue);
		}
		
		
		
		
//		MarkerChecker checker = markerCheckerRepository.findByIdCompany(3, user.getCompany().getCompanyId());
//
//		if (checker.isActive()) {
//			System.out.println("MARKER CHECKER iS ENABLED");
//			if (checker.getChekerUserId().getId().equals(user.getId())) {
//				System.out.println("USER IS CHECKER");
//				calenderService.deleteAppointmentBookingSlot(keyValue);
//
//			} else {
//				System.out.println("" + keyValue);
//				System.out.println("USER IS NOT CHECKER");
//				authenticationManager.executeWorkFlowChecker(checker, "DELETE_APPOINTMENTBOOKING", keyValue);
//			}
//		} else {
//			System.out.println("MARKER CHECKER iS DISABLED");
//			calenderService.deleteAppointmentBookingSlot(keyValue);
//		}

		return eamilsAddress;

	}

}
