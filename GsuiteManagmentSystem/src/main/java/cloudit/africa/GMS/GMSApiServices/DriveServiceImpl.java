package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Permissions.Delete;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import cloudit.africa.GMS.Entity.DriveWorkFlow;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class DriveServiceImpl implements DriveService {

	@Override
	public boolean revockAcessesGeneral(DriveWorkFlow data) {
		// TODO Auto-generated method stub

		if (data.isOwner()) {
			filesSharedByMeRevockAccess(data);
		} else {
			filesSharedWithMeRevockAccess(data);

		}

		return false;
	}

	public void filesSharedWithMeRevockAccess(DriveWorkFlow data) {
		Drive driveServiceAccount;
		try {
			String accountEmailAddess = data.getKey();

			driveServiceAccount = ServiceAccount.getDriveService(accountEmailAddess);

			for (int x = 0; x < data.getGmsFiles().size(); x++) {

				System.out.println("DATA FILES" + data.getGmsFiles());
				File file = driveServiceAccount.files().get(data.getGmsFiles().get(x).getFileId())
						.setFields("iconLink,createdTime,id,name,size,permissions,kind,mimeType,parents").execute();

				String myPermisionId = data.getGmsFiles().get(x).getPermisionId();
				for (int per = 0; per < file.getPermissions().size(); per++) {
					Permission permission = file.getPermissions().get(per);
					if (permission.getRole().equals("owner")) {
						Drive permissionServiceAccount = ServiceAccount.getDriveService(permission.getEmailAddress());
						permissionServiceAccount.permissions().delete(file.getId(), myPermisionId).execute();
					}
				}
			}
			System.out.println("=======================================================");

		} catch (NullPointerException | GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void filesSharedByMeRevockAccess(DriveWorkFlow data) {
		Drive driveServiceAccount;
		try {
			String accountEmailAddess = data.getKey();

			driveServiceAccount = ServiceAccount.getDriveService(accountEmailAddess);

			for (int x = 0; x < data.getGmsFiles().size(); x++) {

				System.out.println("DATA FILES" + data.getGmsFiles());
				File file = driveServiceAccount.files().get(data.getGmsFiles().get(x).getFileId())
						.setFields("iconLink,createdTime,id,name,size,permissions,kind,mimeType,parents").execute();

				for (int per = 0; per < file.getPermissions().size(); per++) {

					Permission permission = file.getPermissions().get(per);
					if (!(permission.getRole().equals("owner") && Utilities.getEmptyNullStringValue(permission.getEmailAddress()).equals(accountEmailAddess))) {
						driveServiceAccount.permissions().delete(file.getId(), permission.getId()).execute();
					}

				}
			}
			System.out.println("=======================================================");

		} catch (NullPointerException | GeneralSecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
