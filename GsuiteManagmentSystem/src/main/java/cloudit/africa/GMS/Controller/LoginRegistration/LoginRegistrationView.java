
package cloudit.africa.GMS.Controller.LoginRegistration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.services.admin.directory.model.User;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;
import cloudit.africa.GMS.Utilities.Config.Pages;

@Controller
public class LoginRegistrationView {

	@Autowired
	GMSAuthenticationManager authenticationManager;
	
	@Autowired
	GlobalModelView globalModelView;

	
	@Autowired
	DirectoryService direct;


	@RequestMapping(value = "/Auth")
	public String test() {
		return "login";
	}

	@RequestMapping(value = "/DashBoard")
	public String user(Model model, HttpServletRequest request) throws IOException {
		UserApp authenticatedUser = authenticationManager.getUserCredentials();if(authenticatedUser == null) return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model,authenticatedUser,request);
		return Pages.DASHBOARD;
	}

	@RequestMapping("/Registration/{userId}")
	public String regestration(Model model, @PathVariable("userId") String userId, HttpServletRequest request) {
		User user = direct.getDomainUserFirst(userId);
		System.out.println(user);
		globalModelView.getRegistrationViewStaticData(model,user,request);
		return Pages.REGISTARTION_PAGE;
	}

	@RequestMapping(value = "/AccessDenied", method = RequestMethod.GET)
	public String AccessDenied(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			request.logout();
			return "page-401";
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			System.out.println("LogOutFailed");
			e.printStackTrace();
		}
		return null;// You can redirect wherever you want, but generally it's a good practice to
		// show login screen again.
	}

}
