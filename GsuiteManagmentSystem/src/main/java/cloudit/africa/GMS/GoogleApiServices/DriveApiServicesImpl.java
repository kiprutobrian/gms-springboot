package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import cloudit.africa.GMS.Model.DrivePermision;
import cloudit.africa.GMS.Model.modeltest;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;

@Service
public class DriveApiServicesImpl implements DriveApiServices {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Drive gooogleDriveApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	public Drive getDrieService(String userEmail) {
		try {
			gooogleDriveApi = ServiceAccount.getDriveService(userEmail);
			return gooogleDriveApi;
		} catch (GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gooogleDriveApi;
	}

	@Override
	public FileList getFiles() {
		// TODO Auto-generated method stub
		FileList result = null;
		gooogleDriveApi = new Drive.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		try {
			result = gooogleDriveApi.files().list().setFields("nextPageToken, files(id, name)").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getStrorageQuota() {
		com.google.api.services.drive.model.About about = null;
		gooogleDriveApi = new Drive.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		try {
			about = gooogleDriveApi.about().get().setFields("*").execute();
			about.getStorageQuota();
			if (about.getStorageQuota().getLimit() == null) {
				return "UNLIMITED";
			} else {
				String driveStorage = String.valueOf(about.getStorageQuota().getLimit() / 1000000);
				return "" + driveStorage;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FileList getDriveFiles(String account) {
		FileList result = null;
		try {
			String nextPageToken = null;
			result = getDrieService(account).files().list().setFields(
					"nextPageToken, files(ownedByMe,owners,iconLink,createdTime,id,name,size,permissions,kind,mimeType,shared,parents,fileExtension)")
					.execute();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public FileList getDriveBackUpFolders(String account) {

		FileList result = null;

		try {
			result = getDrieService(account).files().list().setQ("mimeType='application/vnd.google-apps.folder'")
					.setFields(
							"nextPageToken, files(ownedByMe,owners,iconLink,createdTime,id,name,size,permissions,kind,mimeType,shared,parents,fileExtension)")
					.execute();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Permission insertPermission(DrivePermision drivePermision) {

		String accountOwner = drivePermision.getEmail();
		String fileId = drivePermision.getFileId();
		String newEmailAddress = drivePermision.getNewEmailAdress();
		String role = drivePermision.getRole();
		String type = drivePermision.getType();
		
		Drive service = getDrieService(accountOwner);
		
		Permission newPermission = new Permission();
		newPermission.setEmailAddress(newEmailAddress);
		newPermission.setType(type);
		newPermission.setRole(role);
		
		try {
			if(role.equals("owner")) {
				return service.permissions().create(fileId, newPermission).setTransferOwnership(true).execute();
			}else {
				return service.permissions().create(fileId, newPermission).execute();
			}
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
		return null;
	}

}
