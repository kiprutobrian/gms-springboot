package cloudit.africa.GMS.SecurityConfigurations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Users;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.DriveWorkFlow;
import cloudit.africa.GMS.Entity.Notification;
import cloudit.africa.GMS.Entity.OrganizationMembers;
import cloudit.africa.GMS.Entity.Role;
import cloudit.africa.GMS.Entity.RoleAccess;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.UserRole;
import cloudit.africa.GMS.Entity.WorkFlow;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.GoogleApiServices.GoogleOuthAPI;
import cloudit.africa.GMS.Model.AuthenticationValidater;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Repository.DriveWorkFlowRepository;
import cloudit.africa.GMS.Repository.GmsFileRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.OrganizationMemberRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.Repository.UserRoleRepository;
import cloudit.africa.GMS.Repository.WorkFlowRepository;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.HtmlTemplates;
import cloudit.africa.GMS.Utilities.Utilities;

@Configuration
public class GMSAuthenticationManager implements AuthenticationManager {

	@Autowired
	UserAppRepositiry userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	@Autowired
	NotificationRepository notificationRepositry;

	@Autowired
	OrganizationMemberRepository organizationMemberRepository;

	@Autowired
	GmailApiServices gmailApiServices;

	@Autowired
	ImageUrlRepository imageUrlRepository;

	@Autowired
	PortalHtmlTemplatesRepository portalHtmlTemplatesRepository;

	@Autowired
	GoogleOuthAPI googleOuthAPI;
//	
	@Autowired
	WorkFlowRepository workFlowRepository;

	@Autowired
	DriveWorkFlowRepository driveWorkFlowRepository;

