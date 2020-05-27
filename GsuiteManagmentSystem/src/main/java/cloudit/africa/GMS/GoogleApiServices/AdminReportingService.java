package cloudit.africa.GMS.GoogleApiServices;

import java.util.Date;
import java.util.List;

import com.google.api.services.admin.reports.Reports;
import com.google.api.services.admin.reports.model.Activities;
import com.google.api.services.admin.reports.model.Activity;

import cloudit.africa.GMS.Model.DriveDlp;

public interface AdminReportingService {

	List<Activity> getDriveReportingAcrivity();

	List<DriveDlp> getDriveReportingAcrivity(String startingDate);


	List<Activity> getDriveActivityReport(Reports reportService, String userAccount, String applicationName);

	
	List<Activity> getCustomDriveActivityReport(Reports reportService, String userAccount, String applicationName,
			Date startingDate, Date endingDate, String event);
}
