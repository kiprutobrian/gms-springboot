package cloudit.africa.GMS.Controller.SignatureMarketing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.MarketingTemplate;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GMSApiServices.MarketingService;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.Marketing;
import cloudit.africa.GMS.Repository.MarketingRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@RestController
public class MarketingRest {

//	@Autowired
//	UserAppRepositiry usrRepo;

	@Autowired
	MarketingRepository marketingRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;
//
//	@Autowired
//	GmailApiServices gmailApiServices;
//
//	@Autowired
//	MarkerCheckerRepository markerCheckerRepository;
//
	@Autowired
	AdminSettingService adminSetting;
//
//	@Autowired
//	GmsUrlsRepository gmsUrlsRepository;
//
//	@Autowired
//	ImageUrlRepository imageUrlRepository;
//
//	@Autowired
//	NotificationRepository notificationRepository;
//
	@Autowired
	MarketingService marketingService;

	@RequestMapping("/postSelectedUpdateMarketing")
	public KeyValue postSelectedUpdateSignature(@RequestBody KeyValue KeyValue, Model model) {

		System.out.println("KeyValue-----------------------" + KeyValue);

		UserApp user = authenticationManager.getUserCredentials();
		
		
		Checker checker=adminSetting.getChecker(""+2);
		
		System.out.println(checker.getId());
		boolean isPresent=false;
		try{		
			if(checker.getId().equals(null)){}
			isPresent=true;
			
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		

		if(isPresent) {
		if (checker.isActive()) {
			System.out.println("MARKER CHECKER iS ENABLED");
			if (checker.getChecker().getId().equals(user.getId())) {
				System.out.println("USER IS CHECKER");
				marketingService.marketingUpdatesAccounts(KeyValue);
			} else {
				System.out.println("" + KeyValue);
				System.out.println("USER IS NOT CHECKER");
				authenticationManager.executeWorkFlowCheckerNew(checker, "MARKETING_BRANDING_UPDATE", KeyValue);
			}
		} else {
			System.out.println("MARKER CHECKER iS DISABLED");
			marketingService.marketingUpdatesAccounts(KeyValue);
		}
		}else {
			System.out.println("MARKER CHECKER iS NOT PRESENT");
			marketingService.marketingUpdatesAccounts(KeyValue);
		}
		
		
		
//		MarkerChecker checker = markerCheckerRepository.findByIdCompany(5, user.getCompany().getCompanyId());
//
//		if (checker.isActive()) {
//			System.out.println("MARKER CHECKER iS ENABLED");
//			if (checker.getChekerUserId().getId().equals(user.getId())) {
//				System.out.println("USER IS CHECKER");
//		marketingService.marketingUpdatesAccounts(KeyValue);
//			} else {
//				System.out.println("" + KeyValue);
//				System.out.println("USER IS NOT CHECKER");
//				authenticationManager.executeWorkFlowChecker(checker, "", KeyValue);
//			}
//		} else {
//			System.out.println("MARKER CHECKER iS DISABLED");
//			marketingService.marketingUpdatesAccounts(KeyValue);
//		}

		return KeyValue;

	}

	@RequestMapping("/RemoveMarketingBrand/{templateId}")
	public String UpdateAllUsersSignature(@PathVariable("templateId") String templateId, Model model)
			throws GeneralSecurityException, IOException, URISyntaxException {
		
		KeyValue KeyValue=new KeyValue();
		KeyValue.setKey(templateId);
//		marketingService.marketingUpdatesAll(KeyValue);
		marketingService.marketingRemoveFromAccounts(KeyValue);
		return "Success";

	}

	@RequestMapping(value = "/createdMarketing", method = RequestMethod.POST)
	public Marketing getSubmitUser(@RequestBody Marketing marketing) {

		UserApp user = new UserApp();
		MarketingTemplate templaMarketing = new MarketingTemplate();
		templaMarketing.setCompany(authenticationManager.getUserCredentials().getCompany());
		templaMarketing.setCreatedBy(authenticationManager.getUserCredentials().getId());
		templaMarketing.setName(marketing.getMarketingName());
		templaMarketing.setMarketingBody(marketing.getMarketingTemplate());

		marketingRepo.saveAndFlush(templaMarketing);
		System.out.println("Signature-----------------------" + marketing.getMarketingTemplate());

		return marketing;
	}

	@RequestMapping("DeleteMarketing/{templateId}")
	public String deleteMarketing(@PathVariable("templateId") String templateId, Model model)
			throws GeneralSecurityException, IOException, URISyntaxException {
		marketingRepo.deleteById(Integer.parseInt(templateId));
		System.out.println("Id To Delete" + templateId);
		return "success";
	}


}
