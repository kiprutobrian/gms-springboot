package cloudit.africa.GMS.Controller.AdminSetting;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.cloudresourcemanager.model.Operation;
import com.google.api.services.iam.v1.model.ServiceAccount;

import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GoogleApiServices.CloudResourceManagerService;
import cloudit.africa.GMS.GoogleApiServices.IamServiceService;
import cloudit.africa.GMS.Model.Appointments;
import cloudit.africa.GMS.Model.Checkers;
import cloudit.africa.GMS.Model.RoleAccess;
import cloudit.africa.GMS.Model.SettingUpdate;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;

@RestController
public class AdminSettingRest {

	@Autowired
	AdminSettingService adminSettingService;

	@Autowired
	CloudResourceManagerService cloudResourceManagerService;

	@Autowired
	IamServiceService iamServiceService;

	@Autowired
	UserAppRepositiry userAppRepo;
	
	@Autowired
	GabSettingRepository gabSettingRepository; 

	@RequestMapping("/AddRoleAccess")
	public RoleAccess AddRoleAccess(@RequestBody RoleAccess roleAccess) {
		adminSettingService.addRoleAccess(roleAccess);
		return roleAccess;

	}
	
	@RequestMapping("/PostUpdateSettingsOptions")
	public SettingUpdate PostUpdateSettingsOptions(@RequestBody SettingUpdate updateSetting) {
		
		GabSetting setting=gabSettingRepository.findById(updateSetting.getId()).get();
		setting.setValue(updateSetting.getValue());
		setting.setActive(updateSetting.isActive());
		setting.setUpdatedOn(new Date());
		setting.setUpdatedBy("admin");
		gabSettingRepository.save(setting);
		return updateSetting;

	}
	
	

	@RequestMapping("/AddMarkerChecker")
	public Checkers AddMarkerChecker(@RequestBody Checkers Checkers) {
		adminSettingService.AddMarkerChecker(Checkers);
		return Checkers;
	}

	@RequestMapping("/makerChekerState")
	public Checkers makerChekerState(@RequestBody Checkers Checkers) {
		adminSettingService.updateActiveState(Checkers);
		return Checkers;

	}

	@RequestMapping("/CreateGCPProject")
	public ServiceAccount CreateProject(@RequestBody Checkers Checkers) {
//		Operation repo=cloudResourceManagerService.CreateCloudResourceManagerProject();
		ServiceAccount service = iamServiceService.CreateserviceAccounts();

		return service;

	}

	@PostMapping("/SuspendGMSAccount")
	public List<Appointments> SuspendGMSAccount(@RequestBody List<Appointments> userAppList) {

		System.out.println("USERAPP BROUGHT : : " + userAppList);
		for (int acc = 0; acc < userAppList.size(); acc++) {
			Optional<UserApp> userApp = userAppRepo.findById(userAppList.get(acc).getId());
			if (userApp.isPresent()) {
				UserApp updateUserApp = userApp.get();
				updateUserApp.setActive(userAppList.get(acc).getAppointmentSlot());
				userAppRepo.saveAndFlush(updateUserApp);
			}
		}
		return userAppList;
	}
	
	
	@PostMapping("/UnsuspendGMSAccount")
	public List<Appointments> UnsuspendGMSAccount(@RequestBody List<Appointments> userAppList) {

		System.out.println("USERAPP BROUGHT : : " + userAppList);
		for (int acc = 0; acc < userAppList.size(); acc++) {
			Optional<UserApp> userApp = userAppRepo.findById(userAppList.get(acc).getId());
			if (userApp.isPresent()) {
				UserApp updateUserApp = userApp.get();
				updateUserApp.setActive(userAppList.get(acc).getAppointmentSlot());
				userAppRepo.saveAndFlush(updateUserApp);
			}
		}
		return userAppList;
	}
	
	@PostMapping("/DeleteGMSAccount")
	public List<Appointments> DeleteGMSAccount(@RequestBody List<Appointments> userAppList) {

		System.out.println("USERAPP BROUGHT : : " + userAppList);
		for (int acc = 0; acc < userAppList.size(); acc++) {
			Optional<UserApp> userApp = userAppRepo.findById(userAppList.get(acc).getId());
			if (userApp.isPresent()) {
				UserApp updateUserApp = userApp.get();
				updateUserApp.setActive(userAppList.get(acc).getAppointmentSlot());
				userAppRepo.saveAndFlush(updateUserApp);
			}
		}
		return userAppList;
	}
	
	

}
