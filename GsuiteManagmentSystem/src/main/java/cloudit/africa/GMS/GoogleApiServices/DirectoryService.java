package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.util.List;

import com.google.api.services.admin.directory.model.Alias;
import com.google.api.services.admin.directory.model.Customer;
import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.User;

public interface DirectoryService {

	
//	Group createGroup();
	List<User> getDomainUsers();
	boolean getDomainUserRole(String userId);
	User getDomainUser(String emailAddress);
	boolean updateCustomerInfo(String customerId);
	String getcustomer(String userId);
	boolean getCustomerDirectory(String customerId);
	List<User> getDomainUsersUsingServiceAccount(String myEmail, String Adminemail);
	User getDomainUserUsingServiceAccount(String myEmail, String Adminemail, String emailAddress);
	User createUserOnDomain(User user);
	Alias AddAliasesToAcount(String account, String aliasAccount);
	void updateUserPassword(User user, String password) throws IOException;
	User getDomainUserFirst(String emailAddress);
	
	List<User> getDomainUsers(String domainserviceAccount);
	
}
