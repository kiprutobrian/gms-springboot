package cloudit.africa.GMS.GoogleApiServices;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.oauth2.model.Userinfoplus;

import cloudit.africa.GMS.Model.*;


public interface GmailDataMigrationService {

	
	List<String> getTotalMessages();

	void BackupEmailAccountData(List<DataMigrationAccount> dataMigrationAccount, String toAcccount)
			throws  IOException, GeneralSecurityException, URISyntaxException;

	List<GabEmailReport> CheckEmailSendOutSideDomain();

	List<GabEmailReport> MySearchCheckEmailSendOutSideDomain(String string, Date from, Date to);

	Message sendEmailReportWithAttachment(String to, String from, String subject, String bodyText, File file)
			throws MessagingException, IOException;

	String getfilepth(String account, String messageId);

	List<Label> getRestorationLables(String backupaccount)
			throws IOException, GeneralSecurityException, URISyntaxException;

	void RestoreEmailAccountData(List<DataRestore> dataRestoreAcount, String toAcccount)
			throws IOException, GeneralSecurityException, URISyntaxException;
	
}
