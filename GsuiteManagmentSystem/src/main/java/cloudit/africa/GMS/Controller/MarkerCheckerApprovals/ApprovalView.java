package cloudit.africa.GMS.Controller.MarkerCheckerApprovals;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloudit.africa.GMS.Entity.DriveWorkFlow;
import cloudit.africa.GMS.Entity.WorkFlow;
import cloudit.africa.GMS.GMSApiServices.CalenderService;
import cloudit.africa.GMS.GMSApiServices.DelegationService;
import cloudit.africa.GMS.GMSApiServices.DriveService;
import cloudit.africa.GMS.GMSApiServices.MarketingService;
import cloudit.africa.GMS.GMSApiServices.SignatureService;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Repository.DriveWorkFlowRepository;
import cloudit.africa.GMS.Repository.MarketingRepository;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.WorkFlowRepository;
import cloudit.africa.GMS.Utilities.Utilities;

@Controller
public class ApprovalView {

	@Autowired
	WorkFlowRepository workFlowRepository;

	@Autowired
	DriveWorkFlowRepository driveWorkFlowRepository;

	@Autowired
	SignatureService signatureService;
	@Autowired
	MarketingService marketingService;

	@Autowired
	DelegationService delegationService;

	@Autowired
	CalenderService calenderService;

	@Autowired
	DriveService driveService;
	
	@Autowired
	MarketingRepository marketingrepository;

	@Autowired
	SignatureRepository signatureRepository;

	
	
	@RequestMapping("Approve/{WorkFlowId}/{token}")
	public String approval(@PathVariable("WorkFlowId") String WorkFlowId, @PathVariable("token") String token,Model model) {
		Optional<WorkFlow> wrk = workFlowRepository.findById(Integer.parseInt(WorkFlowId));

		boolean result = wrk.get().getToken().equals(token)
				&& Utilities.checkTokenValidity(new Date(), wrk.get().getTokenExpirationTime());
		System.out.println("OVERAL" + result);
		
		
		model.addAttribute("Approvalurl", "ApproveAction/"+WorkFlowId+"/"+token);
		

		if (!wrk.get().isExecuted()) {

			if (wrk.get().getToken().equals(token) && Utilities.checkTokenValidity(new Date(), wrk.get().getTokenExpirationTime())) {
				KeyValue keyValue = new KeyValue();
				keyValue.setKey(wrk.get().getKey());
				keyValue.setOrgUser(wrk.get().getAccountsAction());
				boolean executed = false;
				
				model.addAttribute("organizationMembers", keyValue.getOrgUser());
				System.out.println("PROCESS TITLE : " + wrk.get().getName());

				switch (wrk.get().getName()) {
				case "UPDATE_SIGNATURE":
//					executed = signatureService.signatureUpdatesAccounts(keyValue);
					System.out.println("UPDATE : " + executed);
					model.addAttribute("keybody", signatureRepository.findById(Integer.parseInt((keyValue.getKey()))).get().getSignatureBody());					
					model.addAttribute("header", "SIGNATURE UPDATE");
					break;
				case "DELETE_SIGNATURE": {
//					executed = signatureService.signatureDeleteAccounts(keyValue);
					model.addAttribute("keybody", signatureRepository.findById(Integer.parseInt((keyValue.getKey()))).get().getSignatureBody());					
					System.out.println("DELETE : " + executed);
					model.addAttribute("header", "SIGNATURE REMOVAL");
					break;
				}
				case "MARKETING_BRANDING_UPDATE": {
//					executed = marketingService.marketingUpdatesAccounts(keyValue);
					System.out.println("UPDATE : " + executed);
					model.addAttribute("header", "MARKETING & BRANDING UPDATE");
					model.addAttribute("keybody", marketingrepository.findById(Integer.parseInt((keyValue.getKey()))).get().getMarketingBody());
					break;
				}

				case "UPDATE_DELEGATE": {
//					executed = delegationService.delegationUpdatesAccounts(keyValue);
					System.out.println("UPDATE DELEGATE : " + executed);
					model.addAttribute("keybody", "DELEGATE ACCOUNT  "+keyValue.getKey());					
					model.addAttribute("header", "MARKETING & BRANDING UPDATE");
					break;
				}
				case "DELETE_DELEGATE": {
//					executed = delegationService.delegationDeleteAccounts(keyValue);
					model.addAttribute("header", "REMOVE DELEGATE");
					model.addAttribute("keybody", "REMOVE DELEGATE ACCOUNT  "+keyValue.getKey());					
					
					System.out.println("DELETE DELEGATE : " + executed);
					break;
				}
				case "UPDATE_APPOINTMENTBOOKING": {
//					executed = calenderService.updateAppointmentBookingSlot(keyValue);
					model.addAttribute("header", "CALENDER APPOINTMENT BOOKING UPDATE");
					model.addAttribute("keybody", "SET CALENDER APPOINTMENT BOOKING");
					System.out.println("UPDATE CALENDER APPOINTMENTBOOKING : " + executed);
					break;
				}
				case "DELETE_APPOINTMENTBOOKING": {
					executed = calenderService.deleteAppointmentBookingSlot(keyValue);
					model.addAttribute("header", "REMOVE CALENDER APPOINTMENT BOOKING");
					model.addAttribute("keybody", "REMOVE CALENDER APPOINTMENT BOOKING");
					System.out.println("DELETE CALENDER APPOINTMENTBOOKING : " + executed);
					break;
				}

				case "GRANT_DRIVEACCESS": {
					executed = true;
					System.out.println("GRANT DRIVE : " + executed);
					break;
				}

				default:
					break;
				}

			}

			wrk.get().setExecuted(true);
//			workFlowRepository.saveAndFlush(wrk.get());
		}
		return "gms_approvalData";
	}

	@RequestMapping("DriveApprove/{WorkFlowId}/{token}")
	public String DriveApprove(@PathVariable("WorkFlowId") String WorkFlowId, @PathVariable("token") String token) {

		Optional<DriveWorkFlow> driveWorkFlow = driveWorkFlowRepository.findById(Integer.parseInt(WorkFlowId));

		boolean result = driveWorkFlow.get().getToken().equals(token)
				&& Utilities.checkTokenValidity(new Date(), driveWorkFlow.get().getTokenExpirationTime());
		System.out.println("OVERAL" + result);

		if (!driveWorkFlow.get().isExecuted()) {

			if (driveWorkFlow.get().getToken().equals(token)
					&& Utilities.checkTokenValidity(new Date(), driveWorkFlow.get().getTokenExpirationTime())) {

				
				boolean executed = false;

				System.out.println("PROCESS TITLE : " + driveWorkFlow.get().getName());

				switch (driveWorkFlow.get().getName()) {
				case "REVOKE_DRIVEACCESS": {
					executed = driveService.revockAcessesGeneral(driveWorkFlow.get());
					System.out.println("REVOKE DRIVE : " + executed);
					break;
				}

				default:
					break;
				}

			}

			driveWorkFlow.get().setExecuted(true);
			driveWorkFlowRepository.saveAndFlush(driveWorkFlow.get());
		}
		return "EXECUTED";
	}

}
