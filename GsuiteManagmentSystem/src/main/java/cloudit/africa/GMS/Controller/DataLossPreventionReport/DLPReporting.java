package cloudit.africa.GMS.Controller.DataLossPreventionReport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.gmail.model.Message;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.DataLossPreventionReport;
import cloudit.africa.GMS.Entity.GabSetting;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.DataLossPreventionService;
import cloudit.africa.GMS.GoogleApiServices.GmailDataMigrationService;
import cloudit.africa.GMS.GoogleApiServices.SheetsApiService;
import cloudit.africa.GMS.Repository.DataLossPreventionReportRepository;
import cloudit.africa.GMS.Repository.GabSettingRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;
import cloudit.africa.GMS.Utilities.Config.Pages;

@Controller
public class DLPReporting {

	@Autowired
	GmailDataMigrationService gmailApiService;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GlobalModelView globalModelView;

	@Autowired
	GabSettingRepository gabSettingRepository;

	@Autowired
	SheetsApiService sheetsApiService;

	@Autowired
	DataLossPreventionService dataLossPreventionService;

	@Autowired
	DataLossPreventionReportRepository dataLossPreventionReportRepository;

	@RequestMapping("/EmailReport")
	public String getDLPReporting(Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		Optional<DataLossPreventionReport> dataLossPrevention = dataLossPreventionReportRepository
				.findByDataLossPreventionReportTypeAndCompany(1, authenticatedUser.getCompany().getCompanyId());

		if (dataLossPrevention.isPresent()) {
			try {
				DataLossPreventionReport dataLossPreventionReport = dataLossPrevention.get();
				model.addAttribute("keyword", "Current KeyWord Filter: " + dataLossPreventionReport.getKeyValue());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			model.addAttribute("alert", "KEY WORD NOT THERE ");
		}
		return Pages.DLP_MAIL_REPORT_PAGE;
	}

	@RequestMapping("/DriveReport")
	public String getDLPDriveReportReporting(Model model, HttpServletRequest request) {
		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		try {
			Optional<DataLossPreventionReport> dataLossPrevention = dataLossPreventionReportRepository
					.findByDataLossPreventionReportTypeAndCompany(2, authenticatedUser.getCompany().getCompanyId());
			if (dataLossPrevention.isPresent()) {
				DataLossPreventionReport dataLossPreventionReport = dataLossPrevention.get();
				model.addAttribute("ipAddress", "" + dataLossPreventionReport.getKeyValue());
			} else model.addAttribute("alert", "KEY WORD NOT THERE ");;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return Pages.DLP_DRIVE_REPORT_PAGE;
	}
}
