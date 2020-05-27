package cloudit.africa.GMS.GoogleApiServices;

import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import cloudit.africa.GMS.Model.DrivePermision;
import cloudit.africa.GMS.Model.modeltest;


public interface DriveApiServices  {

	FileList getFiles();

	String getStrorageQuota();

	FileList getDriveFiles(String account);

	FileList getDriveBackUpFolders(String account);

	Permission insertPermission(DrivePermision drivePermision);

}
