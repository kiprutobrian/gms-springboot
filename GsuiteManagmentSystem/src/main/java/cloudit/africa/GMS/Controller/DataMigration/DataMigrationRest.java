package cloudit.africa.GMS.Controller.DataMigration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.admin.datatransfer.model.ApplicationsListResponse;
import com.google.api.services.admin.datatransfer.model.DataTransfer;
import com.google.api.services.admin.directory.model.User;

import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.GoogleApiServices.DataTransferService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.GoogleApiServices.GmailDataMigrationService;
import cloudit.africa.GMS.Model.DataMigrationAccount;
import cloudit.africa.GMS.Model.DataRestore;
import cloudit.africa.GMS.Repository.GabSettingRepository;

@RestController
public class DataMigrationRest {

	@Autowired
	GmailDataMigrationService gmailservice;

	@Autowired
	GabSettingRepository gabSettingRepository;

	@Autowired
	DataTransferService dataTransferService;

	@Autowired
	DirectoryService directoryService;

	@RequestMapping("/GetApplications")
	public ApplicationsListResponse getvalue() {
		return dataTransferService.getApplications();
	}

	@RequestMapping("/GetApplicationTransfer/{applicationId}/{oldOwnerUserId}/{newOwnerUserId}")
	public DataTransfer getStartMigration(@PathVariable("applicationId") Long applicationId,
			@PathVariable("oldOwnerUserId") String oldOwnerUserId,
			@PathVariable("newOwnerUserId") String newOwnerUserId) {
		return dataTransferService.StartDataTransferMigration(applicationId, oldOwnerUserId, newOwnerUserId);
	}

	@RequestMapping("/GetApplicationTransfers")
	public List<DataTransfer> GetApplicationTransfers() {
		return dataTransferService.GetApplicationTransfers();
	}

	@RequestMapping("/BackupEmailData")
	public List<DataMigrationAccount> BackupEmailData(@RequestBody List<DataMigrationAccount> dataMigrationAccount) {
		System.out.println("BackupEmailData ACCOUNTS ");
		System.out.println("" + dataMigrationAccount);
		try {
			if (dataMigrationAccount.size() > 0) {
				DataMigrationAccount bck = dataMigrationAccount.get(0);
				if (bck.isEmail() == true && bck.isDrive() == true) {
					gmailservice.BackupEmailAccountData(dataMigrationAccount, bck.getBackupaccount());
					User backupAccountUser = directoryService.getDomainUser(bck.getBackupaccount());
					ApplicationsListResponse applications = dataTransferService.getApplications();
					Long applicationId = applications.getApplications().get(0).getId();
					for (int a = 0; a < dataMigrationAccount.size(); a++) {
						User user = directoryService.getDomainUser(dataMigrationAccount.get(a).getEmailAddress());
						dataTransferService.StartDataTransferMigration(applicationId, user.getId(),
								backupAccountUser.getId());
					}
				} else if (bck.isEmail() == true && bck.isDrive() == false) {
					gmailservice.BackupEmailAccountData(dataMigrationAccount, bck.getBackupaccount());
				} else if (bck.isEmail() == false && bck.isDrive() == true) {
					User backupAccountUser = directoryService.getDomainUser(bck.getBackupaccount());
					ApplicationsListResponse applications = dataTransferService.getApplications();
					Long applicationId = applications.getApplications().get(0).getId();
					for (int a = 0; a < dataMigrationAccount.size(); a++) {
						User user = directoryService.getDomainUser(dataMigrationAccount.get(a).getEmailAddress());
						dataTransferService.StartDataTransferMigration(applicationId, user.getId(),
								backupAccountUser.getId());
					}
				}
			}
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
		return dataMigrationAccount;
	}

	@RequestMapping("/RestoreEmailData")
	public List<DataRestore> RestoreEmailData(@RequestBody List<DataRestore> dataRestore) {
		System.out.println("RestoreEmailData ACCOUNTS ");
		System.out.println("" + dataRestore);
		try {
			GabSetting backupAccount = gabSettingRepository.findById(4).get();
			gmailservice.RestoreEmailAccountData(dataRestore, backupAccount.getValue());
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
		return dataRestore;
	}

}
