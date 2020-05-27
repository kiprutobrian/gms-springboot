package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.mail.MessagingException;

import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.ListThreadsResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.oauth2.model.Userinfoplus;

public interface GmailApiServices {

	Userinfoplus getUserInfoPluse() throws IOException;

	List<String> getTotalMessages();

	Message sendMessage(String userId,String checkerEmailAdress, String emailContent) throws MessagingException, IOException;


	Message sendEmailMessage(String userId, String to, String numberOfLicences, String emailContent)
			throws MessagingException, IOException;

	ListMessagesResponse getMessagesByLable(String lable);

	void UploadEmailMessage() throws MessagingException, IOException, GeneralSecurityException, URISyntaxException;

	List<com.google.api.services.gmail.model.Thread> getThread(String account);

	List<Message> getThreadMessages(String userId, String threadId) throws IOException;
	
}
