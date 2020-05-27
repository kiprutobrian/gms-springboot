package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.admin.reports.Reports;
import com.google.api.services.admin.reports.model.Activities;
import com.google.api.services.admin.reports.model.Activity;
import com.google.api.services.admin.reports.model.Activity.Events;
import com.google.api.services.admin.reports.model.Activity.Events.Parameters;

import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Model.DriveDlp;
import cloudit.africa.GMS.Model.Filter;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class AdminReportingServiceImpl implements AdminReportingService {

	private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private Reports gooogleReportsApi;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYY-MM-dd hh:mm:ss");
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Autowired
	GabSettingRepository gabSettingRepository;

	@Autowired
	UserAppRepositiry userAppRepositiry;

	public Reports geService() {
		gooogleReportsApi = new Reports.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return gooogleReportsApi;
	}

	@Override
	public List<DriveDlp> getDriveReportingAcrivity(String startingDate) {

		List<UserApp> superadmin = new ArrayList<UserApp>();
		if (superadmin != null && superadmin.size() > 0) {
			String us = "";
			for (int a = 0; a < superadmin.size(); a++) {
				String email[] = superadmin.get(a).getEmail().split("@");
				if (email[1].equals("dev.businesscom.dk")) {
					us = superadmin.get(a).getEmail();
					break;
				}
			}
			HashMap<String, List<Activity>> data = new HashMap<>();
			List<DriveDlp> dlpreportList = new ArrayList<>();

			try {
				Directory directoryService = ServiceAccount.getDirectoryServices(us);
				Reports reportDrive = ServiceAccount.getReportServices(us);

				Users account = directoryService.users().list().setCustomer("my_customer").setOrderBy("email")
						.execute();

				List<User> usersList = new ArrayList<User>();
				com.google.api.services.admin.directory.Directory.Users.List ul = directoryService.users().list()
						.setCustomer("my_customer").setMaxResults(500);
				do {
					Users curPage = ul.execute();
					usersList.addAll(curPage.getUsers());
					ul.setPageToken(curPage.getNextPageToken());
				} while (ul.getPageToken() != null && ul.getPageToken().length() > 0);

				for (int acc = 0; acc < account.getUsers().size(); acc++) {
					String userAccount = account.getUsers().get(acc).getPrimaryEmail();
					String applicationName = "drive";
					Activities result = reportDrive.activities().list(userAccount, applicationName).setMaxResults(1000)
							.execute();

					List<Activity> activities = result.getItems();
					String lastIpAddress = null;
					List<Filter> myfilter = new ArrayList<>();
					if (activities != null) {
						data.put(userAccount, activities);
						for (Activity activity : activities) {
							List<Events> eventsList = activity.getEvents();
//						System.out.print("LAST IPADDRESS " + lastIpAddress + "\n");
							for (Events events : eventsList) {
								String eventName = events.getName();
								if (eventName.equals("view")) {
									List<Parameters> parametersList = events.getParameters();
									String user = activity.getActor().getEmail();
									String ipAddresss = activity.getIpAddress();
									String documentId = getParameterValue(parametersList, "doc_id");
									myfilter.add(new Filter(documentId, ipAddresss, user,
											simpleDateFormat.format(activity.getId().getTime().getValue())));
								}
								if (eventName.equals("download")) {
									Date date = new Date(activity.getId().getTime().getValue());
									List<Parameters> parametersList = events.getParameters();
									String eventdate = simpleDateFormat.format(date);
									String ipAddress = activity.getIpAddress();
									String user = activity.getActor().getEmail();
									String documentId = getParameterValue(parametersList, "doc_id");
									String documentName = getParameterValue(parametersList, "doc_title");
									String owner = getParameterValue(parametersList, "owner");
									String visibility = getParameterValue(parametersList, "visibility");
									String description = getParameterValue(parametersList, "description");
									String doctype = getParameterValue(parametersList, "doc_type");
									dlpreportList.add(new DriveDlp(documentName, description, user, eventdate,
											"download", documentId, doctype, owner, "prioorvisibility", visibility,
											ipAddress));

								}
							}
						}
						for (int d = 0; d < dlpreportList.size(); d++) {
							DriveDlp downloadDLP = dlpreportList.get(d);
							if (downloadDLP.getiPAddress() == null) {
								for (int s = 0; s < myfilter.size(); s++) {
									Filter filter = myfilter.get(s);
									String fileId = filter.getId();
									String user = filter.getUser();

									if (fileId.equals(downloadDLP.getItemId()) && user.equals(downloadDLP.getUser())) {
										String ipAddress = filter.getIp();
										String lastviewdate = filter.getViewDate();
										DriveDlp updateIp = downloadDLP;
										updateIp.setLastViewIp(ipAddress);
										updateIp.setViewDate(lastviewdate);
										dlpreportList.set(d, updateIp);
										break;
									}
								}
							}
						}
					}
				}
				return dlpreportList;
			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getParameterValue(List<Parameters> parameterList, String keyWord) {
		String value = "N/A";
		for (int b = 0; b < parameterList.size(); b++) {
			Parameters parameters = parameterList.get(b);
			if (parameters.getName().equals(keyWord)) {
				value = parameters.getValue();
				break;
			}
		}
		return value;
	}

	@Override
	public List<Activity> getDriveReportingAcrivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getDriveActivityReport(Reports reportService, String userAccount, String applicationName) {
		// TODO Auto-generated method stub

		List<Activity> activityList = new ArrayList<Activity>();
		com.google.api.services.admin.reports.Reports.Activities.List ul;
		try {

			SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			System.out.println("CurrenrDate" + input.format(new Date()));
			ul = reportService.activities().list(userAccount, applicationName)
					.setFields("nextPageToken,items(events,ownerDomain,actor,id,ipAddress)")
					.setStartTime("2020-02-09T04:11:49.677Z").setEndTime("").setEventName("").setMaxResults(1000);
			do {
				Activities curPage = ul.execute();
				System.out.println("curPage ========== " + curPage.toString());
				if (curPage.getItems() != null) {
					activityList.addAll(curPage.getItems());
				}
				ul.setPageToken(curPage.getNextPageToken());
			} while (ul.getPageToken() != null && ul.getPageToken().length() > 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return activityList;
	}
	
	@Override
	public List<Activity> getCustomDriveActivityReport(Reports reportService, String userAccount, String applicationName,Date startingDate,Date endingDate,String event) {
		// TODO Auto-generated method stub

		List<Activity> activityList = new ArrayList<Activity>();
		com.google.api.services.admin.reports.Reports.Activities.List ul;
		try {

			String startDate=format.format(startingDate);
			String endDate=format.format(endingDate);
			
			
			ul = reportService.activities().list(userAccount, applicationName).setFields("nextPageToken,items(events,ownerDomain,actor,id,ipAddress)").setStartTime(startDate).setEndTime(endDate).setEventName(event).setMaxResults(1000);
			
			do {
				Activities curPage = ul.execute();
				System.out.println("curPage ========== " + curPage.toString());
				if (curPage.getItems() != null) {
					activityList.addAll(curPage.getItems());
				}
				ul.setPageToken(curPage.getNextPageToken());
			} while (ul.getPageToken() != null && ul.getPageToken().length() > 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return activityList;
	}

	

}
