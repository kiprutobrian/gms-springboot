package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.StringUtils;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.admin.reports.Reports;
import com.google.api.services.admin.reports.model.Activities;
import com.google.api.services.admin.reports.model.Activity;
import com.google.api.services.admin.reports.model.Activity.Events;
import com.google.api.services.admin.reports.model.Activity.Events.Parameters;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.DataLossPreventionReport;
import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.Entity.GmsUrls;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.AdminReportingService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.DataLossPreventionReportDrive;
import cloudit.africa.GMS.Model.DataLossPreventionReportEmail;
import cloudit.africa.GMS.Model.DriveDlp;
import cloudit.africa.GMS.Model.Filter;
import cloudit.africa.GMS.Model.GabEmailReport;
import cloudit.africa.GMS.Repository.DataLossPreventionReportRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class DataLossPreventionServiceImpl implements DataLossPreventionService {

	@Autowired
	DataLossPreventionReportRepository dataLossPreventionReportRepository;

	@Autowired
	DirectoryService directoryService;

	@Autowired
	AdminReportingService adminReportingService;

	@Autowired
	UserAppRepositiry userAppRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	SimpleDateFormat simpleDate = new SimpleDateFormat("yyy/mm/dd");
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYY-MM-dd hh:mm:ss");

	@Override
	public List<DataLossPreventionReportEmail> CheckEmailSendOutSideDomain(
			DataLossPreventionReport dataLossPreventionReport) {
		Company company = dataLossPreventionReport.getCompany();
		List<DataLossPreventionReportEmail> filteredMessages = new ArrayList<>();
		UserApp superadmin = userAppRepository.findByIsSuperAdminAndCompany(true, company);
		String us = superadmin.getEmail();

		GmsUrls url = gmsUrlsRepository.findById(0).get();
		String query = "";
		String hosturl = url.getUrl();
		String keyWord = "";
		Date lastReportDate = null;

		System.out.println(superadmin.toString() + "OUT ");
		System.out.println(url.toString() + "OUT  " + dataLossPreventionReport.toString());

		if (dataLossPreventionReport != null) {

			System.out.println("IN  " + dataLossPreventionReport);

			keyWord = dataLossPreventionReport.getKeyValue();
			lastReportDate = dataLossPreventionReport.getLastReport();

//			if (lastReportDate == null) {
//				query = "in:anywhere " + keyWord + ",after:" + simpleDate.format(lastReportDate);
//			} else {
//			
			query = "in:anywhere " + keyWord + ",after: 2020/02/01";

			String domain = company.getDomain();
			System.out.println("QUERY " + query + " DOMAIN" + domain);

			try {
//				Directory directoryService = ServiceAccount.getDirectoryServices(us);
//				Users result = directoryService.users().list().setCustomer("my_customer").setOrderBy("email").execute();
				List<User> usersList = directoryService.getDomainUsers(us);
				System.out.println("ACCOUNTS LIST " + usersList.toString());

//				domain = account.split("@")[1];

				for (int acc = 0; acc < usersList.size(); acc++) {
					User userAccountSelected = usersList.get(acc);
					if (!userAccountSelected.getSuspended()) {
						String account = userAccountSelected.getPrimaryEmail();

						ListMessagesResponse filteredEmailsWithContent;
						Gmail service = ServiceAccount.getGmailService(account);
						filteredEmailsWithContent = service.users().messages().list(account).setQ(query).execute();
						System.out.println(account + " -------------------- " + filteredEmailsWithContent.size());
						if (filteredEmailsWithContent.getMessages() != null) {
							for (int a = 0; a < filteredEmailsWithContent.getMessages().size(); a++) {
								Message mess = filteredEmailsWithContent.getMessages().get(a);
								Message messageswithKeyWord = service.users().messages().get(account, mess.getId())
										.execute();
								String fromEmail = getUserData("From", messageswithKeyWord.getPayload().getHeaders());
								String toEmail = getUserData("To", messageswithKeyWord.getPayload().getHeaders());
								String subject = getUserData("Subject", messageswithKeyWord.getPayload().getHeaders());
								Date date = new Date(messageswithKeyWord.getInternalDate());
								if (Utilities.isAfter(Utilities.getpreviousWeek(), date)) {
									List<MessagePart> parts = messageswithKeyWord.getPayload().getParts();
									if (toEmail != null && fromEmail != null) {
										if (fromEmail.contains(account)) {
											if ((!toEmail.contains(domain))) {
												DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
												String strDate = dateFormat.format(date);

												DataLossPreventionReportEmail messageReport = new DataLossPreventionReportEmail();
												messageReport.setMessageId(messageswithKeyWord.getId());
												messageReport.setThreadId(messageswithKeyWord.getThreadId());
												messageReport.setHistoryId("" + messageswithKeyWord.getHistoryId());
												messageReport.setFrom(account);
												messageReport.setTo(toEmail);
												messageReport.setSubject(subject);
												String body = StringUtils
														.newStringUtf8(Base64.decodeBase64(messageswithKeyWord
																.getPayload().getParts().get(0).getBody().getData()));
												messageReport.setHasDriveAttachment("" + driveAcchamentShared(body));
												messageReport.setSnippet(body);
												messageReport.setHasAttachment(AttachmentOnAcchaments(
														messageswithKeyWord.getPayload().getParts()));
												messageReport.setDateSent(strDate);

												if ((AttachmentOnAcchaments(
														messageswithKeyWord.getPayload().getParts()) != null)) {
													System.out.println("xcxcxccxcxcxc " + AttachmentOnAcchaments(
															messageswithKeyWord.getPayload().getParts()));
													messageReport.setResourceurl(hosturl + "File" + "/" + account + "/"
															+ messageswithKeyWord.getId());
												} else {
													messageReport.setResourceurl("");
												}
												filteredMessages.add(messageReport);
											}
										}
									}
								}
							}
						}
					}
				}

				dataLossPreventionReport.setUpdatedOn(new Date());
				dataLossPreventionReport.setLastReport(new Date());
				dataLossPreventionReportRepository.saveAndFlush(dataLossPreventionReport);
				System.out.println("DATA LIST ENETERED" + filteredMessages);

				return filteredMessages;
			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<DataLossPreventionReportDrive> getDriveReportingAcrivity(
			DataLossPreventionReport dataLossPreventionReport) {

		if (dataLossPreventionReport != null) {
			Company company = dataLossPreventionReport.getCompany();
			UserApp superadmin = userAppRepository.findByIsSuperAdminAndCompany(true, company);
			String us = superadmin.getEmail();
			System.out.print(us);

			HashMap<String, List<Activity>> data = new HashMap<>();
			List<DataLossPreventionReportDrive> dlpreportList = new ArrayList<>();

			try {
//				Directory directoryService = ServiceAccount.getDirectoryServices(us);
				Reports reportDrive = ServiceAccount.getReportServices(us);
				List<User> account = directoryService.getDomainUsers(us);

				for (int acc = 0; acc < account.size(); acc++) {
					String userAccount = account.get(acc).getPrimaryEmail();
					String applicationName = "drive";

					List<Activity> activities = adminReportingService.getDriveActivityReport(reportDrive, userAccount,
							applicationName);

					System.out.print("ACCOUNT " + userAccount + "   " + activities.size() + "\n");

					List<Filter> myfilter = new ArrayList<>();
					if (activities != null) {

						data.put(userAccount, activities);
						for (Activity activity : activities) {
							List<Events> eventsList = activity.getEvents();
							for (Events events : eventsList) {
//								System.out.print("EVENT" + events.getName() + "\n");
								String eventName = events.getName();
								if (eventName.equals("view")) {
									List<Parameters> parametersList = events.getParameters();
									String user = activity.getActor().getEmail();
									String ipAddresss = activity.getIpAddress();
									String documentId = getParameterValue(parametersList, "doc_id");
									myfilter.add(new Filter(documentId, ipAddresss, user,
											simpleDateFormat.format(activity.getId().getTime().getValue())));

									System.out.print("download" + ipAddresss + "\n");
								}
								if (eventName.equals("edit")) {
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
									dlpreportList.add(new DataLossPreventionReportDrive(documentName, description, user,
											eventdate, "download", documentId, doctype, owner, "prioorvisibility",
											visibility, ipAddress));

									System.out.print("IP" + ipAddress + "\n");

								}
							}
						}
						for (int d = 0; d < dlpreportList.size(); d++) {
							DataLossPreventionReportDrive downloadDLP = dlpreportList.get(d);
							if (downloadDLP.getiPAddress() == null) {
								for (int s = 0; s < myfilter.size(); s++) {
									Filter filter = myfilter.get(s);
									String fileId = filter.getId();
									String user = filter.getUser();

									if (fileId.equals(downloadDLP.getItemId()) && user.equals(downloadDLP.getUser())) {
										String ipAddress = filter.getIp();
										String lastviewdate = filter.getViewDate();
										DataLossPreventionReportDrive updateIp = downloadDLP;
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

	@Override
	public List<DataLossPreventionReportDrive> getCustomDriveReportingAcrivity(Date starting, Date ending,
			String customevent, String customIpAddress, String companyId) {

		Company company = new Company();
		company.setCompanyId(companyId);

		UserApp superadmin = userAppRepository.findByIsSuperAdminAndCompany(true, company);
		String us = superadmin.getEmail();
		System.out.print(us);

		HashMap<String, List<Activity>> data = new HashMap<>();
		List<DataLossPreventionReportDrive> dlpreportList = new ArrayList<>();

		try {
			Reports reportDrive = ServiceAccount.getReportServices(us);
			List<User> account = directoryService.getDomainUsers(us);

			for (int acc = 0; acc < account.size(); acc++) {
				String userAccount = account.get(acc).getPrimaryEmail();
				String applicationName = "drive";

				List<Activity> activities = adminReportingService.getCustomDriveActivityReport(reportDrive, userAccount,
						applicationName, starting, ending, customevent);

				System.out.print("ACCOUNT " + userAccount + "   " + activities.size() + "\n");

				List<Filter> myfilter = new ArrayList<>();
				if (activities != null) {

					data.put(userAccount, activities);
					for (Activity activity : activities) {
						List<Events> eventsList = activity.getEvents();
						for (Events events : eventsList) {
							String eventName = events.getName();

							if (customIpAddress.equals("")) {
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
								dlpreportList.add(new DataLossPreventionReportDrive(documentName, description, user,
										eventdate, customevent, documentId, doctype, owner, "prioorvisibility",
										visibility, ipAddress));

							} else {
								if (!activity.getIpAddress().equals(customIpAddress)) {
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
									dlpreportList.add(new DataLossPreventionReportDrive(documentName, description, user,
											eventdate, customevent, documentId, doctype, owner, "prioorvisibility",
											visibility, ipAddress));
									System.out.print("IP" + ipAddress + "\n");


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

	public Boolean driveAcchamentShared(String body) {
		if (body != null) {
			if (body.contains("<https://docs.google.com")) {
				return true;
			} else if (body.contains("<https://drive.google.com")) {
				return true;
			}

		}

		return null;
	}

	public String AttachmentOnAcchaments(List<MessagePart> parts) {

		for (MessagePart part : parts) {

			String filename = part.getFilename();
			String attId = part.getBody().getAttachmentId();

			System.out.println(filename);
			System.out.println(attId);
			if (part.getFilename() != null && part.getFilename().length() > 0) {
				return part.getFilename();
			}
		}
		return null;
	}

	public String getUserData(String name, List<MessagePartHeader> checkList) {
		String toEmail = null;
		for (int a = 0; a < checkList.size(); a++) {
			MessagePartHeader check = checkList.get(a);
			if (check.getName().equals(name)) {
				toEmail = check.getValue().replaceAll("\\s+", "");
				break;
			}
		}
		return toEmail;
	}

}
