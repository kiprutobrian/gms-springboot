package cloudit.africa.GMS.Controller.MarkerCheckerApprovals;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.WorkFlow;
import cloudit.africa.GMS.Repository.WorkFlowRepository;

@Controller
public class SignatureApprovals {

	@Autowired
	WorkFlowRepository workFlowRepository;
	
	@RequestMapping("/ApprovalDashBoard")
	public String  dashboard(Model model){
		
//		List<WorkFlow> findByApproverContaining(String title);
		
		UserApp userApp=new UserApp();
		Company company=new Company();
		company.setCompanyId("C018tp883");
		userApp.setCompany(company);
		
		Optional<List<WorkFlow>> workflowlist =workFlowRepository.findByCompany(company);
		
//		List<WorkFlow> workflowlist=workFlowRepository.findByUserApp(userApp);
		System.out.println(workflowlist.get().size());
		
		model.addAttribute("workFlow", workflowlist.get());
		
//		workFlowRepository.findByTitleContaining();
		
		return "approval_logs";
	}
	
}
