package cloudit.africa.GMS.Controller.UserManagment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.google.api.services.reseller.model.Subscription;
import com.google.gdata.util.ServiceException;

import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.ResellerService;
import cloudit.africa.GMS.Model.Appointments;
import cloudit.africa.GMS.Model.UpdateDirectory;
import cloudit.africa.GMS.Model.myUser;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ContactApiService;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.ServiceResponse;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController

@CrossOrigin(origins = "http://localhost:8080")
public class UserManagmentRest {

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	ResellerService resellerService;
	
	@Autowired
	DirectoryService directoryservice;
	
	
	@PostMapping(value = "/processUpdateForm")
	public UpdateDirectory upDateUserInformation(@RequestBody UpdateDirectory dataUpdate)
			throws IOException {
		UserApp authenticatedUser = authenticationManager.getUserCredentials();

		User userx = new User();
		UserName names = new UserName();
		names.setFamilyName(dataUpdate.getFamilyname());
		names.setGivenName(dataUpdate.getGivenName());
		names.setGivenName(dataUpdate.getGivenName());
		names.setFullName(names.getGivenName() + " " + names.getGivenName());
		userx.setPrimaryEmail(dataUpdate.getEmailAdress() + ("@" + authenticatedUser.getEmail().split("@")[1]));
		userx.setName(names);
		

		System.out.println("USERS ID------------" + dataUpdate.getId());
		System.out.println("USERS GIVEN NAME------------" + dataUpdate.getGivenName());
		System.out.println("USERS FULL NAME------------" + dataUpdate.getFullname());
		System.out.println("USERS FAMILY NAME------------" + dataUpdate.getFamilyname());
		System.out.println("USERS EMAIL------------" + dataUpdate.getEmailAdress());

		Directory serviceDirect;

		try {
			serviceDirect = ServiceAccount.getDirectoryServices(authenticatedUser.getEmail());
			User user = serviceDirect.users().update(dataUpdate.getId(), userx).execute();
			System.out.println("USERS------------" + user.toString());
			System.out.println("Eliases------------" + user.getAliases());

			if (user != null && Utilities.getNullStringList(user.getAliases()) != 0) {

				try {

					ContactApiService.ContactService(authenticatedUser.getEmail(), user.getAliases().get(0),
							user.getPrimaryEmail());
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Contact UPDATE Error");
				}
			}

		} catch (GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.out.println("FORWAREDED FOT APPROVAL");
		}

		return dataUpdate;

	}

	
	
	
	@PostMapping("userRegistrations")
	public String getSubmitUser(@RequestBody myUser myuser) {
		
		User users = new User();
		users.setChangePasswordAtNextLogin(true);

		UserName names = new UserName();
		names.setFamilyName("" + myuser.getFamilyName());
		names.setGivenName("" + myuser.getOtherName());
		names.setFullName("" + myuser.getFamilyName() + " " + myuser.getOtherName());
		users.setName(names);		
		users.setPrimaryEmail(myuser.getEmailName() +"@"+ myuser.getDomain());
		String password = myuser.getDefaultPassword();
		users.setPassword(password);

		System.out.println("Before Submition------------- " + users.toString());

		String onsucess = "";
		
		User succeessMessage=directoryservice.createUserOnDomain(users);
		onsucess = succeessMessage.toString();

		if(onsucess!=null || !onsucess.isEmpty()) {
			myUser newUser = new myUser();
			newUser.setCretedById(myuser.getCretedById());
		}
		
		return "userregisteration";

	}



	@PostMapping("createUserApi")
	public ServiceResponse getTesting(@RequestBody String newEmployee) throws IOException, GeneralSecurityException {
		System.out.println("UserSettings");
		Object appointment = newEmployee;
		ServiceResponse response = new ServiceResponse();
		response.setStatus("success");
		response.setData(appointment);
		return response;
	}
	
	
	@RequestMapping("/ResetUserPassword")
	public ServiceResponse getResetUserPassword(@RequestBody UpdateDirectory updateDirectory) throws IOException, GeneralSecurityException {
		ServiceResponse response = new ServiceResponse();
		User user=directoryservice.getDomainUser(updateDirectory.getId());
		directoryservice.updateUserPassword(user, updateDirectory.getPasssword());
		response.setData(user);
		return response;
	}

