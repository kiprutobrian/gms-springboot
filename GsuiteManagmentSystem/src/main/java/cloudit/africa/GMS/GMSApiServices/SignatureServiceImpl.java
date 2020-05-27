package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.SendAs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cloudit.africa.GMS.Entity.SignatureTemplate;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.Organization;
import cloudit.africa.GMS.Repository.CalenderAppointmentSlotRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class SignatureServiceImpl implements SignatureService {

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
	public boolean signatureUpdatesAccounts(KeyValue keyValue) {

		String signatureBody;
		SignatureTemplate signatureTemplateSelected = signaterRepo.findById(Integer.parseInt(keyValue.getKey())).get();
		signatureBody = signatureTemplateSelected.getSignatureBody();
		
		List<com.google.api.services.admin.directory.model.User> myOrgUsers=null;
		try {
			com.google.api.services.admin.directory.Directory directoryService = ServiceAccount.getDirectoryServices(authenticationManager.getUserCredentials().getServiceEmailAdress());
			Users result = directoryService.users().list().setCustomer("my_customer").setOrderBy("email").execute();
			myOrgUsers=result.getUsers();
		} catch (GeneralSecurityException | IOException | URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Gmail signatureGmailService = null;
		for (int a = 0; a < keyValue.getOrgUser().size(); a++) {
			String emailAdress = (keyValue.getOrgUser().get(a).getEmailAdress()).replace("\"", "");
			try {
				signatureGmailService = ServiceAccount.getGmailService(emailAdress);
				SendAs sendAs = signatureGmailService.users().settings().sendAs().get(emailAdress, emailAdress).execute();
				sendAs.setSendAsEmail(emailAdress);
				String oldSignature[] = sendAs.getSignature().split("<p style=\"display:none\">marketingbranding</p>");
				String getsignatureAndCalender = oldSignature[0];
				String[] getSignatureOnly = getsignatureAndCalender.split("<p style=\"display:none\">appointmentstart</p>");
				
				for(int dirus=0;dirus<myOrgUsers.size();dirus++){
					String memeberId=keyValue.getOrgUser().get(a).getMemberId().replace("\"", "");			
					if(myOrgUsers.get(dirus).getId().equals(memeberId)) {
						User user=myOrgUsers.get(dirus);						
						System.out.println(user);						
						String firstanem =user.getName().getGivenName();
						String lastname =user.getName().getFamilyName();
						String emailAddress =user.getPrimaryEmail();
						String phoneNumber=Utilities.getValue(user.getPhones());								
						String JobPosition=Utilities.getValues(user.getOrganizations(),"title");														
						String department=Utilities.getValues(user.getOrganizations(),"department");
						String costCenter=Utilities.getValues(user.getOrganizations(),"costCenter");		
						
						String newSignatureTemplate = (signatureBody
								.replaceAll("FirstName", Utilities.getEmptyNullStringValue(firstanem)))
								.replaceAll("LastName", Utilities.getEmptyNullStringValue(lastname))
								.replaceAll("PhoneNumber", Utilities.getEmptyNullStringValue(phoneNumber))
								.replaceAll("EmailAddress", emailAddress)
								.replaceAll("Department", department)
								.replaceAll("CostCenter", costCenter)
								.replace("JobTitle", Utilities.getEmptyNullStringValue(JobPosition));
						
						if (oldSignature.length ==2 && getSignatureOnly.length == 2) {
							System.out.print("BOTH ARE PRESENT");
							sendAs.setSignature(newSignatureTemplate
									+ ("<p style=\"display:none\">appointmentstart</p>" + getSignatureOnly[1])
									+ ("<p style=\"display:none\">marketingbranding</p>" + oldSignature[1]));
						} else if (oldSignature.length == 2 && getSignatureOnly.length == 1) {
							System.out.print("oldSignature IS PRESENT");
							sendAs.setSignature(newSignatureTemplate
									+ ("<p style=\"display:none\">marketingbranding</p>" + oldSignature[1]));
						} else if (oldSignature.length == 1 && getSignatureOnly.length == 2) {
							System.out.print("BOTH ARE PRESENT");
							sendAs.setSignature(newSignatureTemplate
									+ ("<p style=\"display:none\">appointmentstart</p>" + getSignatureOnly[1]));
						} else if (oldSignature.length == 1 && getSignatureOnly.length == 1) {
							System.out.print("BOTH ARE PRESENT");
							sendAs.setSignature(newSignatureTemplate);
						}
						System.out.println("Email Address " + emailAdress);
						signatureGmailService.users().settings().sendAs()
								.patch(emailAdress, emailAdress, sendAs).execute();							
						signatureGmailService.users().settings().sendAs().patch(emailAdress, emailAdress, sendAs).execute();
					}
				}
			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean signatureDeleteAccounts(KeyValue keyValue) {
		// TODO Auto-generated method stub

		for (int a = 0; a < keyValue.getOrgUser().size(); a++) {

			String emailAdress = (keyValue.getOrgUser().get(a).getEmailAdress()).replace("\"", "");
			UserApp userSelect = usrRepo.findByEmail(emailAdress);

			SendAs sendAs = new SendAs();
			String emptySignature = "";
			sendAs.setSignature(emptySignature);
			sendAs.setSendAsEmail(userSelect.getEmail());
			Gmail signatureAccountUpdate = null;
			try {
				signatureAccountUpdate = ServiceAccount.getGmailService(emailAdress);
				signatureAccountUpdate.users().settings().sendAs().patch(userSelect.getEmail(), userSelect.getEmail(), sendAs).execute();
				SignatureTemplate oldsignature = signaterRepo.findByIsActive(true);
				oldsignature.setIsActive(false);
				signaterRepo.saveAndFlush(oldsignature);
			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
