package cloudit.africa.GMS.Controller.AdminSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.admin.directory.model.User;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.MarkerCheckers;
import cloudit.africa.GMS.Entity.Role;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.UserRole;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Repository.CheckerRepository;
import cloudit.africa.GMS.Repository.MarkerCheckersRepository;
import cloudit.africa.GMS.Repository.RoleRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.Repository.UserRoleRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.ServiceResponse;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class AdminSetting {
	
	@Autowired
	GlobalModelView globalModelView;
	
	@Autowired
	DirectoryService directoryService;

	@Autowired
	UserAppRepositiry userRepo;

	@Autowired
	UserRoleRepository userRoleRepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CheckerRepository checkerRepository;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	AdminSettingService adminSettingService;

	@Autowired
	MarkerCheckersRepository makerCheckerRepo;

	@RequestMapping("/AdminControl")
	public String adminconsoul(Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
	
		
		List<User> myOrgUsers = directoryService.getDomainUsers();
		model.addAttribute("users", myOrgUsers);

		
		List<Role> roleList = roleRepository.findAll();
		model.addAttribute("roleslist", roleList);

		return "gms-domainroleaccess";

	}

	@RequestMapping("/RoleSetting/{Id}")
	public String RoleSetting(Model model, @PathVariable("Id") Integer Id, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)
			return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
	
		String comapnyId = authenticatedUser.getCompany().getCompanyId();

		model.addAttribute("domainUsers", directoryService.getDomainUsers());
		
		List<User> adminList = new ArrayList<User>();
		List<com.google.api.services.admin.directory.model.User> myOrgUsers = directoryService.getDomainUsers();

		for (int admin = 0; admin < myOrgUsers.size(); admin++) {
			User adminUser = myOrgUsers.get(admin);
			if (adminUser.getIsAdmin()) {
				adminList.add(adminUser);
			}
		}
		model.addAttribute("users", adminList);

		Role role = new Role();
		role.setId(Id);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Id);

		Optional<MarkerCheckers> markerCheckerServices = makerCheckerRepo.findById("" + Id);
		Optional<List<Checker>> checkerList = checkerRepository.findByCompany(authenticatedUser.getCompany());

		ServiceResponse serveresponse = null;
		if (checkerList.isPresent()) {
			serveresponse = Utilities.getChecker(checkerList.get(), "" + Id);
		} else {
			serveresponse = new ServiceResponse();
			serveresponse.setPresent(false);
		}

		model.addAttribute("roles", roleRepository.findAllById(roleIds));
		Optional<List<UserRole>> users = userRoleRepo.findByRole(Id, comapnyId);
		if (users.isPresent()) {
			model.addAttribute("roleAccess", users.get());
		}
		if (serveresponse.isPresent()) {
			Checker checker = (Checker) serveresponse.getData();
			model.addAttribute("checker", checker);
			model.addAttribute("isActive", checker.isActive());
			model.addAttribute("checkerId", checker.getId());
		}
		if (markerCheckerServices.isPresent()) {
			model.addAttribute("title", markerCheckerServices.get().getName());
		}
		model.addAttribute("potentialCheckers", userRepo.findByCompany(authenticatedUser.getCompany()));
		model.addAttribute("RoleId", Id);

		return "gms-domainroleaccesssetting";
	}

	@RequestMapping("/RevockRoleAccess/{Id}/{roleId}")
	public String RevockRoleAccess(Model model, @PathVariable("Id") String Id, @PathVariable("roleId") String roleId,
			HttpServletRequest request) {
		UserRole userRole = new UserRole();
		userRole.setId(Integer.parseInt(Id));
		adminSettingService.revockRoleAccess(userRole, roleId);

		return "redirect:/RoleSetting/" + roleId;
	}

}
