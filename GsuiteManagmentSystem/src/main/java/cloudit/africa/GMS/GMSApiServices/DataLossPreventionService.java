package cloudit.africa.GMS.GMSApiServices;

import java.util.Date;
import java.util.List;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.DataLossPreventionReport;
import cloudit.africa.GMS.Model.DataLossPreventionReportDrive;
import cloudit.africa.GMS.Model.DataLossPreventionReportEmail;
import cloudit.africa.GMS.Model.DriveDlp;
import cloudit.africa.GMS.Model.GabEmailReport;

public interface DataLossPreventionService {

	
	List<DataLossPreventionReportEmail> CheckEmailSendOutSideDomain(DataLossPreventionReport dataLossPreventionReport);
	List<DataLossPreventionReportDrive> getDriveReportingAcrivity(DataLossPreventionReport dataLossPreventionReport);
	List<DataLossPreventionReportDrive> getCustomDriveReportingAcrivity(Date starting, Date ending, String customevent,String ipAddress, String companyId);

}
