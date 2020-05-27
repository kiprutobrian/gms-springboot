package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.SendAs;

import cloudit.africa.GMS.Entity.MarketingTemplate;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Repository.CalenderAppointmentSlotRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.MarketingRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;

@Service
public class MarketingServiceImpl implements MarketingService {

	@Autowired
	UserAppRepositiry usrRepo;

	@Autowired
	MarketingRepository marketingRepo;

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
	public boolean marketingUpdatesAccounts(KeyValue keyValue) {
		// TODO Auto-generated method stub
		String marketingBody;
		MarketingTemplate marketingTemplateSelected = marketingRepo.findById(Integer.parseInt(keyValue.getKey())).get();
		marketingBody = marketingTemplateSelected.getMarketingBody();
		
		for (int a = 0; a < keyValue.getOrgUser().size(); a++) {
			String emailAdress=keyValue.getOrgUser().get(a).getEmailAdress().replace("\"", "");	;
			String memeberId=keyValue.getOrgUser().get(a).getMemberId().replace("\"", "");	
			Gmail marketingGmailSercvice = null;
			try {
				marketingGmailSercvice= ServiceAccount.getGmailService(emailAdress);
				SendAs oldSendAs =marketingGmailSercvice.users().settings().sendAs().get(emailAdress, emailAdress).execute();
				String[] CurrentSignature= oldSendAs.getSignature().split("<p style=\"display:none\">marketingbranding</p>");
				SendAs newSendAs = new SendAs();
				newSendAs.setSignature(CurrentSignature[0]+"<p style=\"display:none\">marketingbranding</p>"+marketingBody);
				marketingGmailSercvice.users().settings().sendAs().patch(emailAdress, emailAdress, newSendAs).execute();
			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean marketingRemoveFromAccounts(KeyValue keyValue) {
		// TODO Auto-generated method stub
		
		String marketingBody;
		
		for (int a = 0; a < keyValue.getOrgUser().size(); a++) {
			String emailAdress=keyValue.getOrgUser().get(a).getEmailAdress();		
			Gmail marketingGmailSercvice = null;
			try {
				marketingGmailSercvice= ServiceAccount.getGmailService(emailAdress);
				SendAs oldSendAs =marketingGmailSercvice.users().settings().sendAs().get(emailAdress, emailAdress).execute();
				String[] CurrentSignature= oldSendAs.getSignature().split("<p style=\"display:none\">marketingbranding</p>");
				SendAs newSendAs = new SendAs();
				newSendAs.setSignature(CurrentSignature[0]);
				marketingGmailSercvice.users().settings().sendAs().patch(emailAdress, emailAdress, newSendAs).execute();
			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}

	
}
