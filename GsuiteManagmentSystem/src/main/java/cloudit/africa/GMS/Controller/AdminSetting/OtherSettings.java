package cloudit.africa.GMS.Controller.AdminSetting;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class OtherSettings {
	
	@Autowired
	GabSettingRepository gabSettingRepository;
	
	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GlobalModelView globalModelView;
	

	@RequestMapping("/Settings")
	public String stettingPage(Model model, HttpServletRequest request) {
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
	
		model.addAttribute("settings", gabSettingRepository.findAll());
		
		
		return "gab_settings";
	}
}