	@RequestMapping("postSuspendAccounts")
	public List<Appointments> postSuspendAccounts(@RequestBody List<Appointments> emailsAdresses, Model model) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		String authenticateduserAdress = authenticatedUser.getEmail();

		try {
			Directory directoryService = ServiceAccount.getDirectoryServices("edwin@dev.businesscom.dk");
			for (int accounts = 0; accounts < emailsAdresses.size(); accounts++) {
				if (!(emailsAdresses.get(accounts).getEmail().equals(authenticateduserAdress))) {
					User user = new User();
					user.setSuspended(true);
					directoryService.users().patch(emailsAdresses.get(accounts).getEmail(), user).execute();
				}

			}
		} catch (GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emailsAdresses;
	}

	@RequestMapping("postUnSuspendAccounts")
	public List<Appointments> postUnSuspendAccounts(@RequestBody List<Appointments> emailsAdresses, Model model) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		String authenticateduserAdress = authenticatedUser.getEmail();
		try {
			Directory directoryService = ServiceAccount.getDirectoryServices("edwin@dev.businesscom.dk");
			for (int accounts = 0; accounts < emailsAdresses.size(); accounts++) {
				if (!(emailsAdresses.get(accounts).getEmail().equals(authenticateduserAdress))) {
					User user = new User();
					user.setSuspended(false);
					directoryService.users().patch(emailsAdresses.get(accounts).getEmail(), user).execute();
				}

			}
		} catch (GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emailsAdresses;
	}

	@RequestMapping("/postCreateAlias")
	public Appointments postDeleteAccounts(@RequestBody Appointments accountAlias, Model model) {
		
		directoryservice.AddAliasesToAcount(accountAlias.getId(), accountAlias.getEmail());

//		System.out.print("DELETE USERS --------" + emailsAdresses);
		return accountAlias;
	}

	@RequestMapping("/CheckDomainLicense")
	public ServiceResponse isuserOnResellerConsoul() {

		ServiceResponse serviceResponse = new ServiceResponse();
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		String customerId = authenticatedUser.getCompany().getCompanyId();
		String subscriptionId = authenticatedUser.getCompany().getSubscriptionPlan();

		if (subscriptionId.equals("NA")) {
			serviceResponse.setPresent(true);
			serviceResponse.setData(true);
			serviceResponse.setStatus("DOMAIN IS NOT ON OUR RESELLER CONSOUL KINDLY CONTACT YOUR RESELER FOR MORE LICENSES OR PROCEED TO CREATE ACCOUNT");
		} else {
			Subscription subscription = resellerService.getCustomerSubscritption(customerId, subscriptionId);
			Integer currentUserdLicenses = subscription.getSeats().getLicensedNumberOfSeats();
			Integer boughtLicenses = subscription.getSeats().getNumberOfSeats();
			if (subscription.getSeats().getMaximumNumberOfSeats() != 50000) {
				boughtLicenses = subscription.getSeats().getMaximumNumberOfSeats();
			} else {
				boughtLicenses = subscription.getSeats().getNumberOfSeats();
			}

			if (boughtLicenses > currentUserdLicenses) {
				serviceResponse.setPresent(true);
				serviceResponse.setData(false);
				serviceResponse.setStatus("DOMAIN HAS EXTRA LICENSES : " + (boughtLicenses - currentUserdLicenses));
			} else {
				serviceResponse.setPresent(true);
				serviceResponse.setData(true);
				serviceResponse.setStatus("CONTACT YOUR RESELLER FOR MORE LICENSES");
			}

		}
		return serviceResponse;
	}
	
	
	
	

}
