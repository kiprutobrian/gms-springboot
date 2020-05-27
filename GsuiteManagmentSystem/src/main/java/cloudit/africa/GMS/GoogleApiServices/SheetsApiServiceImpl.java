package cloudit.africa.GMS.GoogleApiServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.reports.model.Activity;
import com.google.api.services.admin.reports.model.Activity.Events;
import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.Model.*;
import cloudit.africa.GMS.Repository.GabReportAccountsRepository;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;


@Service
public class SheetsApiServiceImpl implements SheetsApiService {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Sheets gooogleSheetsApi;
	String pattern = "MM-dd-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	@Autowired
	GabReportAccountsRepository gabReportAccountsRepository;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Autowired
	GmailDataMigrationService gmailApiServices;

	@Autowired
	GabSettingRepository gabSettingRepository;

	
	public Sheets getSheetsService() {
		gooogleSheetsApi = new Sheets.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("CLOUDIT").build();
		return gooogleSheetsApi;
	}

	@Override
	public String saveDLPReportOnMyAccount(List<GabEmailReport> myData) {
		String title = "GABMailDLReport-" + System.currentTimeMillis();
		Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
		try {

			Sheets gooogleSheetsApi = getSheetsService();
			spreadsheet = gooogleSheetsApi.spreadsheets().create(spreadsheet).setFields("spreadsheetId,spreadsheetUrl")
					.execute();
			String url = spreadsheet.getSpreadsheetUrl();
			System.out.println("Spreadsheet URL: " + url);
			String spreadsheetId = spreadsheet.getSpreadsheetId();
			String writeRange = "Sheet1!A1";
			myData.add(0, new GabEmailReport("MessageId", "threadId", "historyId", "Sent By", "Sent To", "Subject",
					"Message", "Has Shared Drive Attachment ", "Has File Attachment", "Date Sent", "Download file"));

			List<List<Object>> writeData = new ArrayList<>();
			for (GabEmailReport someData : myData) {
				List<Object> dataRow = new ArrayList<>();
				dataRow.add("" + someData.getMessageId());
				dataRow.add("" + someData.getDateSent());
				dataRow.add("" + someData.getFrom());
				dataRow.add("" + someData.getTo());
				dataRow.add("" + someData.getSubject());
				dataRow.add("" + someData.getHasDriveAttachment());
				dataRow.add("" + someData.getHasAttachment());
				dataRow.add("" + someData.getResourceurl());
				dataRow.add("" + someData.getSnippet());
				writeData.add(dataRow);
			}
			ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
			UpdateValuesResponse response = gooogleSheetsApi.spreadsheets().values()
					.update(spreadsheetId, writeRange, vr).setValueInputOption("RAW").execute();
			return url;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean createGmailDLPReportAndSendReport(List<GabEmailReport> myData) {
		String title = "GABMailDLReport-" + System.currentTimeMillis();
		
		GabSetting systemSetting=gabSettingRepository.findById(1).get();;
		GabSetting gmailSetting=gabSettingRepository.findById(3).get();;
		
		String systemAccountAddress=systemSetting.getValue();
		String groupEmailAddress=gmailSetting.getValue();
	
		
		Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
		try {

			Sheets gooogleSheetsApi = ServiceAccount.getSheetService(systemAccountAddress);
			
			spreadsheet = gooogleSheetsApi.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
			spreadsheet.getSpreadsheetUrl();
			String spreadsheetId = spreadsheet.getSpreadsheetId();
			String writeRange = "Sheet1!A1";
			myData.add(0, new GabEmailReport("MessageId", "threadId", "historyId", "Sent By", "Sent To", "Subject","Message", "Has Shared Drive Attachment ", "Has File Attachment", "Date Sent", "Download file"));

			List<List<Object>> writeData = new ArrayList<>();
			for (GabEmailReport someData : myData) {
				List<Object> dataRow = new ArrayList<>();
				dataRow.add("" + someData.getMessageId());
				dataRow.add("" + someData.getDateSent());
				dataRow.add("" + someData.getFrom());
				dataRow.add("" + someData.getTo());
				dataRow.add("" + someData.getSubject());
				dataRow.add("" + someData.getHasDriveAttachment());
				dataRow.add("" + someData.getHasAttachment());
				dataRow.add("" + someData.getResourceurl());
				dataRow.add("" + someData.getSnippet());
				writeData.add(dataRow);
			}
			ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
			UpdateValuesResponse response = gooogleSheetsApi.spreadsheets().values().update(spreadsheetId, writeRange, vr).setValueInputOption("RAW").execute();

			String fileId = response.getSpreadsheetId();
			OutputStream outputStream = new FileOutputStream(title);
			ServiceAccount.getDriveService(systemAccountAddress).files().export(fileId, "application/x-vnd.oasis.opendocument.spreadsheet").executeMediaAndDownloadTo(outputStream);
			outputStream.close();

			File file = new File(title);
			File file2 =new File(title+".xlsx");
			file.renameTo(file2);
			try {
				
				Message mes = gmailApiServices.sendEmailReportWithAttachment(groupEmailAddress,systemAccountAddress, "Email DLP REPORTING","The message contain dlp reporting for the period "+ Utilities.getpreviousWeek() +" to "+new Date()+"", file2);
				System.out.println("Message : " + mes);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<DriveDlp> createDownloadedFileDLPReport(List<DriveDlp> myData) {
		try {
			List<DriveDlp> downloaedfiles = new ArrayList<>();
			String title = "GABDriveDLReport-" + System.currentTimeMillis();
			
			Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
			gooogleSheetsApi = getSheetsService();
			spreadsheet = gooogleSheetsApi.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
			spreadsheet.getSpreadsheetUrl();
			System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
			String spreadsheetId = spreadsheet.getSpreadsheetId();
			String writeRange = "Sheet1!A1";
			List<List<Object>> writeData = new ArrayList<>();
			myData.add(0,
					new DriveDlp("Item Name", "Event Description", "User Account", "Download Date", "Event Name","Item Id", "Item Type", "Item Owner", "Prior Visibility", "visibility","Downloaded IPAddress", "last View IpAddress", "Last View Date"));
			for (DriveDlp someData : myData) {

				List<Object> dataRow = new ArrayList<>();
				dataRow.add("" + someData.getItemName());
				dataRow.add("" + someData.getEventDescription());
				dataRow.add("" + someData.getUser());
				dataRow.add(someData.getDate());
				dataRow.add("" + someData.getEventName());
				dataRow.add("" + someData.getItemId());
				dataRow.add("" + someData.getItemType());
				dataRow.add("" + someData.getOwner());
				dataRow.add("" + someData.getPriorVisibility());
				dataRow.add("" + someData.getVisibility());
				dataRow.add("" + someData.getiPAddress());
				dataRow.add("" + someData.getLastViewIp());
				dataRow.add(someData.getViewDate());
				writeData.add(dataRow);
			}

			ValueRange valueRange = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
			UpdateValuesResponse response;
			response = gooogleSheetsApi.spreadsheets().values().update(spreadsheetId, writeRange, valueRange).setValueInputOption("RAW").execute();
			System.out.println(response);
			return downloaedfiles;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<DriveDlp> createDriveDLPReportAndSendReport(List<DriveDlp> myData) {
		try {
			List<DriveDlp> downloaedfiles = new ArrayList<>();
			String title = "GABDriveDLReport-" + System.currentTimeMillis();
			
			GabSetting systemSetting=gabSettingRepository.findById(1).get();;
			GabSetting gmailSetting=gabSettingRepository.findById(2).get();;
			
			String systemAccountAddress=systemSetting.getValue();
			String groupEmailAddress=gmailSetting.getValue();
		
			
			Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
			gooogleSheetsApi = ServiceAccount.getSheetService((systemAccountAddress));
			spreadsheet = gooogleSheetsApi.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
			spreadsheet.getSpreadsheetUrl();
			System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
			String spreadsheetId = spreadsheet.getSpreadsheetId();
			String writeRange = "Sheet1!A1";
			
			List<List<Object>> writeData = new ArrayList<>();
			myData.add(0,new DriveDlp("Item Name", "Event Description", "User Account", "Download Date", "Event Name","Item Id", "Item Type", "Item Owner", "Prior Visibility", "visibility","Downloaded IPAddress", "last View IpAddress", "Last View Date"));
			
			for (DriveDlp someData : myData) {
				List<Object> dataRow = new ArrayList<>();
				dataRow.add("" + someData.getItemName());
				dataRow.add("" + someData.getEventDescription());
				dataRow.add("" + someData.getUser());
				dataRow.add(someData.getDate());
				dataRow.add("" + someData.getEventName());
				dataRow.add("" + someData.getItemId());
				dataRow.add("" + someData.getItemType());
				dataRow.add("" + someData.getOwner());
				dataRow.add("" + someData.getPriorVisibility());
				dataRow.add("" + someData.getVisibility());
				dataRow.add("" + someData.getiPAddress());
				dataRow.add("" + someData.getLastViewIp());
				dataRow.add(someData.getViewDate());
				writeData.add(dataRow);
			}

			ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
			UpdateValuesResponse response;
			response = gooogleSheetsApi.spreadsheets().values().update(spreadsheetId, writeRange, vr).setValueInputOption("RAW").execute();

			
			String fileId = response.getSpreadsheetId();
			OutputStream outputStream = new FileOutputStream(title);
			ServiceAccount.getDriveService(systemAccountAddress).files().export(fileId, "application/x-vnd.oasis.opendocument.spreadsheet").executeMediaAndDownloadTo(outputStream);
			outputStream.close();

			File file = new File(title);
			File file2 =new File(title+".xlsx");
			file.renameTo(file2);
			
			
					
			Message mes = gmailApiServices.sendEmailReportWithAttachment(groupEmailAddress,systemAccountAddress, "Weekly Drive DLP REPORTING","The message contain DLP reporting for the period "+ Utilities.getpreviousWeek() +" to "+new Date()+"", file2);
			System.out.println("Message : " + mes);

			return downloaedfiles;

		} catch (IOException | GeneralSecurityException | URISyntaxException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
