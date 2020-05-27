package cloudit.africa.GMS.Controller.AdminSetting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.iam.v1.model.CreateServiceAccountRequest;
import com.google.api.services.iam.v1.model.ServiceAccount;

import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Model.Ids;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@RestController
public class DomainSettingRest {

	@Autowired
	UserAppRepositiry userAppRepo;

	@Autowired
	GMSAuthenticationManager gmsAuthentication;

	@RequestMapping("/UserAppAccess")
	public List<Ids> getDomainSetting(@RequestBody List<Ids> user) {

		List<String> users = new ArrayList<String>();
		for (int a = 0; a < user.size(); a++) {
			users.add(user.get(a).getId());
		}
		List<UserApp> userAppList = userAppRepo.findAllById(users);
		for (int a = 0; a < userAppList.size(); a++) {
			UserApp userApp = userAppList.get(a);
			userApp.setActive(true);
			userAppRepo.saveAndFlush(userApp);
		}

		return user;
	}

	@RequestMapping("/RevockUserAppAccess")
	public List<Ids> RevockUserAppAccess(@RequestBody List<Ids> user) {

		List<String> users = new ArrayList<String>();
		for (int a = 0; a < user.size(); a++) {
			users.add(user.get(a).getId());
		}
		List<UserApp> userAppList = userAppRepo.findAllById(users);
		for (int a = 0; a < userAppList.size(); a++) {
			UserApp userApp = userAppList.get(a);
			userApp.setActive(false);
			userAppRepo.saveAndFlush(userApp);
		}
		return user;
	}

//	public ServiceAccount createServiceAccount(String projectId, String name, String displayName)
//		    throws IOException {
//
//		  ServiceAccount serviceAccount = new ServiceAccount();
//		  serviceAccount.setDisplayName(displayName);
//		  CreateServiceAccountRequest request = new CreateServiceAccountRequest();
//		  request.setAccountId(name);
//		  request.setServiceAccount(serviceAccount);
//
//		  serviceAccount =service.projects().serviceAccounts().create("projects/" + projectId, request).execute();
//
//		  System.out.println("Created service account: " + serviceAccount.getEmail());
//		  return serviceAccount;
//		}
	

}
