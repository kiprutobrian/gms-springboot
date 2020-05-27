package cloudit.africa.GMS.GoogleApiServices;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.LabelColor;
import com.google.api.services.gmail.model.ListHistoryResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.ListThreadsResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.google.api.services.gmail.model.Thread;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import cloudit.africa.GMS.Repository.GMSEmailAdressesRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;

@Service
public class GmailApiServicesImpl implements GmailApiServices {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Gmail gooogleGmailApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Autowired
	GMSEmailAdressesRepository gmsEmailAdressesRepository;

	public Gmail getMessages() {
		gooogleGmailApi = new Gmail.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return gooogleGmailApi;
	}

	@Override
	public ListMessagesResponse getMessagesByLable(String lable) {
		ListMessagesResponse messagesWithLabels = null;
		try {
			messagesWithLabels = getMessages().users().messages().list("me").setQ("label:SENT").execute();
			List<Message> messagesList = messagesWithLabels.getMessages();
			for (int a = 0; a < messagesList.size(); a++) {
				Message msg = getMessages().users().messages().get("me", messagesList.get(a).getId()).execute();
			}

			return messagesWithLabels;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Userinfoplus getUserInfoPluse() throws IOException {
		Userinfoplus userinfo = null;
		Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(),
				myAuthenticationManager.getGoogleCredential()).setApplicationName("Oauth2").build();
		userinfo = oauth2.userinfo().get().execute();
		

		return userinfo;
	}

	@Override
	public List<com.google.api.services.gmail.model.Thread> getThread(String account) {
		Gmail mailService;
		try {
			mailService = ServiceAccount.getGmailService(account);
			ListThreadsResponse response = mailService.users().threads().list(account).setQ("label:SENT").execute();
			List<com.google.api.services.gmail.model.Thread> threads = new ArrayList<com.google.api.services.gmail.model.Thread>();
			while (response.getThreads() != null) {
//			    	threads.addAll(response.getThreads());
				List<com.google.api.services.gmail.model.Thread> threaddate = response.getThreads();
				threads.addAll(threaddate);
				if (response.getNextPageToken() != null) {
					String pageToken = response.getNextPageToken();
					response = mailService.users().threads().list(account).setQ("label:SENT").setPageToken(pageToken)
							.execute();
				} else {
					break;
				}
			}

			for (com.google.api.services.gmail.model.Thread thread : threads) {
				System.out.println(thread.toPrettyString());
			}
//			  String nct=thread.getNextPageToken();

			return threads;

		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Message> getThreadMessages(String userId, String threadId) throws IOException {
		Gmail mailService;
		try {
			mailService = ServiceAccount.getGmailService(userId);
			Thread thread = mailService.users().threads().get(userId, threadId).setFormat("full").execute();
			return thread.getMessages();
		} catch (GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<String> getTotalMessages() {
		List<String> messageStat = new ArrayList<>();
		try {

			ListMessagesResponse mesages = getMessages().users().messages().list("me").execute();
			List<String> labelIds = new ArrayList<String>();
			labelIds.add("UNREAD");
			labelIds.add("INBOX");
			int unReadMessages = getMessages().users().messages().list("me").setLabelIds(labelIds).execute().size();
			messageStat.add("" + mesages.getMessages().size());
			messageStat.add("" + unReadMessages);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return messageStat;

	}

	public String getEmailAdress() {
		String email = "";
		try {
			email = getMessages().users().getProfile("me").execute().getEmailAddress();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;

	}

	@Override
	public Message sendMessage(String userId, String checkerEmailAdress, String emailContent)
			throws MessagingException, IOException {

		MimeMessage mimeMessage = createEmail(checkerEmailAdress, "me", "MARKER CHECKER", emailContent);
		Message message = createMessageWithEmail(mimeMessage);
		message = getMessages().users().messages().send(userId, message).execute();
		return message;
	}

	@Override
	public Message sendEmailMessage(String userId, String emailId, String numberOfLicences, String emailContent)
			throws MessagingException, IOException {

		String gmsSupport = gmsEmailAdressesRepository.findById(1).get().getEmailAddress();

		MimeMessage mimeMessage = createEmail(gmsSupport, "me", "LICENCE ADDITION", emailContent);
		Message message = createMessageWithEmail(mimeMessage);
		message = getMessages().users().messages().send(userId, message).execute();
		return message;
	}

	@Override
	public void UploadEmailMessage()
			throws MessagingException, IOException, GeneralSecurityException, URISyntaxException {

		Gmail userAccountService = ServiceAccount.getGmailService("mulunda@dev.businesscom.dk");
		Label toCreateLabel = new Label();
		toCreateLabel.setName("GmailMulunda");
		toCreateLabel.setLabelListVisibility("labelShow");
		toCreateLabel.setMessageListVisibility("show");
		Label tolabelCreate = getMessages().users().labels().create("me", toCreateLabel).execute();
		String lableId = tolabelCreate.getId();

		ListMessagesResponse messagesFromLable = userAccountService.users().messages().list("me").execute();
		List<Message> imporetedMessages = new ArrayList<>();
		for (int a = 0; a < messagesFromLable.getMessages().size(); a++) {
			Message mess = messagesFromLable.getMessages().get(a);
			Message uploadmessage = userAccountService.users().messages()
					.get("mulunda@dev.businesscom.dk", mess.getId()).setFormat("raw").execute();
			uploadmessage.setThreadId(null);

			Message sc = getMessages().users().messages()
					.gmailImport("me", uploadmessage, new AbstractInputStreamContent("message/rfc822") {
						byte[] bytes = uploadmessage.getRaw().getBytes();

						@Override
						public InputStream getInputStream() throws IOException {
							return new ByteArrayInputStream(bytes);
						}

						@Override
						public long getLength() throws IOException {
							return bytes.length;
						}

						@Override
						public boolean retrySupported() {
							return true;
						}
					}).execute();
			imporetedMessages.add(sc);
		}

		List<String> labelsToAdd = new ArrayList<>();
		labelsToAdd.add(lableId);
		ModifyMessageRequest mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd).setAddLabelIds(labelsToAdd);

		for (int a = 0; a < imporetedMessages.size(); a++) {
			Message messageNewLable = getMessages().users().messages()
					.modify("me", imporetedMessages.get(a).getId(), mods).execute();
		}

	}

	public MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);

		Multipart mp = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(bodyText, "text/html");
		mp.addBodyPart(htmlPart);
		email.setContent(mp);

		return email;
	}

	public Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

}
