package cloudit.africa.GMS.Controller.Drive.RestApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import cloudit.africa.GMS.Model.MyDriveFiles;

public class DriveUtilities {
	
	
	
	
	public Permission getFileOwnerPermsion(List<Permission> permissions){
		Permission permision=new Permission();
		
		for(int a=0;a<permissions.size();a++) {
			if(permissions.get(a).getRole().equals("owner")){
				return permissions.get(a);
			}
		}
		return permision;	
	}

	public List<File> getFilesOwnedByMe(List<File> filesList) {
		List<File> updatedFilesList = new ArrayList<File>();
		for (int a = 0; a < filesList.size(); a++) {
			File file = filesList.get(a);
			if ((file.getOwnedByMe() == true)) {
				updatedFilesList.add(file);
			}
		}
		return filesList;
	}

	public List<File> getFilesSharedWithMe(FileList filesLists) {
		List<File> filesList = filesLists.getFiles();
		System.out.println("/n/n");

		List<File> updatedFilesList = new ArrayList<File>();
		for (int a = 0; a < filesList.size(); a++) {
			File file = filesList.get(a);
			System.out.println(file.getOwnedByMe());
			if (file.getOwnedByMe() == false) {
				updatedFilesList.add(file);
			}
		}
		return updatedFilesList;
	}

	public List<File> getFilesSharedByMe(List<File> filesList) {
		List<File> updatedFilesList = new ArrayList<File>();
		for (int a = 0; a < filesList.size(); a++) {
			File file = filesList.get(a);

			if ((file.getOwnedByMe() == true && file.getShared() == true)) {
				updatedFilesList.add(file);
			}
		}
		return updatedFilesList;
	}

	
	public List<File> getBackUpFoldersOnly(List<File> filesList) {
		List<File> updatedFilesList = new ArrayList<File>();
		for (int a = 0; a < filesList.size(); a++) {
			File file = filesList.get(a);
			String filename[]=file.getName().split("@");
			if (filename.length>1) {
				Date date=new Date(file.getCreatedTime().getValue());
				
				file.setName(file.getName()+"-"+date);
				updatedFilesList.add(file);
			}
		}
		return updatedFilesList;
	}
	
	
//	public List<MyDriveFiles> getFilesSharedWithMeMyDriveFiles(List<File> filesList) {
//		for (int a = 0; a < filesList.size(); a++) {
//			File file = filesList.get(a);
//			if (!(file.getOwnedByMe())) {
//				filesList.remove(file);
//			}
//		}
//		return filesList;
}
//}
