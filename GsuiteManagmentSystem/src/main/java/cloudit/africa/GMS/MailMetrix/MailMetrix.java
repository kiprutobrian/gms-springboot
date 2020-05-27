package cloudit.africa.GMS.MailMetrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.gmail.model.Message;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.MailMetrixService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class MailMetrix {

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	MailMetrixService mailMetrixService;
	
	@Autowired
	DirectoryService directoryServies;
	
	@Autowired
	GlobalModelView globalModelView;
	

	@RequestMapping("/MailMetrix")
	public String getMailMaatrixView(HttpServletRequest request, Model model) {
		
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
	
		
		List<com.google.api.services.admin.directory.model.User> myOrgUsers=directoryServies.getDomainUsers();
		
		
		model.addAttribute("users", myOrgUsers);
		
		return "gms-mailmatrix";
	}
	
	
	@RequestMapping("/MetrixAccount/{userId}")
	public String getMetrixAccountView(HttpServletRequest request, Model model,String userId) {
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		
		
	
		return "gms-mailmatrix-accounts";
	}
	
	

}
