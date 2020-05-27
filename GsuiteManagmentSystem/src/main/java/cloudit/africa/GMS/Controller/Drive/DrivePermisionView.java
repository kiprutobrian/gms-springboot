package cloudit.africa.GMS.Controller.Drive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import cloudit.africa.GMS.Controller.Drive.RestApi.DriveUtilities;
import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.OrgDriveFiles;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class DrivePermisionView {

	@Autowired
	UserAppRepositiry userappRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GlobalModelView globalModelView;

	Drive driveServiceAccount;

	@Autowired
	DirectoryService directoryService;

	@RequestMapping("getCurrentUserPermission/{ownerEmail}/{fileId}")
	private String getCurrentUserPermission(@PathVariable("fileId") String fileId,
			@PathVariable("ownerEmail") String ownerEmail, Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		List<User> userList = directoryService.getDomainUsers();

		try {
			driveServiceAccount = ServiceAccount.getDriveService(ownerEmail);

			String fileQuery = "'" + fileId + "' in parents and trashed=false";
			File fileSelect = driveServiceAccount.files().get(fileId)
					.setFields("ownedByMe,iconLink,createdTime,id,name,size,permissions,kind,mimeType,shared,parents")
					.execute();

			if (fileSelect.getMimeType().equals("application/vnd.google-apps.folder")) {
				FileList childrenFiles = driveServiceAccount.files().list().setQ(fileQuery).execute();
				model.addAttribute("folderFiles", childrenFiles.getFiles());
				model.addAttribute("isFolder", true);

			} else {
				model.addAttribute("isFolder", false);
			}

			DriveUtilities utilities = new DriveUtilities();
			Permission ownerPermisison = utilities.getFileOwnerPermsion(fileSelect.getPermissions());
			model.addAttribute("ownerpermission", ownerPermisison);

			model.addAttribute("permissionList", fileSelect.getPermissions());
			model.addAttribute("fileName", fileSelect.getName());
			model.addAttribute("fileType", fileSelect.getMimeType());
			model.addAttribute("iconeLink", fileSelect.getIconLink());
			model.addAttribute("accountName", ownerEmail);
			model.addAttribute("userList", userList);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("selectedUserId", ownerEmail);
		model.addAttribute("fileId", fileId);
		model.addAttribute("userId", "" + authenticatedUser.getId());

		return "gms-driveuserfilesprofile";
	}

	@RequestMapping("AllsharedFilesByOrg")
	public String EntireOrganizationFiles(Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		try {

			List<com.google.api.services.admin.directory.model.User> myOrgUsers = directoryService.getDomainUsers();
			List<OrgDriveFiles> filesSharedbyOrg = new ArrayList<OrgDriveFiles>();

			for (int a = 0; a < myOrgUsers.size(); a++) {
				String email = myOrgUsers.get(a).getPrimaryEmail();
				String[] mydomain = email.split("@");
				driveServiceAccount = ServiceAccount.getDriveService(email);

				FileList resultoutside = driveServiceAccount.files().list().setFields(
						"nextPageToken, files(ownedByMe,iconLink,createdTime,id,name,size,permissions,kind,mimeType,shared,parents)")
						.execute();

				List<File> filesList = resultoutside.getFiles();
				for (int y = 0; y < filesList.size(); y++) {
					File file = filesList.get(y);
					if (file.getOwnedByMe() && file.getShared()) {
						OrgDriveFiles orgDriveFiles = new OrgDriveFiles();
						orgDriveFiles.setId(file.getId());
						orgDriveFiles.setFileName(file.getName());
						orgDriveFiles.setFileIcone(file.getIconLink());
						orgDriveFiles.setFileSize("" + file.getSize());
						orgDriveFiles.setFileType(file.getMimeType());
						orgDriveFiles.setOwner(email);
						orgDriveFiles.setCreatedDate("" + file.getCreatedTime());

						System.out.println("	PERMISION SIZE " + file.getPermissions().size());
						int sharedwith = 0;
						for (int per = 0; per < file.getPermissions().size(); per++) {
							Permission permision = file.getPermissions().get(per);

							String[] domain = Utilities.getEmptyNullStringValue(permision.getEmailAddress()).split("@");
							System.out.println("FILE NAME " + file.getName() + " EMAIL PERMISSION  "
									+ permision.getEmailAddress() + " ROLE " + permision.getRole());
							System.out.println("DOMAIN lenghth" + domain.length);
							System.out.println("DOMAIN 1" + domain[0]);

							if (domain.length == 2)
								if (!(domain[1].equals(mydomain[1]))) {
									sharedwith += 1;
									orgDriveFiles.setSharedOutside(true);
								}
						}
						orgDriveFiles.setSharedWithEmailAdress("" + sharedwith);
						filesSharedbyOrg.add(orgDriveFiles);
					}
				}

			}
			model.addAttribute("orgDrive", filesSharedbyOrg);

			return "gms-driveorgfilesfolderssharedoutside";

		} catch (GeneralSecurityException | IOException |

				URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping("FileOrgPermission/{ownerEmail}/{fileId}")
	public String getFilePermisionsSharedOutside(@PathVariable("fileId") String fileId,
			@PathVariable("ownerEmail") String ownerEmail, Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		try {
			driveServiceAccount = ServiceAccount.getDriveService(ownerEmail);

			String fileQuery = "'" + fileId + "' in parents and trashed=false";
			File fileSelect = driveServiceAccount.files().get(fileId).setFields("ownedByMe,iconLink,createdTime,id,name,size,permissions,kind,mimeType,shared,parents").execute();

			if (fileSelect.getMimeType().equals("application/vnd.google-apps.folder")) {
				FileList childrenFiles = driveServiceAccount.files().list().setQ(fileQuery).execute();

				model.addAttribute("permissionList", fileSelect.getPermissions());
				model.addAttribute("folderFiles", childrenFiles.getFiles());
				model.addAttribute("isFolder", true);

			} else {
				model.addAttribute("permissionList", fileSelect.getPermissions());
				model.addAttribute("isFolder", false);
			}

			model.addAttribute("fileName", fileSelect.getName());
			model.addAttribute("fileType", fileSelect.getMimeType());
			model.addAttribute("iconeLink", fileSelect.getIconLink());
			model.addAttribute("accountName", ownerEmail);

			System.out.println("=========================================" + fileSelect.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("selectedUserId", ownerEmail);
		model.addAttribute("fileId", fileId);

		
		return "gms-driveorgsharedoutside";

	}

}
