package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Delegate;

import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;

@Service
public class DelegationServiceImpl implements DelegationService {

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

	@Override
	public boolean delegationUpdatesAccounts(KeyValue keyValue) {

		for (int a = 0; a < keyValue.getOrgUser().size(); a++) {

			String emailAddress = keyValue.getOrgUser().get(a).getEmailAdress();
			System.out.println("delegate account" + emailAddress);
			System.out.println("delegateter account" + keyValue.getKey());
			System.out.println("delegate account" + authenticationManager.getUserCredentials().getEmail());

			Delegate deligation = new Delegate();
			deligation.setDelegateEmail(keyValue.getKey());

			Gmail delegateAccountUpdate = null;
			try {
				if (!(emailAddress.equals(keyValue.getKey()))) {
					delegateAccountUpdate = ServiceAccount.getGmailService(emailAddress);
					delegateAccountUpdate.users().settings().delegates().create(emailAddress, deligation).execute();
				}

			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		return true;
	}

	@Override
	public boolean delegationDeleteAccounts(KeyValue keyValue) {
		// TODO Auto-generated method stub

		for (int a = 0; a < keyValue.getOrgUser().size(); a++) {

			String emailAddress = keyValue.getOrgUser().get(a).getEmailAdress();
			System.out.println("delegate account" + emailAddress);
			System.out.println("delegate account" + authenticationManager.getUserCredentials().getEmail());
			System.out.println("	PERSONS ACCOUNT KEY "+keyValue.getKey());
			
			Delegate deligation = new Delegate();
			deligation.setDelegateEmail(keyValue.getKey());

			Gmail delegateAccountUpdate = null;
			try {
				if (!(keyValue.getKey().equals(emailAddress))) {
					
					delegateAccountUpdate = ServiceAccount.getGmailService(keyValue.getKey());
					delegateAccountUpdate.users().settings().delegates().delete(keyValue.getKey(), emailAddress).execute();
					
				}

			} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

}
