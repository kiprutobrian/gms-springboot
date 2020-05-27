package cloudit.africa.GMS.GMSApiServices;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.admin.directory.model.User;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.MarkerCheckers;
import cloudit.africa.GMS.Entity.Role;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.UserRole;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.Model.Checkers;
import cloudit.africa.GMS.Model.RoleAccess;
import cloudit.africa.GMS.Repository.CheckerRepository;
import cloudit.africa.GMS.Repository.RoleRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.Repository.UserRoleRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.ServiceResponse;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class AdminSettingServiceImpl implements AdminSettingService {

	@Autowired
	DirectoryService directoryService;

	@Autowired
	UserAppRepositiry userRepo;

	@Autowired
	LoginService loginService;

	@Autowired
	UserRoleRepository userRoleRepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	CheckerRepository checkerRepository;

	@Override
	public boolean addRoleAccess(RoleAccess roleAccess) {
		// TODO Auto-generated method stub
		boolean isPresent = false;
		String emailAddress = roleAccess.getEmailAdress();
		String roleId = roleAccess.getRoleId();
		Role role = new Role();
		role.setId(Integer.parseInt(roleId));

		UserApp user = userRepo.findByEmail(emailAddress);
		
		try {
			if (user.getId() != null) {
				user.setActive(true);
				isPresent = true;
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		if (isPresent) {
			user.setActive(true);
			
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUserApp(user);
			userRepo.save(user);
			userRoleRepo.saveAndFlush(userRole);
		} else {
			User selectedUser = directoryService.getDomainUser(emailAddress);
			user = loginService.addUser(selectedUser);
			user.setActive(true);
			
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUserApp(user);
			userRoleRepo.saveAndFlush(userRole);
		}

		return true;
	}

	@Override
	public boolean AddMarkerChecker(Checkers checkers) {
		// TODO Auto-generated method stub

		UserApp userapp = authenticationManager.getUserCredentials();
		UserApp userApp = userRepo.findByEmail(checkers.getCheckerEmailAddress());

		Optional<List<Checker>> cherkerList = checkerRepository.findByCompany(userApp.getCompany());

		ServiceResponse serviceResponse = null;
		if (cherkerList.isPresent()) {
			serviceResponse = Utilities.getCheckerExistance(cherkerList.get(), checkers, userApp);
		} else {
			serviceResponse = new ServiceResponse();
			serviceResponse.setPresent(false);
		}

		Checker cherker = null;
		if (serviceResponse.isPresent()) {
			cherker = (Checker) serviceResponse.getData();
			cherker.setChecker(userApp);
			checkerRepository.saveAndFlush(cherker);
		} else {
			UUID uuid = UUID.randomUUID();
			MarkerCheckers marChecker = new MarkerCheckers();
			Checker newChecker = new Checker();
			marChecker.setId(checkers.getId());
			newChecker.setId(uuid.toString());
			newChecker.setMakerCheckers(marChecker);
			newChecker.setCompany(userapp.getCompany());
			newChecker.setChecker(userApp);
			checkerRepository.saveAndFlush(newChecker);
		}

		return true;
	}

	@Override
	public boolean updateActiveState(Checkers checkers) {
		// TODO Auto-generated method stub
		System.out.println(" CHECKER ID " + checkers.getId());
		
		Optional<Checker> cherker = null;
		try {
			cherker = checkerRepository.findByMakerCheckersCompany(checkers.getId(), authenticationManager.getUserCredentials().getCompany().getCompanyId());
		} catch (Exception ex) {
		}

		if (cherker.isPresent()) {
			cherker.get().setActive(checkers.isActive());
			checkerRepository.saveAndFlush(cherker.get());
		}

		return false;
	}

	@Override
	public boolean revockRoleAccess(UserRole userRole, String roleId) {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setId(Integer.parseInt(roleId));
		userRole.setRole(role);
		userRoleRepo.delete(userRole);

		return false;
	}

	@Override
	public Checker getChecker(String MakerCheckerId) {
		// TODO Auto-generated method stub
		Checker checker = new Checker();
		Optional<List<Checker>> checkers = checkerRepository
				.findByCompany(authenticationManager.getUserCredentials().getCompany());
		if (checkers.isPresent()) {
			for (int p = 0; p < checkers.get().size(); p++) {
				if (checkers.get().get(p).getMakerCheckers().getId().equals(MakerCheckerId)) {
					checker = checkers.get().get(p);
					return checker;
				}
			}
		}
		return checker;
	}

}