	@Autowired
	GmsFileRepository gmsFileRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public GoogleCredential getGoogleCredential() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		try {
			if (authentication.isAuthenticated() && !authentication.getName().isEmpty()) {
				JsonObject jsonObject = new Gson()
						.toJsonTree(authentication.getDetails(), authentication.getDetails().getClass())
						.getAsJsonObject();
				GoogleCredential credential = new GoogleCredential()
						.setAccessToken("" + jsonObject.get("tokenValue").toString().replace("\"", ""));
				return credential;
			}

		} catch (NullPointerException nlxp) {

		}
		return null;

	}

	public static GoogleCredential getGoogleCredentials(String token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		try {
			if (authentication.isAuthenticated() && !authentication.getName().isEmpty()) {
				JsonObject jsonObject = new Gson()
						.toJsonTree(authentication.getDetails(), authentication.getDetails().getClass())
						.getAsJsonObject();
				GoogleCredential credential = new GoogleCredential()
						.setAccessToken("" + jsonObject.get("tokenValue").toString().replace("\"", ""));
				return credential;
			}

		} catch (NullPointerException nlxp) {

		}
		return null;

	}

	public UserApp getUserCredentials() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			Optional<UserApp> user = userRepository.findById(auth.getName());
			return user.get();
		} else {
			return null;
		}
	}

	public AuthenticationValidater getUserLoginCredentials() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AuthenticationValidater authenticatinValideter = new AuthenticationValidater();
		GoogleCredential credts = getGoogleCredential();
		credts.setExpiresInSeconds((long) 10000);
		System.out.println("TOKEN EXPIERY TIME " + credts.getExpiresInSeconds());
		googleOuthAPI.getUserInfoPluse();

		return authenticatinValideter;
	}

	public Model getViewStaticData(Model model, UserApp userApp, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Services serviceAcess = getSerivesAcess(userApp);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Notification> notifications = notificationRepositry.findByUserApps(userApp.getId());
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		map.put("_csrf.token", token.getToken());
		map.put("_csrf.parameterName", token.getParameterName());
		map.put("_csrf_header", token.getHeaderName());
		map.put("notifications", notifications);
		map.put("notificationsize", notifications.size());
		map.put("authUser", userApp);
		map.put("Services", serviceAcess);
		return model.addAllAttributes(map);
	}

	public Services getSerivesAcess(UserApp user) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Services DisplayRoleAccessService = new Services();
		Optional<List<UserRole>> roleList = userRoleRepository.findByUserApp(user);
		RoleAccess roleAccessSetUp = new RoleAccess();
		if (roleList.isPresent()) {
			for (int roles = 0; roles < roleList.get().size(); roles++) {
				UserRole roleAcess = roleList.get().get(roles);
				RoleAccess myroleAccess = roleAcess.getRole().getRoleAcess();
				if (myroleAccess.isSignature()) {
					roleAccessSetUp.setSignature(true);
				}
				if (myroleAccess.isMarkertingBranding()) {
					roleAccessSetUp.setMarkertingBranding(true);
				}
				if (myroleAccess.isMailDelegation()) {
					roleAccessSetUp.setMailDelegation(true);
				}
				if (myroleAccess.isDriveAnalysis()) {
					roleAccessSetUp.setDriveAnalysis(true);
				}
				if (myroleAccess.isCalenderAppointment()) {
					roleAccessSetUp.setCalenderAppointment(true);
				}
				if (myroleAccess.isEmailAnalysis()) {
					roleAccessSetUp.setEmailAnalysis(true);
				}
				if (myroleAccess.isUserManegment()) {
					roleAccessSetUp.setUserManegment(true);
				}
				if (myroleAccess.isRegistration()) {
					roleAccessSetUp.setRegistration(true);
				}
				if (myroleAccess.isSetting()) {
					roleAccessSetUp.setSetting(true);
				}
				if (myroleAccess.isHr()) {
					roleAccessSetUp.setHr(true);
				}
				if (myroleAccess.isBilling()) {
					roleAccessSetUp.setBilling(true);
				}
				if (myroleAccess.isReporting()) {
					roleAccessSetUp.setReporting(true);
				}
				if (myroleAccess.isDataMigration()) {
					roleAccessSetUp.setDataMigration(true);
				}
			}

		}

		Company comp = user.getCompany();
		comp.getPackages();

		try {

			RoleAccess rolesAcesses = roleAccessSetUp;

			DisplayRoleAccessService.setBilling(
					Utilities.getRightsAcess(comp.getPackages().getServices().isBilling(), rolesAcesses.isBilling()));
			DisplayRoleAccessService.setSignature(Utilities
					.getRightsAcess(comp.getPackages().getServices().isSignature(), rolesAcesses.isSignature()));
			DisplayRoleAccessService.setEmailAnalysis(Utilities.getRightsAcess(
					comp.getPackages().getServices().isEmailAnalysis(), rolesAcesses.isEmailAnalysis()));
			DisplayRoleAccessService.setDriveAnalysis(Utilities.getRightsAcess(
					comp.getPackages().getServices().isDriveAnalysis(), rolesAcesses.isDriveAnalysis()));
			DisplayRoleAccessService.setCalenderApointment(Utilities.getRightsAcess(
					comp.getPackages().getServices().isCalenderApointment(), rolesAcesses.isCalenderAppointment()));
			DisplayRoleAccessService
					.setHr(Utilities.getRightsAcess(comp.getPackages().getServices().isHr(), rolesAcesses.isHr()));
			DisplayRoleAccessService.setMaildelegation(Utilities.getRightsAcess(
					comp.getPackages().getServices().isMaildelegation(), rolesAcesses.isMailDelegation()));
			DisplayRoleAccessService.setUserManegment(Utilities.getRightsAcess(
					comp.getPackages().getServices().isUserManegment(), rolesAcesses.isUserManegment()));
			DisplayRoleAccessService.setMarketingBranding(Utilities.getRightsAcess(
					comp.getPackages().getServices().isMarketingBranding(), rolesAcesses.isMarkertingBranding()));
			DisplayRoleAccessService.setSetting(
					Utilities.getRightsAcess(comp.getPackages().getServices().isSetting(), rolesAcesses.isSetting()));
			DisplayRoleAccessService.setRegistration(Utilities
					.getRightsAcess(comp.getPackages().getServices().isRegistration(), rolesAcesses.isRegistration()));

			DisplayRoleAccessService.setDataMigration(Utilities.getRightsAcess(
					comp.getPackages().getServices().isDataMigration(), rolesAcesses.isDataMigration()));
			DisplayRoleAccessService.setReporting(Utilities
					.getRightsAcess(comp.getPackages().getServices().isReporting(), rolesAcesses.isReporting()));

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		return DisplayRoleAccessService;

	}

	public boolean executeWorkFlowCheckerNew(Checker checker, String actionType, Object object) {

		KeyValue KeyValue = (KeyValue) object;
		UserApp authenticatedUser = getUserCredentials();

		String uniqueID = UUID.randomUUID().toString();

		WorkFlow workflow = new WorkFlow();
		workflow.setKey(KeyValue.getKey());
		workflow.setAccountsAction(KeyValue.getOrgUser());
		workflow.setName(actionType);
		workflow.setUrlType("/postUpdate");
		workflow.setToken(uniqueID);
		workflow.setTokenExpirationTime(Utilities.setDateExpirationTime(2));
		workflow.setCreatedAt(new Date());
		workflow.setCreatedBy(authenticatedUser);
		workflow.setApprover(checker.getChecker().getEmail());
		workflow.setCompany(authenticatedUser.getCompany());

		organizationMemberRepository.saveAll(KeyValue.getOrgUser());
//		store workflow process
		WorkFlow results = workFlowRepository.saveAndFlush(workflow);
		results.getId();

//		template body
		String Htmltemplate = portalHtmlTemplatesRepository.findById(3).get().getHtmlTemplate();

//		setApprovalLink
		String approvelink = gmsUrlsRepository.findById(5).get().getUrl() + "" + results.getId() + "/"
				+ results.getToken();

		String applicationlogo = imageUrlRepository.findById(1).get().getImageUrl();

		System.out.println("AUTHENTICATED USER xxxx  :  " + authenticatedUser);
		System.out.println("CHECKER USERNAME xxxx  :  " + checker.getChecker().getUsername());
		System.out.println("TEMPLATE xxxx  :  " + Htmltemplate);
		System.out.println("CHECKER NAME xxxx  :  " + checker.getMakerCheckers().getName());
		System.out.println("APPROVAL LINK xxxx  :  " + approvelink);
		System.out.println("LOGOG xxxx  :  " + applicationlogo);

		String finalEmailBodyTemplate = HtmlTemplates.makerCheckerTemlate(authenticatedUser,
				checker.getChecker().getUsername(), Htmltemplate, checker.getMakerCheckers().getName(), approvelink,
				applicationlogo);

		try {
			gmailApiServices.sendMessage(authenticatedUser.getId(), checker.getChecker().getEmail(),
					finalEmailBodyTemplate);
			Notification notification = new Notification();
			notification.setUserApp(checker.getChecker());
			notification.setFromUser(authenticatedUser.getEmail());
			notification.setTitle(checker.getMakerCheckers().getName());
			notification.setMessage(checker.getMakerCheckers().getDescrption());
			notification.setCreatedDate(new Date());
			notification.setUpdateDate(new Date());
			notification.setMessageHtmlBody(finalEmailBodyTemplate);
			notificationRepositry.saveAndFlush(notification);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	public boolean executeWorkFlowCheckerDrive(Checker checker, String actionType, Object object) {

		DriveWorkFlow driveWorkFlow = (DriveWorkFlow) object;
		UserApp authenticatedUser = getUserCredentials();

		String uniqueID = UUID.randomUUID().toString();
//		System.out.print("===================" + driveWorkFlow);

		driveWorkFlow.setUrlType("/postUpdate");
		driveWorkFlow.setName(actionType);
		driveWorkFlow.setToken(uniqueID);
		driveWorkFlow.setTokenExpirationTime(Utilities.setDateExpirationTime(2));

//		store workflow process

		gmsFileRepository.saveAll(driveWorkFlow.getGmsFiles());
		DriveWorkFlow results = driveWorkFlowRepository.saveAndFlush(driveWorkFlow);
		results.getId();

//		template body
		String Htmltemplate = portalHtmlTemplatesRepository.findById(3).get().getHtmlTemplate();

//		setApprovalLink
		String approvelink = gmsUrlsRepository.findById(6).get().getUrl() + "" + results.getId() + "/"
				+ results.getToken();

		String applicationlogo = imageUrlRepository.findById(1).get().getImageUrl();

		System.out.println("AUTHENTICATED USER xxxx  :  " + authenticatedUser);
		System.out.println("CHECKER USERNAME xxxx  :  " + checker.getChecker().getUsername());
		System.out.println("TEMPLATE xxxx  :  " + Htmltemplate);
		System.out.println("CHECKER NAME xxxx  :  " + checker.getMakerCheckers().getName());
		System.out.println("APPROVAL LINK xxxx  :  " + approvelink);
		System.out.println("LOGOG xxxx  :  " + applicationlogo);

		String finalEmailBodyTemplate = HtmlTemplates.makerCheckerTemlate(authenticatedUser,
				checker.getChecker().getUsername(), Htmltemplate, checker.getMakerCheckers().getName(), approvelink,
				applicationlogo);

		try {
			gmailApiServices.sendMessage(authenticatedUser.getId(), checker.getChecker().getEmail(),
					finalEmailBodyTemplate);
			Notification notification = new Notification();
			notification.setUserApp(checker.getChecker());
			notification.setFromUser(authenticatedUser.getEmail());
			notification.setTitle(checker.getMakerCheckers().getName());
			notification.setMessage(checker.getMakerCheckers().getDescrption());
			notification.setCreatedDate(new Date());
			notification.setUpdateDate(new Date());
			notification.setMessageHtmlBody(finalEmailBodyTemplate);
			notificationRepositry.saveAndFlush(notification);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

}
