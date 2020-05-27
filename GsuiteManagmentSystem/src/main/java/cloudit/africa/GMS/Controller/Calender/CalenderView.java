package cloudit.africa.GMS.Controller.Calender;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.util.DateTime;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.CalenderAppointmentSlot;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.CalenderAppointment;
import cloudit.africa.GMS.Model.MyEvents;
import cloudit.africa.GMS.Repository.CalenderAppointmentSlotRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class CalenderView {

	@Autowired
	UserAppRepositiry userRepository;
	@Autowired
	CalenderAppointmentSlotRepository calenderAppointmentRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	@Autowired
	GMSAuthenticationManager authenticationManager;
	
	@Autowired
	GlobalModelView globalModelView;

	
	@Autowired
	DirectoryService directoryService;

	@RequestMapping("calendersettings")
	public String CalenderSettings(Model model, HttpServletRequest request)
			throws IOException, GeneralSecurityException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
	
		List<User> myOrgUsers=directoryService.getDomainUsers();
		
		List<CalenderAppointment> CalenderAppointmentList = new ArrayList<>();

		for (int accounts = 0; accounts < myOrgUsers.size(); accounts++) {

			String id = myOrgUsers.get(accounts).getId();
			String email = myOrgUsers.get(accounts).getPrimaryEmail();
			String name = myOrgUsers.get(accounts).getName().getFullName();
			String orgUnit =myOrgUsers.get(accounts).getOrgUnitPath();
			
			Optional<CalenderAppointmentSlot> calenderAppointmentSlot = calenderAppointmentRepository.findById(id);
			if (calenderAppointmentSlot.isPresent()) {
				CalenderAppointmentList.add(
						new CalenderAppointment(id, email, name, calenderAppointmentSlot.get().getAppointmentActive(),
								Utilities.simpleDateFormat.format(calenderAppointmentSlot.get().getUpdatedAt()),orgUnit));
			} else {

				CalenderAppointmentList.add(
						new CalenderAppointment(id, email, name, false, Utilities.simpleDateFormat.format(new Date()),orgUnit));
			}

		}

		model.addAttribute("users", CalenderAppointmentList);

		userRepository.findAll();

		return "gms-calenderuserslist";
	}

	@RequestMapping("calenderppointment/{email}")
	public String postTesting(@PathVariable("email") String email, Model model)
			throws IOException, GeneralSecurityException, URISyntaxException {
		List<MyEvents> list = new ArrayList<MyEvents>();
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		rightNow.add(java.util.Calendar.DATE, 365); // add one day
		long nextDayInMillis = rightNow.getTimeInMillis();
		DateTime endDate = new DateTime(nextDayInMillis);
		Directory dir = ServiceAccount.getDirectoryServices("" + email);

		User user = dir.users().get("" + email).execute();
		model.addAttribute("user_email", email);
		model.addAttribute("user_phone_number", "07000129");
		model.addAttribute("user_company_location", "50 Muthithi Road The Citadel, 4th Floor");
		model.addAttribute("user_name", "" + user.getName().getFullName());
		model.addAttribute("user_company_name", "" + "BussinesCom Africa");

		System.out.println("Emails ---------------" + user.getPrimaryEmail());
		System.out.println("Id ---------------" + user.getId());
		System.out.println("comming ---------------" + email);

		email = "" + user.getPrimaryEmail();
		Calendar service = ServiceAccount.getCalenderService("" + email + "");
		DateTime now = new DateTime(System.currentTimeMillis());

		com.google.api.services.calendar.model.Events events1 = service.events().list("primary").setMaxResults(100)
				.setTimeMin(now).setTimeMax(endDate).setOrderBy("startTime").setSingleEvents(true).execute();

		List<Event> items = events1.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				DateTime end = event.getEnd().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				} else {
					Long startingDate = event.getStart().getDateTime().getValue();
					Long endingDate = event.getEnd().getDateTime().getValue();
					list.add(new MyEvents("Busy", startingDate, endingDate, "bg-danger"));
					System.out.print("Long Start-----" + event.getStart().getDateTime().getValue() + "\n");
					System.out.print("Long End-----" + event.getEnd().getDateTime().getValue() + "/n");
					System.out.printf("%s (%s)\n", event.getSummary(), start);
				}
			}
		}

		model.addAttribute("element", list);

		model.addAttribute("user_name", email);
		return "newfile.html";
	}

	@RequestMapping(value = "calendersetting")
	public String calenderSetting(Model model) {
		return "calendersetting";
	}

}
