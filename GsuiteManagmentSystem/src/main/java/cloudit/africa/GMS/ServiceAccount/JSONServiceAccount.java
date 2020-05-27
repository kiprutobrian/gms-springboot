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
public class JSONServiceAccount {

	private static final String APPLICATION_NAME = "Gmail-Alexa";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	static String SERVICE_ACCOUNT_EMAIL_FROM_DEV_CONSOLE = "buscomservice@my-project-95-234306.iam.gserviceaccount.com";
	com.google.api.client.googleapis.auth.oauth2.GoogleCredential credential;

	public static String getP12DataFromPath(String domain) {
		String[] emailadress = domain.split("@");
		String fileName = "RegFile/" + ((emailadress[1]).replace(".", "")) + ".P12";
		ClassLoader classLoader = new GsuiteManagmentSystemApplication().getClass().getClassLoader();
		java.io.File file = new java.io.File(classLoader.getResource(fileName).getFile());
		return file.getPath();
	}

	public static String getJsonDataFromPath(String domain) {
		String[] emailadress = domain.split("@");
		String fileName = "RegFile/" + ((emailadress[1]).replace(".", "")) + ".json";
		ClassLoader classLoader = new GsuiteManagmentSystemApplication().getClass().getClassLoader();
		java.io.File file = new java.io.File(classLoader.getResource(fileName).getFile());
		return file.getPath();
	}

	/*
	 * =============================================================================
	 * ================================================ BEGINING JSON FILE TERRITORY
	 * =============================================================================
	 * ================================================
	 */

	public static Gmail getGmailService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(permisonsAll2());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Gmail service = new Gmail.Builder(new NetHttpTransport(), JSON_FACTORY, adapter).setApplicationName("GSUIT")
				.build();
		return service;
	}

	public static Gmail gmsEmailServiceAccount(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(GMSGmailScopesOnly());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Gmail service = new Gmail.Builder(new NetHttpTransport(), JSON_FACTORY, adapter).setApplicationName("GSUIT")
				.build();
		return service;
	}

	public static Drive getDriveService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(permisonsAllDrive());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Drive service = new Drive.Builder(new NetHttpTransport(), JSON_FACTORY, adapter).setApplicationName("GSUIT")
				.build();
		return service;
	}

	public static PeopleService getPeopleService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(permisonsAll());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		PeopleService service = new PeopleService.Builder(new NetHttpTransport(), JSON_FACTORY, adapter)
				.setApplicationName("GSUIT").build();
		return service;
	}

	public static Directory getDirectoryServices(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {

		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(permisonsAll());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Directory service = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY, adapter)
				.setApplicationName("GSUIT").build();

		return service;
	}

	public static Calendar getCalenderService(String userEmail) throws GeneralSecurityException, IOException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(permisonsAll());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Calendar service = new Calendar.Builder(new NetHttpTransport(), JSON_FACTORY, adapter)
				.setApplicationName("GSUIT").build();
		return service;
	}

	public static Sheets getSheetService(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {
		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		OAuth2Credentials credential = ServiceAccountCredentials.fromStream(resourceAsStream).createDelegated(userEmail)
				.createScoped(SheetScopes());
		HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credential);
		Sheets service = new Sheets.Builder(new NetHttpTransport(), JSON_FACTORY, adapter).setApplicationName("GSUIT")
				.build();
		return service;
	}

	public void alert() {
		ClassLoader classLoader = new GsuiteManagmentSystemApplication().getClass().getClassLoader();
		java.io.File file = new java.io.File(classLoader.getResource("credentials.json").getFile());
		InputStream in = GsuiteManagmentSystemApplication.class.getResourceAsStream(file.getPath());
		try {
			com.google.auth.oauth2.GoogleCredentials credentials = ServiceAccountCredentials.fromStream(in)
					.createDelegated("edwin")
					.createScoped(Collections.singleton("https://www.googleapis.com/auth/apps.alerts"));
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

	/*
	 * =============================================================================
	 * ================================================ END JSON FILE TERRITORY
	 * =============================================================================
	 * ================================================
	 */

	/*
	 * =============================================================================
	 * ================================================ BEGINING P12 FILE TERITORY
	 * =============================================================================
	 * ================================================
	 */
	public static GoogleCredential getGoogleCredentialServices(String userEmail)
			throws GeneralSecurityException, IOException, URISyntaxException {

		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		String serviceAccountEmail = ServiceAccountCredentials.fromStream(resourceAsStream).getServiceAccountUser();

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(serviceAccountEmail)
				.setServiceAccountPrivateKeyFromP12File(new File(getP12DataFromPath(userEmail)))
				.setServiceAccountScopes(permisonsAll()).setServiceAccountUser(userEmail).build();
		credential.getAccessToken();
		ContactsService contactsService = new ContactsService("MY_PRODUCT_NAME");
		contactsService.setOAuth2Credentials(credential);
		return credential;
	}

	public static ContactsService getconnect(String userEmail) throws GeneralSecurityException, IOException {

		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		String serviceAccountEmail = ServiceAccountCredentials.fromStream(resourceAsStream).getServiceAccountUser();
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JacksonFactory.getDefaultInstance()).setServiceAccountUser(userEmail)
				.setServiceAccountId(serviceAccountEmail)
				.setServiceAccountScopes(Collections.singleton("https://www.google.com/m8/feeds/"))
				.setServiceAccountPrivateKeyFromP12File(new File(getP12DataFromPath(userEmail))).build();
		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		ContactsService myService = new ContactsService(APPLICATION_NAME);
		myService.setOAuth2Credentials(credential);
		return myService;
	}

	public static ContactsService getconnectDelete(String userEmail) throws GeneralSecurityException, IOException {

		InputStream resourceAsStream = new GsuiteManagmentSystemApplication().getClass().getClassLoader()
				.getResourceAsStream(getJsonDataFromPath(userEmail));
		String serviceAccountEmail = ServiceAccountCredentials.fromStream(resourceAsStream).getServiceAccountUser();

		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JacksonFactory.getDefaultInstance()).setServiceAccountUser(userEmail)
				.setServiceAccountId(serviceAccountEmail)
				.setServiceAccountScopes(Collections.singleton("https://www.googleapis.com/auth/contacts.readonly"))
				.setServiceAccountPrivateKeyFromP12File(new File(getP12DataFromPath(userEmail))).build();
		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		ContactsService myService = new ContactsService(APPLICATION_NAME);
		myService.setOAuth2Credentials(credential);
		credential.refreshToken();
		return myService;
	}

	/*
	 * =============================================================================
	 * ================================================ END P12 FILE TERITORY
	 * =============================================================================
	 */

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

}
