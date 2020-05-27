package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.SendAs;

import cloudit.africa.GMS.Entity.CalenderAppointmentSlot;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Repository.CalenderAppointmentSlotRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.HtmlTemplates;

@Service
public class CalenderServiceImpl implements CalenderService {

	@Autowired
	UserAppRepositiry usrRepo;

	@Autowired
	SignatureRepository signaterRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GmailApiServices gmailApiServices;


	@Autowired
	PortalHtmlTemplatesRepository portalHtmlTemplatesRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	@Autowired
	ImageUrlRepository imageUrlRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CalenderAppointmentSlotRepository calenderAppointmentRepository;

	@Override
	public boolean updateAppointmentBookingSlot(KeyValue keyValue) {
		// TODO Auto-generated method stub

		String parentUrl = gmsUrlsRepository.findById(2).get().getUrl();
		UserApp userAuthenticated = authenticationManager.getUserCredentials();

		for (int account = 0; account < keyValue.getOrgUser().size(); account++) {
			Gmail gmail;
			try {
				gmail = ServiceAccount.getGmailService(keyValue.getOrgUser().get(account).getEmailAdress());
				com.google.api.services.gmail.model.SendAs sendAs = gmail.users().settings().sendAs()
						.get(keyValue.getOrgUser().get(account).getEmailAdress(),
								keyValue.getOrgUser().get(account).getEmailAdress())
						.execute();
				String oldSignature[] = sendAs.getSignature().split("<p style=\"display:none\">marketingbranding</p>");
				String getsignatureAndCalender = oldSignature[0];
				String[] getSignatureOnly = getsignatureAndCalender.split("<p style=\"display:none\">appointmentstart</p>");

				String link = parentUrl + keyValue.getOrgUser().get(account).getEmailAdress() + "/";
				String calenderAppointmentHtml = HtmlTemplates.appointmentBooking(link);
				String newSignatueCalendeMarketing = "";

				if (oldSignature.length > 1) {
					String marketingBrandingTemplate = ("<p style=\"display:none\">marketingbranding</p>"
							+ oldSignature[1]);
					newSignatueCalendeMarketing = getSignatureOnly[0] + calenderAppointmentHtml
							+ marketingBrandingTemplate;

				} else {
					newSignatueCalendeMarketing = getSignatureOnly[0] + calenderAppointmentHtml;

				}

				SendAs appointmentBooking = new SendAs();
				appointmentBooking.setSignature(newSignatueCalendeMarketing);
				SendAs result = gmail.users().settings().sendAs()
						.update(keyValue.getOrgUser().get(account).getEmailAdress(),
								keyValue.getOrgUser().get(account).getEmailAdress(), appointmentBooking)
						.execute();

				System.out.println("AFTER SUBMITION====" + result);
				CalenderAppointmentSlot calenderAppointment = new CalenderAppointmentSlot();
				calenderAppointment.setAppointmentActive(true);
				calenderAppointment.setId("" + keyValue.getOrgUser().get(account).getMemberId());
				calenderAppointment.setCompanyId(userAuthenticated.getCompany());
				calenderAppointment.setUpdatedAt(new Date());
				calenderAppointmentRepository.saveAndFlush(calenderAppointment);

			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return true;
	}

	@Override
	public boolean deleteAppointmentBookingSlot(KeyValue keyValue) {
		// TODO Auto-generated method stub

		UserApp userAuthenticated = authenticationManager.getUserCredentials();

		for (int account = 0; account < keyValue.getOrgUser().size(); account++) {

			String accountEmailAdress = keyValue.getOrgUser().get(account).getEmailAdress();
			String accountId = keyValue.getOrgUser().get(account).getMemberId();

			Gmail gmail;
			try {
				gmail = ServiceAccount.getGmailService(accountEmailAdress);
				SendAs sendAs = gmail.users().settings().sendAs().get(accountEmailAdress, accountEmailAdress).execute();

				String oldSignature[] = sendAs.getSignature().split("<p style=\"display:none\">marketingbranding</p>");
				String getsignatureAndCalender = oldSignature[0];
				String[] getSignatureOnly = getsignatureAndCalender
						.split("<p style=\"display:none\">appointmentstart</p>");
				SendAs removeCalenderBooking = new SendAs();
				String newSignatueCalendeBookingRemoved = "";

				if (oldSignature.length > 1) {
					String marketingBrandingTemplate = ("<p style=\"display:none\">marketingbranding</p>"
							+ oldSignature[1]);
					newSignatueCalendeBookingRemoved = getSignatureOnly[0] + marketingBrandingTemplate;
				} else {
					newSignatueCalendeBookingRemoved = getSignatureOnly[0];
				}
				removeCalenderBooking.setSignature(newSignatueCalendeBookingRemoved);
				gmail.users().settings().sendAs().update(accountEmailAdress, accountEmailAdress, removeCalenderBooking)
						.execute();

				CalenderAppointmentSlot calenderAppointment = new CalenderAppointmentSlot();
				calenderAppointment.setAppointmentActive(false);
				calenderAppointment.setId(accountId);
				calenderAppointment.setCompanyId(userAuthenticated.getCompany());
				calenderAppointment.setUpdatedAt(new Date());
				calenderAppointmentRepository.saveAndFlush(calenderAppointment);
				
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

}
