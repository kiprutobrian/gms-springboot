package cloudit.africa.GMS.Controller.Drive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.gmail.Gmail;

import cloudit.africa.GMS.Controller.Drive.RestApi.DriveUtilities;
import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.DriveApiServices;
import cloudit.africa.GMS.Model.DataTest;
import cloudit.africa.GMS.Model.DriveYearly;
import cloudit.africa.GMS.Model.MyDriveFiles;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class DriveView {

	@Autowired
	UserAppRepositiry userappRepo;

	Drive driveServiceAccount;
	Gmail gmailAnlysisService;

	Directory serviceDirect;
	Gmail serviceGmail;
	List<User> users;

	@Autowired
	DirectoryService direcService;
	
	@Autowired
	DriveApiServices driveApiServices;


	@Autowired
	GMSAuthenticationManager authenticationManager;
	
	
	@Autowired
	GlobalModelView globalModelView;


	

	@RequestMapping("DriveAnalysis")
	public String AccountDeligation(Model model, final HttpServletRequest request)
			throws GeneralSecurityException, IOException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		
		List<User> orgUserList = direcService.getDomainUsers();
		model.addAttribute("users", orgUserList);
		
		return "gms-driveuserslist";
	}

	@RequestMapping("DriveStatisticalAnalysis/{userId}")
	public String driveAnalysisPage(@PathVariable("userId") String userId, Model model,
			HttpServletRequest request) throws GeneralSecurityException, IOException, URISyntaxException {

		
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		driveServiceAccount=ServiceAccount.getDriveService(userId);
		
		FileList result = driveServiceAccount.files().list().setFields("nextPageToken, files(createdTime,id,name,size,permissions) ").execute();
		List<File> file = result.getFiles();

		HashMap<String, List<DriveYearly>> hashMap = new HashMap<String, List<DriveYearly>>();
		List<DriveYearly> list = new ArrayList<DriveYearly>();
		for (int x = 0; x < file.size(); x++) {

			
			String year = Utilities.getYearOnly(file.get(x).getCreatedTime().getValue());
			if (!hashMap.containsKey(year)) {
				list.add(new DriveYearly(file.get(x).getName(), 1, file.get(x).getSize()));

				hashMap.put(year, list);
			} else {
				list.add(new DriveYearly(file.get(x).getName(), 1, file.get(x).getSize()));

			}
		}

		List<DriveYearly> finalData = new ArrayList<DriveYearly>();

		for (Entry<String, List<DriveYearly>> entry : hashMap.entrySet()) {
			String key = entry.getKey();
			List<DriveYearly> value = entry.getValue();

			Long total = 0L;
			for (int y = 0; y < value.size(); y++) {
				if (value.get(y).getTotalSize() != null) {
					total += value.get(y).getTotalSize();
				}
			}
			finalData.add(new DriveYearly(key, value.size(), total));

		}

		com.google.api.services.drive.model.About about = driveServiceAccount.about().get().setFields("*").execute();

		String usersDRIVE = String.valueOf(about.getStorageQuota().getUsageInDrive() / 1000000);
		String DriveTrash = String.valueOf(about.getStorageQuota().getUsageInDriveTrash() / 1000000);
		List<DataTest> data = new ArrayList<DataTest>();
		data.add(new DataTest("USAGE (MB)", Double.parseDouble(usersDRIVE)));
		data.add(new DataTest("DRIVE TRASH (MB)", Double.parseDouble(DriveTrash)));
		model.addAttribute("StorageQuota", data);
		model.addAttribute("yyy", finalData);
		
		return "gms-driveuserprofile";
	}

	@RequestMapping("DrivePermissions/{userId}")
	public String DrivePermissions(@PathVariable("userId") String userId, Model model, HttpServletRequest request)
			throws GeneralSecurityException, IOException, URISyntaxException {
		DriveUtilities diriveUtiles=new DriveUtilities();
		
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		
		User user = direcService.getDomainUser(userId);
		
		driveServiceAccount = ServiceAccount.getDriveService(user.getPrimaryEmail());		
		FileList driveFileList=driveApiServices.getDriveFiles(userId);
		System.out.println("START " + driveFileList.getFiles().get(0));

		
		List<MyDriveFiles> myDriveFilesList = new ArrayList<MyDriveFiles>();
		List<MyDriveFiles> mySharedDriveFilesList = new ArrayList<MyDriveFiles>();

		List<File> fileListData = driveFileList.getFiles();

			
		List<File> fileOwnedByMe=diriveUtiles.getFilesOwnedByMe(fileListData);
		List<File> fileSharedByMe=diriveUtiles.getFilesSharedByMe(fileListData);
		List<File> fileSharedWithMe=diriveUtiles.getFilesSharedWithMe(driveFileList);
		

		model.addAttribute("selectedUserId", userId);
		model.addAttribute("selectedUserEmail", user.getPrimaryEmail());		
		model.addAttribute("filesharedwithme", fileSharedWithMe);
		model.addAttribute("filesharedbyme", fileSharedByMe);
		model.addAttribute("sharedbymeSize", fileSharedByMe.size());
		model.addAttribute("sharedwithmeSize", fileSharedWithMe.size());
		model.addAttribute("mydrivefilessize", fileOwnedByMe.size());
		model.addAttribute("mydrivefiles", fileOwnedByMe);
		
		
		return "gms-driveuserprofile";
	}

	@RequestMapping("DriveAnalysisAll/{userId}")
	public String EntireOrganization(@PathVariable("userId") String userId, Model model)
			throws GeneralSecurityException, IOException, URISyntaxException {

		Optional<UserApp> user = userappRepo.findById(userId);
		String loginEmail = user.get().getEmail();
		Directory serviceDirect = ServiceAccount.getDirectoryServices("" + loginEmail);

		com.google.api.services.admin.directory.model.Users result = serviceDirect.users().list()
				.setCustomer("my_customer").setMaxResults(10).setOrderBy("email").execute();
		List<User> users = result.getUsers();

		HashMap<String, List<List<DriveYearly>>> hashMapAll = new HashMap<String, List<List<DriveYearly>>>();

		for (int i = 0; i < users.size(); i++) {
			driveServiceAccount = ServiceAccount.getDriveService(user.get().getEmail());
			FileList results = driveServiceAccount.files().list()
					.setFields("nextPageToken, files(createdTime,id,name,size) ").execute();
			List<File> file = results.getFiles();

			HashMap<String, List<DriveYearly>> hashMap = new HashMap<String, List<DriveYearly>>();
			List<DriveYearly> list = new ArrayList<DriveYearly>();
			for (int x = 0; x < file.size(); x++) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy");
				String year = df.format(file.get(x).getCreatedTime().getValue());
				if (!hashMap.containsKey(year)) {
					list.add(new DriveYearly(file.get(x).getName(), 1, file.get(x).getSize()));
					hashMap.put(year, list);
				} else {
					list.add(new DriveYearly(file.get(x).getName(), 1, file.get(x).getSize()));
				}
			}

			List<DriveYearly> das = hashMap.get("2019");
			List<DriveYearly> finalData = new ArrayList<DriveYearly>();

			for (Entry<String, List<DriveYearly>> entry : hashMap.entrySet()) {
				String key = entry.getKey();
				List<DriveYearly> value = entry.getValue();

				Long total = 0L;
				for (int y = 0; y < value.size(); y++) {
					if (value.get(y).getTotalSize() != null) {
						total += value.get(y).getTotalSize();
					}
				}
				finalData.add(new DriveYearly(key, value.size(), total));
			}

		}

		return "driveanalysis";
	}

	public String getPermisionId(List<Permission> permisions, String me) {

		try {

			for (int per = 0; per < permisions.size(); per++) {
				if (me.equals(Utilities.getEmptyNullStringValue(permisions.get(per).getEmailAddress()))) {
					return permisions.get(per).getId();
				}

			}
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		return null;
	}

}
