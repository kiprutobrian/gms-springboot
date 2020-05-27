package cloudit.africa.GMS.Controller.AdminSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class DomainSetting {

	@Autowired
	UserAppRepositiry userAppRepo;
	
	@Autowired
	GMSAuthenticationManager authenticationManager;
	
	@Autowired
	GlobalModelView globalModelView;
	
	@RequestMapping("/GMSSetting")
	public String getDomainSetting(Model model,HttpServletRequest request) {
		
		
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
	
		List<UserApp> gmsusersAcessList= userAppRepo.findByCompany(authenticatedUser.getCompany());
		
		
		model.addAttribute("users", gmsusersAcessList);
		return "gms-portalaccounts";
	}
}
