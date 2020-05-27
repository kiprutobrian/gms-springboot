package cloudit.africa.GMS.Controller.DataMigration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.Directory.Users;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.drive.model.File;
import com.google.api.services.gmail.model.Label;

import cloudit.africa.GMS.Controller.Drive.RestApi.DriveUtilities;
import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.DriveApiServices;
import cloudit.africa.GMS.GoogleApiServices.GmailDataMigrationService;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class DataMigrationView {

	Directory directory;
	
	@Autowired
	GmailDataMigrationService  gmailService;
	
	@Autowired
	DirectoryService directoryService;
	
	@Autowired
	DriveApiServices driveApiServices; 
	
	@Autowired
	GMSAuthenticationManager authenticationManager;
	
	@Autowired
	GabSettingRepository gabSettingRepository;
	
	@Autowired
	GlobalModelView globalModelView;

	
	@RequestMapping("/DataMigration/{type}")
	public String getDLPReporting(Model model,HttpServletRequest request,@PathVariable("type") String migrationType) {
		
		
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		

		try {
			String backupemailAddress=gabSettingRepository.findById(4).get().getValue();
			List<User> usersList=directoryService.getDomainUsers();
			model.addAttribute("users", usersList);
			model.addAttribute("backupaccount", backupemailAddress);
			
			if(migrationType.equals("BackMailBox")) {
				model.addAttribute("backup", true);
			}else if(migrationType.equals("RestorMailBox")) {
				model.addAttribute("restor", true);
				List<Label> lablesList=gmailService.getRestorationLables(backupemailAddress);
				DriveUtilities driveUtilities=new DriveUtilities();	
				List<File> filesBackup=driveApiServices.getDriveBackUpFolders(backupemailAddress).getFiles();
				model.addAttribute("label", lablesList);
				model.addAttribute("backupfolders", driveUtilities.getBackUpFoldersOnly(filesBackup));
			}
		
			return "gms-datamigration";
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "gms-datamigration";
	}
}
