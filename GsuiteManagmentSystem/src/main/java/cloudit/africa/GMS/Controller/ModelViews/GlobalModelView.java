package cloudit.africa.GMS.Controller.ModelViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;

import com.google.api.services.admin.directory.model.User;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.Notification;
import cloudit.africa.GMS.Entity.RoleAccess;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.UserRole;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.GoogleApiServices.GoogleOuthAPI;
import cloudit.africa.GMS.Repository.CompanyRepository;
import cloudit.africa.GMS.Repository.DriveWorkFlowRepository;
import cloudit.africa.GMS.Repository.GmsFileRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.OrganizationMemberRepository;
import cloudit.africa.GMS.Repository.PackageRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.Repository.UserRoleRepository;
import cloudit.africa.GMS.Repository.WorkFlowRepository;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageHeader;
import cloudit.africa.GMS.Utilities.Config.PageMenu;

@Configuration
public class GlobalModelView {
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
	
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	
	public Model getViewStaticData(Model model, UserApp userApp, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Services serviceAcess = getSerivesAcess(userApp);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Notification> notifications = notificationRepositry.findByUserApps(userApp.getId());
		df.setTimeZone(TimeZone.getTimeZone("GMT+03"));
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		System.out.println("Date for me 1 : "+df.format(new Date()));
		System.out.println("Date for me 2 : "+format.format(new Date()));
		
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		map.put("header", PageHeader.appHeader.replace("imageUrl", userApp.getImageUrl()));
		map.put("sidenaveMenu", PageMenu.menuItemsView);
		
		map.put("_csrf.token", token.getToken());
		map.put("_csrf.parameterName", token.getParameterName());
		map.put("_csrf_header", token.getHeaderName());
		map.put("notifications", notifications);
		map.put("notificationsize", notifications.size());
		map.put("authUser", userApp);
		map.put("Services", serviceAcess);
		model.addAttribute("userId", "" + userApp.getId());
		
		
		return model.addAllAttributes(map);
	}
	
	
	public Model getRegistrationViewStaticData(Model model, User user, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> map = new HashMap<String, Object>();
		List<cloudit.africa.GMS.Entity.Package> packagesList = packageRepository.findAll();
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		map.put("_csrf.token", token.getToken());
		map.put("_csrf.parameterName", token.getParameterName());
		map.put("_csrf_header", token.getHeaderName());
		
		Company company=companyRepository.findById(user.getCustomerId()).get();
		map.put("packages", packagesList);
		map.put("Company", company);
		map.put("userId", user.getId());
		
		System.out.println("IS VERIFIED -----------"+company.isCustomerDomainVerified());
		map.put("isverfied", company.isCustomerDomainVerified());
		
		map.put("authUser", user);
		
				
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


}
