package cloudit.africa.GMS.Controller.Delegation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Delegate;
import com.google.api.services.gmail.model.ListDelegatesResponse;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GMSApiServices.DelegationService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.UpdateDirectory;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;

@RestController
public class DelegationRest {

	@Autowired
	UserAppRepositiry usrRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	DelegationService delegationService;

	@Autowired
	AdminSettingService adminSetting;

	@Autowired
	DirectoryService directoryService;

	@RequestMapping("/search")
	public @ResponseBody List searchPost(@RequestParam("term") String query) {

		List<Object> retVal = getListOfObjectFromDbBasedOnQuery(query);

		return retVal;
	}

	private List<Object> getListOfObjectFromDbBasedOnQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping("DelegateAccount")
	public List<Delegate> AccountToDeligation(@RequestBody String delegateEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {

		List<Delegate> deleligates = null;
		Gmail serviceGmail = ServiceAccount.getGmailService(delegateEmail);
		ListDelegatesResponse deligateData = serviceGmail.users().settings().delegates().list(delegateEmail).execute();
		deleligates = deligateData.getDelegates();
		try {
			deleligates.size();
		} catch (NullPointerException e) {
			// TODO: handle exception
			deleligates = new ArrayList<Delegate>();
			Delegate delempty=new Delegate();
			delempty.setDelegateEmail("empty");
			deleligates.add(delempty);
		}
		
		System.out.println("DELEGATES "+deleligates);

		return deleligates;
	}


	@RequestMapping("/postAccountDelegation")
	public KeyValue postAccountDelegation(@RequestBody KeyValue keyValue) {

		UserApp user = authenticationManager.getUserCredentials();
		Checker checker = adminSetting.getChecker("" + 3);

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
//					calenderService.updateAppointmentBookingSlot(keyValue);
					delegationService.delegationUpdatesAccounts(keyValue);
				} else {
					System.out.println("" + keyValue);
					System.out.println("USER IS NOT CHECKER");
					authenticationManager.executeWorkFlowCheckerNew(checker, "UPDATE_DELEGATE", keyValue);
				}
			} else {
				System.out.println("MARKER CHECKER iS DISABLED");
				delegationService.delegationUpdatesAccounts(keyValue);
			}
		} else {
			System.out.println("MARKER CHECKER iS NOT PRESENT");
			delegationService.delegationUpdatesAccounts(keyValue);
		}

		return keyValue;
	}

	@RequestMapping(value = "processDeligationAccount")
	public UpdateDirectory processDeligationForm(@RequestBody UpdateDirectory dataUpdate)
			throws IOException, GeneralSecurityException, URISyntaxException {

		String emailAdress = ((dataUpdate.getEmailAdress()).toLowerCase()).replaceAll("\\s+", "");

		System.out.println("EMAIL Delegate----" + emailAdress);
		System.out.println("ID Delegate----" + dataUpdate.getId());

		Gmail serviceGmail = ServiceAccount.getGmailService(dataUpdate.getId());
		Delegate deligation = new Delegate();
		deligation.setDelegateEmail(emailAdress);
		serviceGmail.users().settings().delegates().create(dataUpdate.getId(), deligation).execute();

		return dataUpdate;
	}

}
