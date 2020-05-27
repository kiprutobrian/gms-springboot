package cloudit.africa.GMS.Controller.SignatureMarketing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.util.ArrayMap;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.MarketingTemplate;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.GeneralDelegation;
import cloudit.africa.GMS.Model.Signature;
import cloudit.africa.GMS.Repository.MarketingRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class MarketingController {

	@Autowired
	UserAppRepositiry usrRepo;

	@Autowired
	MarketingRepository marketingRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GmailApiServices gmailApiServices;

	@Autowired
	DirectoryService directoryService;
	
	@Autowired
	GlobalModelView globalModelView;



	@RequestMapping("MarketingSetting")
	public String AccountDeligation(Model model, HttpServletRequest request)
			throws GeneralSecurityException, IOException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);


		List<com.google.api.services.admin.directory.model.User> myOrgUsers = directoryService.getDomainUsers();
		for (int x = 0; x < myOrgUsers.size(); x++) {
			try {
				List<Object> org = (List<Object>) myOrgUsers.get(x).getOrganizations();
				if (org != null) {
					for (int a = 0; a < org.size(); a++) {
						ArrayMap<String, String> map = (ArrayMap<String, String>) org.get(a);					}
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		List<MarketingTemplate> marketingTemplatesList = marketingRepo.findByCompany(authenticationManager.getUserCredentials().getCompany());
		GeneralDelegation generalDelegation = new GeneralDelegation();
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("users", myOrgUsers);
		model.addAttribute("GeneralDelegation", generalDelegation);
		model.addAttribute("mysignature", marketingTemplatesList);
		return "gms-marketinguserlist";
	}

	@RequestMapping("/createMarketingTemplate")
	public String getSignatureLayout(Model model,HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);
		
		List<MarketingTemplate> marketingTemplatesList = marketingRepo.findByCompany(authenticatedUser.getCompany());
		Collections.reverse(marketingTemplatesList);
		model.addAttribute("myMarketing", marketingTemplatesList);
		model.addAttribute("Marketing", new Signature());
		model.addAttribute("buttontype", "submit");
				return "gms-marketingbrandingcreate";
	}
	@RequestMapping("/MarketingGallary")
	public String getMarketingGallary(Model model,HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);
		
		List<MarketingTemplate> marketingTemplatesList = marketingRepo.findByCompany(authenticatedUser.getCompany());
		Collections.reverse(marketingTemplatesList);
		model.addAttribute("myMarketing", marketingTemplatesList);
		model.addAttribute("Marketing", new Signature());
		model.addAttribute("buttontype", "submit");

		return "gms_template_marketing";
	}
}
