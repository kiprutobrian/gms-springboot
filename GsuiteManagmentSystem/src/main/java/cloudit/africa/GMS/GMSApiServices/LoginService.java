package cloudit.africa.GMS.GMSApiServices;

import com.google.api.services.admin.directory.model.User;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.UserApp;

public interface LoginService {

	UserApp addUser(User user);

	UserApp superAdminRegestration(Company company);

}
