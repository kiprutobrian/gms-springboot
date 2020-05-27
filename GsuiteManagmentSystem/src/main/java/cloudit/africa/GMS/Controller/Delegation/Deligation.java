package cloudit.africa.GMS.Controller.Delegation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Delegate;
import com.google.api.services.gmail.model.ListDelegatesResponse;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.OrganizationMembers;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.DelegationService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.UpdateDirectory;
import cloudit.africa.GMS.Repository.OrganizationMemberRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class Deligation {

	Directory serviceDirect;
	Gmail serviceGmail;
	List<User> users;

	@Autowired
	UserAppRepositiry userrepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	DelegationService delegationService;

	@Autowired
	OrganizationMemberRepository orgrepo;

	@Autowired
	DirectoryService directoryService;
	
	@Autowired
	GlobalModelView globalModelView;


	@RequestMapping("Delegation")
	public String AccountDeligation(Model model, final HttpServletRequest request)
			throws GeneralSecurityException, IOException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		List<com.google.api.services.admin.directory.model.User> myOrgUsers = directoryService.getDomainUsers();
		model.addAttribute("users", myOrgUsers);

		return "gms-delegationuserlist";
	}

	@RequestMapping("DelegateAccounts/{delegateId}/")
	public String AccountToDeligation(@PathVariable("delegateId") String delegateId, Model model,
			final HttpServletRequest request) throws GeneralSecurityException, IOException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		User user = directoryService.getDomainUser(delegateId);

		List<User> userList = directoryService.getDomainUsers();

		String delegateEmail = user.getPrimaryEmail();

		serviceGmail = ServiceAccount.getGmailService(delegateEmail);
		UpdateDirectory dataUpdate = new UpdateDirectory();
		dataUpdate.setId(delegateId);

		model.addAttribute("AccountToDelegate", user);

		model.addAttribute("updateuser", dataUpdate);
		List<String> names = new ArrayList<String>();
		names.add("E");
		names.add("s");
		names.add("c");
		names.add("d");
		names.add("g");

		model.addAttribute("list", names);

		serviceGmail = ServiceAccount.getGmailService(delegateEmail);
		ListDelegatesResponse deligateData = serviceGmail.users().settings().delegates().list(delegateEmail).execute();
		List<Delegate> deleligates = deligateData.getDelegates();
		model.addAttribute("deligateList", deleligates);
		try {
			model.addAttribute("DelegatesNumber", deleligates.size());

		} catch (NullPointerException e) {
			// TODO: handle exception
			model.addAttribute("DelegatesNumber", 0);
		}

		return "gms-delegationform";

	}

//	@RequestMapping(value = "RemoveDelegateAccount/{Id}/{accountId}", method = RequestMethod.GET)
//	public String RemoveDelegateAccount(@PathVariable("Id") String Id, @PathVariable("accountId") String accountId)
//			throws IOException, GeneralSecurityException, URISyntaxException {
//		String[] myId = Id.split("lg");
//		String emailToRemove = myId[0];
//		String delegateccount = myId[1];
//
//		System.out.println("DELEGATE ACCOUNT ======================" + delegateccount);
//		System.out.println("EMAIL TO REMOVE  ======================" + emailToRemove);
//		System.out.println("ACCPOUNT ID      ======================" + accountId);
//
////		UserApp authenticatedUser = authenticationManager.getUserCredentials();
//		
//		List<OrganizationMembers> organis = new ArrayList<OrganizationMembers>();
//		OrganizationMembers organizationMembers=new OrganizationMembers();
//		organizationMembers.setEmailAdress(emailToRemove);
//		organis.add(organizationMembers);
//		
//		KeyValue keyValue = new KeyValue();
//		keyValue.setKey(delegateccount);
//		keyValue.setOrgUser(organis);
//
//		delegationService.delegationDeleteAccounts(keyValue);
////				
////		serviceGmail = SercicesAccounts.getGmailService(delegateccount);
////		serviceGmail.users().settings().delegates().delete(delegateccount, emailToRemove).execute();
//
//		return "redirect:/DelegateAccount/" + accountId;
//
//	}

	@RequestMapping(value = "RemoveDelegateAccount/{Id}/{accountId}/", method = RequestMethod.GET)
	public String RemoveDelegateAccount(@PathVariable("Id") String selectedperson,
			@PathVariable("accountId") String emailToRemove)
			throws IOException, GeneralSecurityException, URISyntaxException {
		
		List<OrganizationMembers> organis = new ArrayList<OrganizationMembers>();
		OrganizationMembers organizationMembers = new OrganizationMembers();
		organizationMembers.setEmailAdress(emailToRemove);
		organis.add(organizationMembers);

		KeyValue keyValue = new KeyValue();
		keyValue.setKey(selectedperson);
		keyValue.setOrgUser(organis);

		delegationService.delegationDeleteAccounts(keyValue);
		return "redirect:/Delegation";

	}

}
