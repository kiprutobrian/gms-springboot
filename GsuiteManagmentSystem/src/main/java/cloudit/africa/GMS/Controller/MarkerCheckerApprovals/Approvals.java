package cloudit.africa.GMS.Controller.MarkerCheckerApprovals;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import cloudit.africa.GMS.Repository.WorkFlowRepository;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController
public class Approvals {

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

	@RequestMapping("ApproveAction/{WorkFlowId}/{token}")
	public String approval(@PathVariable("WorkFlowId") String WorkFlowId, @PathVariable("token") String token,@RequestBody String comments) {
		Optional<WorkFlow> wrk = workFlowRepository.findById(Integer.parseInt(WorkFlowId));

		boolean result = wrk.get().getToken().equals(token)
				&& Utilities.checkTokenValidity(new Date(), wrk.get().getTokenExpirationTime());
		System.out.println("OVERAL" + result);

		if (!wrk.get().isExecuted()) {

			if (wrk.get().getToken().equals(token) && Utilities.checkTokenValidity(new Date(), wrk.get().getTokenExpirationTime())) {
				KeyValue keyValue = new KeyValue();
				keyValue.setKey(wrk.get().getKey());
				keyValue.setOrgUser(wrk.get().getAccountsAction());
				boolean executed = false;
				
				System.out.println("PROCESS TITLE : " + wrk.get().getName());

				switch (wrk.get().getName()) {
				case "UPDATE_SIGNATURE":
					executed = signatureService.signatureUpdatesAccounts(keyValue);
					System.out.println("UPDATE : " + executed);
					break;
				case "DELETE_SIGNATURE": {
					executed = signatureService.signatureDeleteAccounts(keyValue);
					System.out.println("DELETE : " + executed);
					break;
				}
				case "MARKETING_BRANDING_UPDATE": {
					executed = marketingService.marketingUpdatesAccounts(keyValue);
					System.out.println("UPDATE : " + executed);
					break;
				}

				case "UPDATE_DELEGATE": {
					executed = delegationService.delegationUpdatesAccounts(keyValue);
					System.out.println("UPDATE DELEGATE : " + executed);
					break;
				}
				case "DELETE_DELEGATE": {
					executed = delegationService.delegationDeleteAccounts(keyValue);
					System.out.println("DELETE DELEGATE : " + executed);
					break;
				}
				case "UPDATE_APPOINTMENTBOOKING": {
					executed = calenderService.updateAppointmentBookingSlot(keyValue);
					System.out.println("UPDATE CALENDER APPOINTMENTBOOKING : " + executed);
					break;
				}
				case "DELETE_APPOINTMENTBOOKING": {
					executed = calenderService.deleteAppointmentBookingSlot(keyValue);
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
			workFlowRepository.saveAndFlush(wrk.get());
		}
		return "EXECUTED";
	}


	
	
	//	@RequestMapping("DriveApprove/{WorkFlowId}/{token}")
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
