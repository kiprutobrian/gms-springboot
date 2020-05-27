package cloudit.africa.GMS.Controller.Drive.RestApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.DriveWorkFlow;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GMSApiServices.DriveService;
import cloudit.africa.GMS.GoogleApiServices.DriveApiServices;
import cloudit.africa.GMS.Model.DrivePermision;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.modeltest;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.ServiceResponse;

@RestController
public class DriveRestAPI {

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	UserAppRepositiry userappRepo;

	Drive driveServiceAccount;

	@Autowired
	DriveService driveService;

	
	@Autowired
	AdminSettingService adminSetting;
	
	@Autowired
	DriveApiServices driveApiServices;

	
	
	@RequestMapping("/GetFileData/{userId}")
	public FileList getfileData(@PathVariable("userId") String userId){
		
		return driveApiServices.getDriveFiles(userId);
	}
	
	@RequestMapping("getfiledetails/{ownerEmail}/{fileId}")
	private File getdate(@PathVariable("fileId") String fileId, @PathVariable("ownerEmail") String ownerEmail,
			Model model) {

		try {
			driveServiceAccount = ServiceAccount.getDriveService(ownerEmail);
			File file = driveServiceAccount.files().get(fileId).setFields("*").execute();
			model.addAttribute("permissionList", file.getPermissions());
			return file;

		} catch (GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("FILE ID---------------------- " + fileId + " ==================== " + ownerEmail);
		return null;
	}

	@PostMapping("RevockFileShareWithMe")
	private DriveWorkFlow postAllData(@RequestBody DriveWorkFlow driveWorkFlow, Model model) {	
		UserApp user = authenticationManager.getUserCredentials();
		Checker checker=adminSetting.getChecker(""+5);
		
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
				driveService.revockAcessesGeneral(driveWorkFlow);
			} else {
				System.out.println("" + driveWorkFlow);
				System.out.println("USER IS NOT CHECKER");
				authenticationManager.executeWorkFlowCheckerDrive(checker, "REVOKE_DRIVEACCESS", driveWorkFlow);
			}
		} else {
			System.out.println("MARKER CHECKER iS DISABLED");
			driveService.revockAcessesGeneral(driveWorkFlow);
		}
		}else {
			System.out.println("MARKER CHECKER iS NOT PRESENT");
			boolean update = driveService.revockAcessesGeneral(driveWorkFlow);
		}
		
		ServiceResponse response = new ServiceResponse();
		response.setStatus("success");
		response.setData(driveWorkFlow);

		System.out.print("response : " + driveWorkFlow);
		return driveWorkFlow;

	}

	@RequestMapping("postRevockAccess")
	public modeltest getRevockAcess(@RequestBody modeltest value) {
		try {
			System.out.println("User Email Adress----------------" + value.getEmail());
			driveServiceAccount = ServiceAccount.getDriveService(value.getEmail());
			driveServiceAccount.permissions().delete(value.getFileId(), value.getId()).execute();

		} catch (GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}
	
	
	@RequestMapping("postChangePermisionOwnerShip")
	public DrivePermision postChangePermisionOwnerShip(@RequestBody DrivePermision drivePermision) {
		Permission permision=driveApiServices.insertPermission(drivePermision);
		return drivePermision;
	}

}
