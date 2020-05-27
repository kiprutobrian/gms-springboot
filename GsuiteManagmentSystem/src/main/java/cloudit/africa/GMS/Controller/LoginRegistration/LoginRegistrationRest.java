package cloudit.africa.GMS.Controller.LoginRegistration;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.admin.reports.model.Activity;
import com.google.api.services.oauth2.model.Userinfoplus;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.AdminReportingService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GmailDataMigrationService;
import cloudit.africa.GMS.GoogleApiServices.GoogleOuthAPI;
import cloudit.africa.GMS.Model.GabEmailReport;
import cloudit.africa.GMS.Repository.CompanyRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;

@RestController
public class LoginRegistrationRest {

	@Autowired
	AdminReportingService adminReportingService;

	@Autowired
	GmailDataMigrationService gmailDataMigrationService;

	@Autowired
	DirectoryService direct;

	@Autowired
	UserAppRepositiry userRepo;

	@Autowired
	GoogleOuthAPI googleOuthAPI;

	@Autowired
	CompanyRepository companyRepository;

	@RequestMapping("/user")
	ResponseEntity user(HttpServletResponse response) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/DashBoard"));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping("/userAuthenticated")
	ResponseEntity userAuthenticated(HttpServletResponse response) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		Userinfoplus userinfo = googleOuthAPI.getUserInfoPluse();
		Optional<UserApp> authenticatedUser = userRepo.findById(userinfo.getId());

		System.out.println(userinfo.getEmail().split("@")[0] + "    -------------------------       "
				+ ((userinfo.getEmail().split("@"))[1]));
		Optional<Company> company = companyRepository.findByDomain(((userinfo.getEmail().split("@"))[1]));

		String redirectTo = "";

		if (authenticatedUser.isPresent()) {
			if (authenticatedUser.get().isActive()) {
				UserApp userApp=authenticatedUser.get();
				userApp.setImageUrl(userinfo.getPicture());
				userApp.setFirstName(userinfo.getFamilyName());
				userApp.setLastName(userinfo.getGivenName());
				userApp.setUsername(userinfo.getFamilyName() +" "+userinfo.getGivenName());
				userRepo.save(userApp);
				redirectTo = "/DashBoard";
			} else {
				redirectTo = "page-401";
			}
		} else if (!company.isPresent()) {
			redirectTo = "https://docs.google.com/forms/d/e/1FAIpQLSdpmtniApUWvqo8GHc9tpzY1InST5fnrw3RdX7viGpcvViXKw/viewform";
		} else if (company.isPresent() && !company.get().isUnderSupport()) {
			redirectTo = "https://docs.google.com/forms/d/e/1FAIpQLSdpmtniApUWvqo8GHc9tpzY1InST5fnrw3RdX7viGpcvViXKw/viewform";
		} else if (company.isPresent() && company.get().isUnderSupport() ) {
			redirectTo = "https://docs.google.com/forms/d/e/1FAIpQLSdpmtniApUWvqo8GHc9tpzY1InST5fnrw3RdX7viGpcvViXKw/viewform";
			boolean isSuperAdmin = direct.getDomainUserRole(userinfo.getId());
			if (isSuperAdmin) {
				String id = userinfo.getEmail();
				direct.updateCustomerInfo(id);
				redirectTo = "redirect:/Registration/" + id;
			} else {
				try {
					redirectTo = "redirect:/AccessDenied";
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
				}
				redirectTo = "redirect:/AccessDenied";
			}
		}
		headers.setLocation(URI.create(redirectTo));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping("/CheckMail")
	public List<GabEmailReport> getGmailDataMigrationService() {
		return gmailDataMigrationService.CheckEmailSendOutSideDomain();
	}

	@RequestMapping("/test")
	public List<Activity> handleFoo() {
		return adminReportingService.getDriveReportingAcrivity();
	}

	@RequestMapping(value = "/logouts", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		try {
			request.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("--------" + request.getHeaderNames());
		auth.setAuthenticated(false);
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";// You can redirect wherever you want, but generally it's a good practice to
		// show login screen again.
	}

}
