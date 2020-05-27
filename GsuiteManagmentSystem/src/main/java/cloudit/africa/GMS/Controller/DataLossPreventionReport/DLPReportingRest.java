package cloudit.africa.GMS.Controller.DataLossPreventionReport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloudit.africa.GMS.Entity.*;
import cloudit.africa.GMS.GMSApiServices.DataLossPreventionService;
import cloudit.africa.GMS.GoogleApiServices.*;
import cloudit.africa.GMS.Model.*;
import cloudit.africa.GMS.Repository.DataLossPreventionReportRepository;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.Utilities.ServerResponseMessage;
import cloudit.africa.GMS.Utilities.ServiceResponse;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController
public class DLPReportingRest {

	@Autowired
	AdminReportingService adminReportingService;

	@Autowired
	SheetsApiService sheetsApiService;

	@Autowired
	GmailDataMigrationService gmailApiService;

	@Autowired
	GabSettingRepository gabSettingRepository;

	@Autowired
	DataLossPreventionReportRepository dataLossPreventionReportRepository;

	@Autowired
	DataLossPreventionService dataLossPreventionService;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat drivestartformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
	
	
	
	
	
	
	@RequestMapping("/GetDLPDriveReport")
	public ServiceResponse GetDLPDriveReport(@RequestBody DataLossPreventionFilter dataLossPreventionFilter) {
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			Date starting=dataLossPreventionFilter.getStartingDate();
			Date ending=dataLossPreventionFilter.getEndingDate();
			String customevent=dataLossPreventionFilter.getKeyword();
			String ipAddress=dataLossPreventionFilter.getIpAddress(); 
			String companyId=dataLossPreventionFilter.getCompany();
			List<DataLossPreventionReportDrive> reportData = dataLossPreventionService.getCustomDriveReportingAcrivity(starting, ending, customevent, ipAddress, companyId);
			serviceResponse.setData(reportData);
			serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
			serviceResponse.setPresent(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			serviceResponse.setData(e);
			serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
			serviceResponse.setPresent(false);
		}
//		List<GabEmailReport> messageReport = gmailApiService.CheckEmailSendOutSideDomain();
		return serviceResponse;
	}

	@RequestMapping("/getDriveDlpReporting/{companyId}")
	public ServiceResponse getDLPReporting(@PathVariable("companyId") String companyId) {

		ServiceResponse serviceResponse = new ServiceResponse();
		Company company = new Company();
		company.setCompanyId(companyId);
		try {
			Optional<DataLossPreventionReport> dataLossPrevention = dataLossPreventionReportRepository.findByDataLossPreventionReportTypeAndCompany(2, companyId);
			if (dataLossPrevention.isPresent()) {
				DataLossPreventionReport dataLossPreventionReport = dataLossPrevention.get();
				List<DataLossPreventionReportDrive> messageReport = dataLossPreventionService.getDriveReportingAcrivity(dataLossPreventionReport);
				serviceResponse.setData(messageReport);
				serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
				serviceResponse.setPresent(true);
			} else {
				serviceResponse.setData(null);
				serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
				serviceResponse.setPresent(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			serviceResponse.setData(e);
			serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
			serviceResponse.setPresent(false);
		}
//		List<GabEmailReport> messageReport = gmailApiService.CheckEmailSendOutSideDomain();
		return serviceResponse;
	}

	@RequestMapping("/getReportUsingKeyWord/{companyId}")
	public ServiceResponse getMessages(@PathVariable("companyId") String companyId) {
		ServiceResponse serviceResponse = new ServiceResponse();
		Company company = new Company();
		company.setCompanyId(companyId);
		try {
			System.out.println(" DATA PREVENTION " + companyId);
			Optional<DataLossPreventionReport> dataLossPrevention = dataLossPreventionReportRepository
					.findByDataLossPreventionReportTypeAndCompany(1, companyId);
			System.out.println(" DATA PREVENTION " + dataLossPrevention.isPresent());
			if (dataLossPrevention.isPresent()) {
				DataLossPreventionReport dataLossPreventionReport = dataLossPrevention.get();
				List<DataLossPreventionReportEmail> messageReport = dataLossPreventionService
						.CheckEmailSendOutSideDomain(dataLossPreventionReport);
				serviceResponse.setData(messageReport);
				System.out.println(" DATA  " + messageReport);

				serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
				serviceResponse.setPresent(true);
			} else {
				serviceResponse.setData(null);
				serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
				serviceResponse.setPresent(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			serviceResponse.setData(e);
			serviceResponse.setMessage(ServerResponseMessage.DLP_GMAIL_SUCCESS_MESSAGE);
			serviceResponse.setPresent(false);
		}
//		List<GabEmailReport> messageReport = gmailApiService.CheckEmailSendOutSideDomain();
		return serviceResponse;
	}

	@PostMapping("/DownloadReport")
	public ServiceResponse downloadReport(@RequestBody List<GabEmailReport> body) {
		String sheetUrl = sheetsApiService.saveDLPReportOnMyAccount(body);
		ServiceResponse seerver = new ServiceResponse();
		seerver.setData(body);
		seerver.setStatus(sheetUrl);
		return seerver;
	}

	@RequestMapping("/getReportUsing/{KeyWord}/{fromdate}/{todate}")
	public List<GabEmailReport> getMessageSearch(@PathVariable("KeyWord") String KeyWord,
			@PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
		System.out.println(": " + KeyWord);
		System.out.println(": " + todate);
		System.out.println(": " + fromdate);

		try {
			Date from = dateFormat.parse(fromdate);
			Date to = dateFormat.parse(todate);
			List<GabEmailReport> messageReport = gmailApiService.MySearchCheckEmailSendOutSideDomain(KeyWord,
					dateFormat.parse(fromdate), dateFormat.parse(todate));
			return messageReport;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
