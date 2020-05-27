package cloudit.africa.GMS.JobShedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.GoogleApiServices.AdminReportingService;
import cloudit.africa.GMS.GoogleApiServices.GmailDataMigrationService;
import cloudit.africa.GMS.GoogleApiServices.SheetsApiService;
import cloudit.africa.GMS.Model.DriveDlp;
import cloudit.africa.GMS.Model.GabEmailReport;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.Utilities.Utilities;

@Configuration
@EnableScheduling
public class JobShedules {

	@Autowired
	AdminReportingService adminReportingService;

	@Autowired
	SheetsApiService sheetsApiService;

	@Autowired
	GmailDataMigrationService gmailApiService;

	@Autowired
	GabSettingRepository gabSettingRepository;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat drivestartformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

//	run 23:00 every Sunday.
	@Scheduled(cron = "0 0 23 * * 0")
//	@Scheduled(fixedRate = 1000)
	public void weeeklySheduledProcesses() {
		CreateDriveDlpReport();
		createEmailDLPReporting();
	}

	public void MonthlySheduledProcesses() {

	}

	public List<DriveDlp> CreateDriveDlpReport() {
		GabSetting drivedSetting = gabSettingRepository.findById(5).get();
		System.out.println("execurted");
		
		String startingDate = "" + drivestartformat.format(Utilities.getpreviousWeek());
		List<DriveDlp> myData = adminReportingService.getDriveReportingAcrivity(startingDate);
		String ipaddress = drivedSetting.getValue();
		
		if(myData != null) {
		for (int xc = 0; xc < myData.size(); xc++) {
			DriveDlp driveAudit = myData.get(xc);
			if (!(Utilities.isIsIpAddressOutsideMyDomain(ipaddress, driveAudit.getiPAddress(),
					driveAudit.getLastViewIp()))) {
				myData.remove(driveAudit);
			}
		}
		sheetsApiService.createDriveDLPReportAndSendReport(myData);}
		return myData;
	}

	public List<GabEmailReport> createEmailDLPReporting() {
		List<GabEmailReport> messageReport = gmailApiService.CheckEmailSendOutSideDomain();
		System.out.println("======================="+messageReport.size());
		if(messageReport.size()>1) {
		boolean iscreated = sheetsApiService.createGmailDLPReportAndSendReport(messageReport);
		}
		return messageReport;
	}

}
