package cloudit.africa.GMS.Controller.AdminSetting;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.GMSApiServices.GmsCustomerService;
import cloudit.africa.GMS.Repository.CompanyRepository;

@Controller
public class BusinessComAdmin {

	@Autowired
	GmsCustomerService gmsCustomerService;

	@Autowired
	CompanyRepository companyRepository;

	@RequestMapping("BUSINESSCOMAFRICA")
	public String getreselllers(Model model) {
		List<Company> compinesList = gmsCustomerService.getAllCustomers();
		model.addAttribute("customers", compinesList);
		return "businesscom_admin";
	}

	@RequestMapping("UPDATECLIENTS")
	public String getrUpdateResellerClientList() {
		gmsCustomerService.updateCustomers();
		return "redirect:/BUSINESSCOMAFRICA";
	}

	@RequestMapping("/UpdateCustomer/{companyId}")
	public String RoleSetting(Model model, @PathVariable("companyId") String companyId, HttpServletRequest request) {
		Optional<Company> company = companyRepository.findById(companyId);
		
		return "dashboard";

	}

}
