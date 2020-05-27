package cloudit.africa.GMS.Controller.UserManagment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Groups;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Delegate;
import com.google.api.services.gmail.model.ListDelegatesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.SendAs;
import com.google.gdata.util.ServiceException;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.RoleAccess;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.TemplateType;
import cloudit.africa.GMS.Model.UpdateDirectory;
import cloudit.africa.GMS.Model.myUser;
import cloudit.africa.GMS.Repository.MarketingRepository;
import cloudit.africa.GMS.Repository.OrganizationMemberRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.Repository.UserRoleRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.SecurityConfigurations.ModelDataView;
import cloudit.africa.GMS.ServiceAccount.ContactApiService;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class UserManagment {

	Directory serviceDirect;
	Gmail serviceGmail;
	List<User> users;

	@Autowired
	UserAppRepositiry userrepo;

	@Autowired
	UserAppRepositiry userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	OrganizationMemberRepository organizationMemberRepository;

	@Autowired
	DirectoryService directoryService;

	@Autowired
	GmailApiServices gmailApiServices;

	@Autowired
	PortalHtmlTemplatesRepository portalHtmlTemplatesRepository;

	@Autowired
	SignatureRepository singnatureRepo;

	@Autowired
	MarketingRepository marketingRepo;

	@Autowired
	ModelDataView modelDataView;
	
	@Autowired
	GlobalModelView globalModelView;

	UserManagmentUtils userManagmentUtils = new UserManagmentUtils();

	@RequestMapping("Gsuit/login")
	public String getUserLogin(Model model) throws IOException, GeneralSecurityException, URISyntaxException {
		return "login";

	}

	@RequestMapping("usermanagment")
	public String getUserManagmentPage(Model model, HttpServletRequest request)
			throws IOException, GeneralSecurityException, URISyntaxException {
		
		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);
		List<com.google.api.services.admin.directory.model.User> myOrgUsers = directoryService.getDomainUsers();
		model.addAttribute("users", myOrgUsers);
		return "gms-usermanagemntlist";
	}

	@GetMapping(value = "viewProfile/{userId}")
	public String getUserProfile(@PathVariable("userId") String userId, Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);

		try {
			UpdateDirectory dataUpdate = new UpdateDirectory();
			dataUpdate.setId(userId);
			
			User user = directoryService.getDomainUser(userId);
			List<User> userList = directoryService.getDomainUsers();
			userList.remove(user);

			Gmail serviceGmail = ServiceAccount.getGmailService(user.getPrimaryEmail());
			ListDelegatesResponse deligateData = serviceGmail.users().settings().delegates().list(user.getPrimaryEmail()).execute();
			List<Delegate> deligateList = new ArrayList<Delegate>();
		
			try {
				if(deligateData.getDelegates() !=null)deligateList = deligateData.getDelegates();
			}catch(NullPointerException s) {}
			
			SendAs sendAss = serviceGmail.users().settings().sendAs().get(user.getPrimaryEmail(), user.getPrimaryEmail()).execute();

			List<User> accounts = userManagmentUtils.getAccountsAvailableForDelegation(userList, deligateList);
			TemplateType templateType = userManagmentUtils.getSignatureMaketingCalenderTemplate(sendAss.getSignature());

			model.addAttribute("domainsignatures", singnatureRepo.findByCompany(authenticatedUser.getCompany()));
			model.addAttribute("domainmarketing", marketingRepo.findByCompany(authenticatedUser.getCompany()));

			modelDataView.getModelData(request, model, user, deligateList, accounts, templateType);

		} catch (IOException | GeneralSecurityException | NullPointerException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "gms-usermangmentinfo";

	}

	@RequestMapping("deleteUser/{userId}")
	public String getdelete(@PathVariable("userId") String userId, Model model) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		Optional<UserApp> usertoDelete = userrepo.findById(userId);
		model.addAttribute("image", "/jMega avax.faces.resource/images/hands.png?ln=california-layout");
		model.addAttribute("domain", authenticatedUser.getEmail().split("@")[1]);
		Directory serviceDirect;
		try {

			serviceDirect = ServiceAccount.getDirectoryServices(authenticatedUser.getEmail());
			serviceDirect.users().delete(usertoDelete.get().getEmail()).execute();

			Groups cfdf = serviceDirect.groups().list().execute();
			userrepo.delete(usertoDelete.get());

		} catch (GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("" + e.getMessage());

		}

		return "redirect:/usermanagment?sucesss";

	}

	@RequestMapping(value = "createUser", method = RequestMethod.GET)
	public String getCreateUser(Model model,HttpServletRequest request) throws IOException, GeneralSecurityException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);

		model.addAttribute("domainUsers", directoryService.getDomainUsers());
		model.addAttribute("mysignature", singnatureRepo.findByCompany(authenticatedUser.getCompany()));
		myUser user = new myUser();
		user.setCretedById(authenticatedUser.getId());
		model.addAttribute("myUser", user);
		return "gms-usermanagmentcreateaccount";
	}

	@RequestMapping(value = "/contactReseller")
	public String upContactReseller() {

		String htmlbody = portalHtmlTemplatesRepository.findById(4).get().getHtmlTemplate();
		String messageBody = htmlbody.replace("numberoflicence", "12");
		try {
			Message message = gmailApiServices.sendEmailMessage("me", "1", "23", messageBody);
			return message.getId();
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
