package cloudit.africa.GMS.ServiceAccount;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.alertcenter.v1beta1.AlertCenter;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.OAuth2Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.gdata.client.contacts.ContactsService;

import cloudit.africa.GMS.GsuiteManagmentSystemApplication;

@RestController
public class ServiceAccount2 {

	private static final String APPLICATION_NAME = "Gmail-Alexa";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	static String SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE = "buscomservice@my-project-95-234306.iam.gserviceaccount.com";	
	com.google.api.client.googleapis.auth.oauth2.GoogleCredential credential;
	
//	@Autowired
//	RegistrationForm registrationForm;


	public ServiceAccount2() {
	}

	public static String getDataFromPath(String domain) {
		String[] emailadress=domain.split("@");
//		String fileName = "RegFile/"+((emailadress[1]).replace(".", ""))+".p12";
		ClassLoader classLoader = new GsuiteManagmentSystemApplication().getClass().getClassLoader();
		
		String fileName = GMSFilesPath.readLineByLineJava8()+((emailadress[1]).replace(".", ""))+".p12";
		
		java.io.File file = new java.io.File(fileName);
		return file.getPath();
	}
	
	
	public static String getJsonDataFromPath(String domain) {
		String[] emailadress=domain.split("@");
//		String fileName = "RegFile/"+((emailadress[1]).replace(".", ""))+".json";
//		ClassLoader classLoader = new GsuiteManagmentSystemApplication().getClass().getClassLoader();
//		java.io.File file = new java.io.File(classLoader.getResource(fileName).getFile());
//		
		String fileName = GMSFilesPath.readLineByLineJava8()+((emailadress[1]).replace(".", ""))+".json";
		java.io.File file = new java.io.File(fileName);
		
		return file.getPath();
	}
	
	
	public static Gmail getGmailService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {

		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail)))
				.setServiceAccountScopes(permisonsAll2()).setServiceAccountUser(userEmail).build();
		credential.getRefreshToken();
		Gmail service = new Gmail.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential)
				.setApplicationName("Gsuit").build();
		return service;
	}

	public static Drive getDriveService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail)))
				.setServiceAccountScopes(permisonsAllDrive()).setServiceAccountUser(userEmail).build();
		credential.getRefreshToken();
		Drive service = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential)
				.setApplicationName("Gsuit").build();
		return service;
	}

	public static PeopleService getPeopleService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail)))
				.setServiceAccountScopes(permisonsAll()).setServiceAccountUser(userEmail).build();
		credential.getRefreshToken();

		PeopleService service = new PeopleService.Builder(httpTransport, jsonFactory, null)
				.setHttpRequestInitializer(credential).setApplicationName("Gsuit").build();
		return service;
	}

	public static Directory getDirectoryServices(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail)))
				.setServiceAccountScopes(Collections.singleton(DirectoryScopes.ADMIN_DIRECTORY_USER))
				.setServiceAccountUser(userEmail).build();
		credential.getRefreshToken();
		Directory directoryservice = new Directory.Builder(httpTransport, jsonFactory, null)
				.setHttpRequestInitializer(credential).setApplicationName("Gsuit").build();
		return directoryservice;
	}

	public static GoogleCredential getGoogleCredentialServices(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail)))
				.setServiceAccountScopes(permisonsAll()).setServiceAccountUser(userEmail).build();
		credential.getAccessToken();
		ContactsService contactsService = new ContactsService("MY_PRODUCT_NAME");
		contactsService.setOAuth2Credentials(credential);	       
		return credential;
	}

	public static Calendar getCalenderService(String userEmail) throws GeneralSecurityException, IOException {
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JacksonFactory.getDefaultInstance()).setServiceAccountUser(userEmail)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountScopes(Collections.singleton("https://www.googleapis.com/auth/calendar"))
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail))).build();
		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		Calendar myService = new Calendar.Builder(httpTransport, jsonFactory, null)
				.setHttpRequestInitializer(credential).setApplicationName("Gsuit").build();
		return myService;
	}

	public static ContactsService getconnect(String userEmail) throws GeneralSecurityException, IOException {
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JacksonFactory.getDefaultInstance()).setServiceAccountUser(userEmail)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountScopes(Collections.singleton("https://www.google.com/m8/feeds/"))
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail))).build();
		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		ContactsService myService = new ContactsService(APPLICATION_NAME);
		myService.setOAuth2Credentials(credential);
		return myService;
	}

	public static ContactsService getconnectDelete(String userEmail) throws GeneralSecurityException, IOException {
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JacksonFactory.getDefaultInstance()).setServiceAccountUser(userEmail)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE)
				.setServiceAccountScopes(Collections.singleton("https://www.googleapis.com/auth/contacts.readonly"))
				.setServiceAccountPrivateKeyFromP12File(new File(getDataFromPath(userEmail))).build();
		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		ContactsService myService = new ContactsService(APPLICATION_NAME);
		myService.setOAuth2Credentials(credential);
		credential.refreshToken();
		return myService;
	}
	
	
	
	public static Sheets getSheetService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader().getResourceAsStream(getDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(SheetScopes());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Sheets service = new Sheets.Builder(new NetHttpTransport(), JSON_FACTORY, adapter).setApplicationName("GSUIT").build();

		return service;
	}
	
	
	
	
	
	
	public static Gmail gmsEmailServiceAccount(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader().getResourceAsStream(getDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(GMSGmailScopesOnly());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Gmail service = new Gmail.Builder(new NetHttpTransport(), JSON_FACTORY, adapter).setApplicationName("GSUIT").build();
		return service;
	}
	
	
	
	public static List<String> permisonsAll() {
		List<String> permisions = new ArrayList<>();
		permisions.add(GmailScopes.GMAIL_SETTINGS_SHARING);
		permisions.add(GmailScopes.GMAIL_SETTINGS_BASIC);
		permisions.add(DirectoryScopes.ADMIN_DIRECTORY_USER);
		permisions.add("https://www.google.com/m8/feeds/");
		permisions.add("https:/credential/www.googleapis.com/auth/contacts.readonly");
		permisions.add(CalendarScopes.CALENDAR);
		return permisions;
	}
	
	
	public static List<String> SheetScopes() {
		List<String> permisions = new ArrayList<String>();
		permisions.add(SheetsScopes.SPREADSHEETS);
		permisions.add(SheetsScopes.DRIVE);
		return permisions;
	}
	
	public static List<String> GMSGmailScopesOnly() {
		List<String> permisions = new ArrayList<String>();
		permisions.add(GmailScopes.GMAIL_READONLY);
		permisions.add(GmailScopes.MAIL_GOOGLE_COM);
		permisions.add(GmailScopes.GMAIL_LABELS);
		return permisions;
	}

	public static List<String> permisonsAll2() {
		List<String> permisions = new ArrayList<String>();
		permisions.add(GmailScopes.GMAIL_SETTINGS_SHARING);
		permisions.add(GmailScopes.GMAIL_SETTINGS_BASIC);
		permisions.add(GmailScopes.MAIL_GOOGLE_COM);
		permisions.add(GmailScopes.GMAIL_LABELS);
		return permisions;
	}

	public static List<String> permisonsAllDrive() {
		List<String> permisionsdrive = new ArrayList<String>();
		permisionsdrive.add("https://www.googleapis.com/auth/drive");
		return permisionsdrive;
	}

	public void alert() {
		ClassLoader classLoader = new GsuiteManagmentSystemApplication().getClass().getClassLoader();
		java.io.File file = new java.io.File(classLoader.getResource("credentials.json").getFile());
		InputStream in = GsuiteManagmentSystemApplication.class.getResourceAsStream(file.getPath());
		try {
			com.google.auth.oauth2.GoogleCredentials credentials = ServiceAccountCredentials.fromStream(in).createDelegated("edwin").createScoped(Collections.singleton("https://www.googleapis.com/auth/apps.alerts"));
			ApacheHttpTransport transport = new ApacheHttpTransport();
			HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credentials);
			AlertCenter alertCenter = new AlertCenter.Builder(transport, new JacksonFactory(), adapter)
					.setApplicationName("Alert Center client").build();
			
			alertCenter.alerts().list().execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	

		public static Directory getTesting(String userEmail)
				throws GeneralSecurityException, IOException, URISyntaxException {
			InputStream resourceAsStream = GsuiteManagmentSystemApplication.class.getClassLoader().getResourceAsStream("config/my-project-95-234306-2d8397b692e7.json");
			OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail).createScoped(Collections.singleton(DirectoryScopes.ADMIN_DIRECTORY_USER));			
			ApacheHttpTransport transport = new ApacheHttpTransport();
			HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
			System.out.println("==============================="+credential.getAuthenticationType());	
			Directory service = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY,adapter).setApplicationName("GSUIT").build();	
		
			return null;
		}

		
		
	


	
	
}
