package cloudit.africa.GMS.Controller.Drive.RestApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.MyDriveFiles;

import cloudit.africa.GMS.Model.modeltest;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController
public class DrivePermisionRest {

	@Autowired
	UserAppRepositiry userappRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;



	Drive driveServiceAccount;
	Directory directoryServiceAccount;

	@Autowired
	DirectoryService direcService;

	@RequestMapping("globalDriveAnalysis")
	public modeltest getRevockAcess() throws GeneralSecurityException, IOException, URISyntaxException {
		String emailAuth = authenticationManager.getUserCredentials().getEmail();
		String getDomain[] = emailAuth.split("@");
		String myDomain = getDomain[1];

		directoryServiceAccount = ServiceAccount.getDirectoryServices(emailAuth);
		Users accounts = directoryServiceAccount.users().list().setCustomer("my_customer").setOrderBy("email")
				.execute();

		int sharedByMeNumber = 0;
		int sharedOuside = 0;

		List<User> users = accounts.getUsers();
		List<HashMap<String, List<MyDriveFiles>>> xcx = new ArrayList<HashMap<String, List<MyDriveFiles>>>();

		for (int a = 0; a < users.size(); a++) {
			String email = users.get(a).getPrimaryEmail();
			driveServiceAccount = ServiceAccount.getDriveService(email);

			List<MyDriveFiles> mySharedDriveFilesList = new ArrayList<MyDriveFiles>();
			FileList sharedByMe = driveServiceAccount.files().list().setFields(
					"nextPageToken, files(ownedByMe,iconLink,createdTime,id,name,size,permissions,kind,mimeType,shared,parents)")
					.execute();

			List<File> fileShredByMe = sharedByMe.getFiles();
			for (int b = 0; b < fileShredByMe.size(); b++) {
				File sharedFilesByMe = fileShredByMe.get(b);
				if (sharedFilesByMe.getShared() && sharedFilesByMe.getOwnedByMe()) {

					mySharedDriveFilesList.add(new MyDriveFiles(sharedFilesByMe.getId(), sharedFilesByMe.getName(),
							sharedFilesByMe.getMimeType(), sharedFilesByMe.getIconLink(),
							"" + sharedFilesByMe.getSize(), "" + sharedFilesByMe.getCreatedTime(),""));
					sharedByMeNumber += 1;

					for (int permisionList = 0; permisionList < sharedFilesByMe.getPermissions()
							.size(); permisionList++) {
						String permisionedEmail[] = Utilities
								.getEmptyNullStringValue(
										sharedFilesByMe.getPermissions().get(permisionList).getEmailAddress())
								.split("@");
						if (permisionedEmail.length > 1) {
							if (!((permisionedEmail[1]).equals(myDomain))) {
								sharedOuside += 1;
							}
						}
					}
				}
			}
		}

		System.out.println("ListOf SharedFiles " + sharedOuside);
		System.out.println("files shared Outside" + sharedOuside);
		System.out.println("files shared by organisation" + sharedByMeNumber);

		return null;
	}

	
	
	
	
	
}
