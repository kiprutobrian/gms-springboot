package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.Role;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.UserRole;
import cloudit.africa.GMS.Model.RoleAccess;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.Repository.UserRoleRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserAppRepositiry userRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public UserApp addUser(User user) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		UserApp userApp = new UserApp();
		userApp.setId(user.getId());
		userApp.setFirstName(user.getName().getGivenName());
		userApp.setLastName(user.getName().getFamilyName());
		userApp.setUsername(user.getName().getFullName());
		userApp.setEmail(user.getPrimaryEmail());
		userApp.setActive(false);
		userApp.setUpdatedAt(new Date());
		userApp.setCompany(authenticatedUser.getCompany());
		userApp.setServiceEmailAdress(authenticatedUser.getEmail());
		UserApp updatedUserApp = userRepo.saveAndFlush(userApp);

		return updatedUserApp;
	}

	@Override
	public UserApp superAdminRegestration(Company company) {

		Userinfoplus userinfo = null;
		Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(),
				authenticationManager.getGoogleCredential()).setApplicationName("Oauth2").build();
		UserApp userApp = new UserApp();

		try {
			userinfo = oauth2.userinfo().get().execute();
			Role role = new Role();
			role.setId(0);
			List<Role> rolesAcess = new ArrayList<Role>();
			UserRole userRole = new UserRole();
			userApp.setId(userinfo.getId());
			userApp.setFirstName(userinfo.getGivenName());
			userApp.setLastName(userinfo.getFamilyName());
			userApp.setUsername(userinfo.getName());
			userApp.setImageUrl(userinfo.getPicture());
			userApp.setEmail(userinfo.getEmail());
			userApp.setUpdatedAt(new Date());
			userApp.setRoles(rolesAcess);
			userApp.setCompany(company);
			userApp.setSuperAdmin(true);
			userApp.setActive(true);
			userApp.setServiceEmailAdress(userinfo.getEmail());
			UserApp userResult = userRepo.saveAndFlush(userApp);
			userRole.setUserApp(userResult);
			userRole.setRole(role);
			Optional<UserRole> OptionUserRole = userRoleRepository.findByUserAppAndRole(userResult, role);
			if (OptionUserRole.isPresent()) {
			} else {
				userRoleRepository.saveAndFlush(userRole);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userApp;
	}

}
