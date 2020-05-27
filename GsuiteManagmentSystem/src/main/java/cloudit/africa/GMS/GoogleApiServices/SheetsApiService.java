package cloudit.africa.GMS.GoogleApiServices;

import java.util.HashMap;
import java.util.List;

import com.google.api.services.admin.reports.model.Activity;

import cloudit.africa.GMS.Model.*;


public interface SheetsApiService {

	boolean createGmailDLPReportAndSendReport(List<GabEmailReport> myData);

	List<DriveDlp> createDownloadedFileDLPReport(List<DriveDlp> myData);

	String saveDLPReportOnMyAccount(List<GabEmailReport> myData);

	List<DriveDlp> createDriveDLPReportAndSendReport(List<DriveDlp> myData);

}
