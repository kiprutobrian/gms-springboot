package cloudit.africa.GMS.GoogleApiServices;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.StringUtils;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.Model.*;
import cloudit.africa.GMS.Repository.*;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;
import javassist.expr.NewArray;

@Service
public class GmailDataMigrationServiceImpl implements GmailDataMigrationService {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Gmail gooogleGmailApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Autowired
	GabSettingRepository gabSettingRepository;

	@Autowired
	GabReportAccountsRepository gabReportAccountsRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	SimpleDateFormat simpleDate = new SimpleDateFormat("yyy/mm/dd");

	public Gmail getGmailAuthService() {
		gooogleGmailApi = new Gmail.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return gooogleGmailApi;
	}

	@Override
	public List<String> getTotalMessages() {
		List<String> messageStat = new ArrayList<>();
		try {

			ListMessagesResponse mesages = getGmailAuthService().users().messages().list("me").execute();
			List<String> labelIds = new ArrayList<String>();
			labelIds.add("UNREAD");
			labelIds.add("INBOX");
			int unReadMessages = getGmailAuthService().users().messages().list("me").setLabelIds(labelIds).execute()
					.size();
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
			email = getGmailAuthService().users().getProfile("me").execute().getEmailAddress();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;

	}

	@Override
	public String getfilepth(String account, String messageId) {
		String filename = "";
		Gmail service = null;

		List<String> filepaths = new ArrayList<String>();
		try {
			service = ServiceAccount.getGmailService(account);
			Message message = service.users().messages().get(account, messageId).execute();
			List<MessagePart> parts = message.getPayload().getParts();
			for (MessagePart part : parts) {
				if (part.getFilename() != null && part.getFilename().length() > 0) {
					filename = part.getFilename();
					String attId = part.getBody().getAttachmentId();
					MessagePartBody attachPart = service.users().messages().attachments().get(account, messageId, attId)
							.execute();
					String path = filename;
					Base64 base64Url = new Base64(true);
					byte[] fileByteArray = base64Url.decodeBase64(attachPart.getData());
					FileOutputStream fileOutFile = new FileOutputStream(path);
					fileOutFile.write(fileByteArray);
					fileOutFile.close();
				}
			}
			return filename;
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
	public List<GabEmailReport> CheckEmailSendOutSideDomain() {
		List<GabEmailReport> filteredMessages = new ArrayList<>();
		GabSetting gabEmailDlp = gabSettingRepository.findById(6).get();
		String query="";
		String hosturl = gmsUrlsRepository.findById(7).get().getUrl();
		String keyWord = gabEmailDlp.getValue();
		Date lastReportDate = gabEmailDlp.getUpdatedOn();

		if (lastReportDate == null) {
			 query = "in:anywhere " + keyWord + ",after:" + simpleDate.format(lastReportDate);
		} else {
			 query = "in:anywhere " + keyWord + ",after:" + simpleDate.format(lastReportDate);
		}
		String domain;
		try {
			Directory directoryService = ServiceAccount.getDirectoryServices("edwinkorir@dev.businesscom.dk");
			Users result = directoryService.users().list().setCustomer("my_customer").setOrderBy("email").execute();
			for (int acc = 0; acc < result.getUsers().size(); acc++) {
				User userAccountSelected = result.getUsers().get(acc);
				if (!userAccountSelected.getSuspended()) {
					String account = userAccountSelected.getPrimaryEmail();
					domain = account.split("@")[1];
					ListMessagesResponse filteredEmailsWithContent;
					Gmail service = ServiceAccount.getGmailService(account);
					filteredEmailsWithContent = service.users().messages().list(account).setQ(query).execute();
					if (filteredEmailsWithContent.getMessages() != null) {
						for (int a = 0; a < filteredEmailsWithContent.getMessages().size(); a++) {
							Message mess = filteredEmailsWithContent.getMessages().get(a);
							Message messageswithKeyWord = service.users().messages().get(account, mess.getId())
									.execute();
							String fromEmail = getUserData("From", messageswithKeyWord.getPayload().getHeaders());
							String toEmail = getUserData("To", messageswithKeyWord.getPayload().getHeaders());
							String subject = getUserData("Subject", messageswithKeyWord.getPayload().getHeaders());
							Date date = new Date(messageswithKeyWord.getInternalDate());
							if (Utilities.isAfter(Utilities.getpreviousWeek(), date)) {
								List<MessagePart> parts = messageswithKeyWord.getPayload().getParts();
								if (toEmail != null && fromEmail != null) {
									if (fromEmail.contains(account)) {
										if (!(toEmail.contains(domain))) {
											DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
											String strDate = dateFormat.format(date);

											GabEmailReport messageReport = new GabEmailReport();
											messageReport.setMessageId(messageswithKeyWord.getId());
											messageReport.setThreadId(messageswithKeyWord.getThreadId());
											messageReport.setHistoryId("" + messageswithKeyWord.getHistoryId());
											messageReport.setFrom(account);
											messageReport.setTo(toEmail);
											messageReport.setSubject(subject);
											String body = StringUtils
													.newStringUtf8(Base64.decodeBase64(messageswithKeyWord.getPayload()
															.getParts().get(0).getBody().getData()));
											messageReport.setHasDriveAttachment("" + driveAcchamentShared(body));
											messageReport.setSnippet(body);
											messageReport.setHasAttachment(AttachmentOnAcchaments(
													messageswithKeyWord.getPayload().getParts()));
											messageReport.setDateSent(strDate);

											if ((AttachmentOnAcchaments(
													messageswithKeyWord.getPayload().getParts()) != null)) {
												System.out.println("xcxcxccxcxcxc " + AttachmentOnAcchaments(
														messageswithKeyWord.getPayload().getParts()));
												messageReport.setResourceurl(hosturl + "File" + "/" + account + "/"
														+ messageswithKeyWord.getId());
											} else {
												messageReport.setResourceurl("");
											}
											filteredMessages.add(messageReport);
										}
									}
								}
							}
						}
					}
				}
			}

			gabEmailDlp.setUpdatedOn(new Date());
			gabSettingRepository.saveAndFlush(gabEmailDlp);

			return filteredMessages;
		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Boolean driveAcchamentShared(String body) {
		if (body != null) {
			if (body.contains("<https://docs.google.com")) {
				return true;
			} else if (body.contains("<https://drive.google.com")) {
				return true;
			}

		}

		return null;
	}

	public String AttachmentOnAcchaments(List<MessagePart> parts) {

		for (MessagePart part : parts) {

			String filename = part.getFilename();
			String attId = part.getBody().getAttachmentId();

			System.out.println(filename);
			System.out.println(attId);
			if (part.getFilename() != null && part.getFilename().length() > 0) {
				return part.getFilename();
			}
		}
		return null;
	}

	public String getUserData(String name, List<MessagePartHeader> checkList) {
		String toEmail = null;
		for (int a = 0; a < checkList.size(); a++) {
			MessagePartHeader check = checkList.get(a);
			if (check.getName().equals(name)) {
				toEmail = check.getValue().replaceAll("\\s+", "");
				break;
			}
		}
		return toEmail;
	}

	@Override
	public void BackupEmailAccountData(List<DataMigrationAccount> dataMigrationAccount, String toAcccount)
			throws IOException, GeneralSecurityException, URISyntaxException {

		Gmail backupaccountService = ServiceAccount.getGmailService(toAcccount);
		for (DataMigrationAccount account : dataMigrationAccount) {

			String fromAccount = account.getEmailAddress().replaceAll("^\"|\"$", "");
			Gmail fromAccountService = ServiceAccount.getGmailService(fromAccount);

			Label toCreateLabel = new Label();
			toCreateLabel.setName(fromAccount + "-" + System.currentTimeMillis());
			toCreateLabel.setLabelListVisibility("labelShow");
			toCreateLabel.setMessageListVisibility("show");
			Label tolabelCreate = backupaccountService.users().labels().create("me", toCreateLabel).execute();
			String lableId = tolabelCreate.getId();
			ListMessagesResponse messagesFromLable = fromAccountService.users().messages().list("me").execute();

			System.out.println("======MIGRATION BEGINS HERE TO THE NEW ACCOUNT LABLE===========");
			List<Message> imporetedMessages = new ArrayList<>();
			for (int a = 0; a < messagesFromLable.getMessages().size(); a++) {
				Message mess = messagesFromLable.getMessages().get(a);
				Message uploadmessage = fromAccountService.users().messages().get(fromAccount, mess.getId())
						.setFormat("raw").execute();
				uploadmessage.setThreadId(null);
				removeCreatedLables(uploadmessage.getLabelIds());
				Message sc = backupaccountService.users().messages()
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
			ModifyMessageRequest mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd)
					.setAddLabelIds(labelsToAdd);
			for (int a = 0; a < imporetedMessages.size(); a++) {
				Message messageNewLable = backupaccountService.users().messages()
						.modify("me", imporetedMessages.get(a).getId(), mods).execute();
			}
		}

	}

	@Override
	public void RestoreEmailAccountData(List<DataRestore> dataRestoreAcount, String backupAccount)
			throws IOException, GeneralSecurityException, URISyntaxException {

		String backUpEmailAddress = backupAccount;
		Gmail backupaccountService = ServiceAccount.getGmailService(backUpEmailAddress);

		for (DataRestore dataRestore : dataRestoreAcount) {
			String emailaddress = dataRestore.getAccount();
			String lableId = dataRestore.getLableId();

			Gmail toAccountService = ServiceAccount.getGmailService(emailaddress);
			List<String> labelIds = new ArrayList<String>();
			labelIds.add(lableId);

			ListMessagesResponse backedupMessagesOnLable = backupaccountService.users().messages().list("me")
					.setLabelIds(labelIds).execute();
			List<Message> backedupMessages = new ArrayList<>();

			if (backedupMessagesOnLable.getMessages() != null) {

				for (int a = 0; a < backedupMessagesOnLable.getMessages().size(); a++) {
					Message mess = backedupMessagesOnLable.getMessages().get(a);
					Message uploadmessage = backupaccountService.users().messages()
							.get(backUpEmailAddress, mess.getId()).setFormat("raw").execute();
					uploadmessage.setThreadId(null);
					removeCreatedLables(uploadmessage.getLabelIds());
					System.out.println(removeCreatedLables(uploadmessage.getLabelIds()));
					Message sc = toAccountService.users().messages()
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
					backedupMessages.add(sc);
				}
				ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(labelIds);
			}
		}

	}

	public List<String> removeCreatedLables(List<String> lablesList) {
		if (lablesList != null) {
			for (int l = 0; l < lablesList.size(); l++) {
				String lable = lablesList.get(l);
				if (lable.contains("Label_")) {
					lablesList.remove(l);
				}
			}
		}
		return lablesList;
	}

	public List<Label> removeGoogleLables(List<Label> lablesList) {
		List<Label> lablesListupdated = new ArrayList<Label>();
		for (int l = 0; l < lablesList.size(); l++) {
			String lable = lablesList.get(l).getName().toLowerCase();
			if ((lable.contains("@"))) {
				System.out.println(lable);
				lablesListupdated.add(lablesList.get(l));
			}
		}
		return lablesListupdated;
	}

	@Override
	public List<Label> getRestorationLables(String backupaccount)
			throws IOException, GeneralSecurityException, URISyntaxException {
		Gmail backupaccountService = ServiceAccount.getGmailService(backupaccount);
		ListLabelsResponse response = backupaccountService.users().labels().list(backupaccount).execute();
		List<Label> labels = response.getLabels();

		return removeGoogleLables(labels);
	}

	@Override
	public List<GabEmailReport> MySearchCheckEmailSendOutSideDomain(String string, Date from, Date to) {
		// TODO Auto-generated method stub
		List<GabEmailReport> filteredMessages = new ArrayList<>();
		String hosturl = gmsUrlsRepository.findById(7).get().getUrl();
		String keyWord = string;
		String query = "in:anywhere " + keyWord;
		String domain;
		String admin = "edwinkorir@dev.businesscom.dk";
		try {
			Directory directoryService = ServiceAccount.getDirectoryServices(admin);
			Users result = directoryService.users().list().setCustomer("my_customer").setOrderBy("email").execute();
			for (int acc = 0; acc < result.getUsers().size(); acc++) {
				User userAccountSelected = result.getUsers().get(acc);
				if (!userAccountSelected.getSuspended()) {
					String account = userAccountSelected.getPrimaryEmail();
					domain = account.split("@")[1];
					ListMessagesResponse filteredEmailsWithContent;
					Gmail service = ServiceAccount.getGmailService(account);
					filteredEmailsWithContent = service.users().messages().list(account).setQ(query).execute();
					if (filteredEmailsWithContent.getMessages() != null) {
						for (int a = 0; a < filteredEmailsWithContent.getMessages().size(); a++) {
							Message mess = filteredEmailsWithContent.getMessages().get(a);
							Message messageswithKeyWord = service.users().messages().get(account, mess.getId())
									.execute();
							String fromEmail = getUserData("From", messageswithKeyWord.getPayload().getHeaders());
							String toEmail = getUserData("To", messageswithKeyWord.getPayload().getHeaders());
							String subject = getUserData("Subject", messageswithKeyWord.getPayload().getHeaders());
							Date date = new Date(messageswithKeyWord.getInternalDate());

							if (Utilities.isWithinRange(date, from, to)) {
								List<MessagePart> parts = messageswithKeyWord.getPayload().getParts();
								if (toEmail != null && fromEmail != null) {
									if (fromEmail.contains(account)) {
//										if (!(toEmail.contains(domain))) {
										DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
										String strDate = dateFormat.format(date);
										System.out.println("Converted String: " + strDate);
										GabEmailReport messageReport = new GabEmailReport();
										messageReport.setMessageId(messageswithKeyWord.getId());
										messageReport.setThreadId(messageswithKeyWord.getThreadId());
										messageReport.setHistoryId("" + messageswithKeyWord.getHistoryId());
										messageReport.setFrom(account);
										messageReport.setTo(toEmail);
										messageReport.setSubject(subject);
										String body = StringUtils.newStringUtf8(Base64.decodeBase64(messageswithKeyWord
												.getPayload().getParts().get(0).getBody().getData()));
										messageReport.setHasDriveAttachment("" + driveAcchamentShared(body));
										messageReport.setSnippet(body);
										messageReport.setHasAttachment(""
												+ AttachmentOnAcchaments(messageswithKeyWord.getPayload().getParts()));
										messageReport.setDateSent(strDate);
										messageReport.setResourceurl(
												hosturl + "File" + "/" + account + "/" + messageswithKeyWord.getId());
										filteredMessages.add(messageReport);
									}
//									}
								}
							}
						}
					}
				}
			}

			return filteredMessages;
		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message sendEmailReportWithAttachment(String to, String from, String subject, String bodyText,
			java.io.File file) throws MessagingException, IOException {

		Gmail service = null;
		try {
			service = ServiceAccount.gmsEmailServiceAccount(from);
			MimeMessage emailContent = createEmailWithAttachment(to, from, subject, bodyText, file);
			Message message = createMessageWithEmail(emailContent);
			message = service.users().messages().send(from, message).execute();
			System.out.println("Message id: " + message.getId());
			System.out.println(message.toPrettyString());
			return message;
		} catch (GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

	public static MimeMessage createEmailWithAttachment(String to, String from, String subject, String bodyText,
			java.io.File file) throws MessagingException, IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(bodyText, "text/plain");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		mimeBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);

		mimeBodyPart.setDataHandler(new DataHandler(source));
		mimeBodyPart.setFileName(file.getName());

		multipart.addBodyPart(mimeBodyPart);
		email.setContent(multipart);

		return email;
	}

}
