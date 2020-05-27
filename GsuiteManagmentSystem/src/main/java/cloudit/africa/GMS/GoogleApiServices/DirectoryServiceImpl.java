package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.Directory.Groups.Aliases;
import com.google.api.services.admin.directory.model.Alias;
import com.google.api.services.admin.directory.model.Customer;
import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Role;
import com.google.api.services.admin.directory.model.RoleAssignments;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;

import ch.qos.logback.classic.pattern.Util;
import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Repository.CompanyRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SMSApiServices.SmsService;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class DirectoryServiceImpl implements DirectoryService {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Directory gooogleDirectoryApi = null;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	UserAppRepositiry userRepository;

	@Autowired
	SmsService smsService;

	public Directory getDirectoryService() {

		UserApp AuthenticatedUser = myAuthenticationManager.getUserCredentials();
		try {
			return gooogleDirectoryApi = ServiceAccount.getDirectoryServices(AuthenticatedUser.getServiceEmailAdress());
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gooogleDirectoryApi;
	}

	@Override
	public User createUserOnDomain(User user) {

		gooogleDirectoryApi = getDirectoryService();

		try {
			User succeessMessage = gooogleDirectoryApi.users().insert(user).execute();
			return succeessMessage;
		} catch (IOException e) {
			return null;
		}

	}

	@Override
	public List<User> getDomainUsers() {
		// TODO Auto-generated method stub
		gooogleDirectoryApi = getDirectoryService();
		com.google.api.services.admin.directory.model.Users result;

		try {

			List<User> usersList = new ArrayList<User>();
			com.google.api.services.admin.directory.Directory.Users.List ul = gooogleDirectoryApi.users().list()
					.setCustomer("my_customer").setMaxResults(500);
			do {
				Users curPage = ul.execute();
				usersList.addAll(curPage.getUsers());
				ul.setPageToken(curPage.getNextPageToken());
			} while (ul.getPageToken() != null && ul.getPageToken().length() > 0);

			return usersList;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getDomainUserFirst(String userkey) {
		gooogleDirectoryApi = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		try {
			com.google.api.services.admin.directory.model.User result = gooogleDirectoryApi.users().get(userkey)
					.execute();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getDomainUser(String keyValue) {
		// TODO Auto-generated method stub
		gooogleDirectoryApi = getDirectoryService();
		try {
			com.google.api.services.admin.directory.model.User result = gooogleDirectoryApi.users().get(keyValue)
					.execute();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateUserPassword(User user, String password) throws IOException {
		try {
			User updateUser = updatePassword(user, password);
			final Directory.Users.Update updateRequest = getDirectoryService().users().update(user.getPrimaryEmail(),
					updateUser);
			updateRequest.execute();

			String number = Utilities.getValue(updateUser.getPhones());
			if (number != null) {
				if (number.substring(0).equals("0")) {
					number = "+254" + number.substring(1, (number.length() - 1));
//					smsService.sendmesage("Password Rest new password" + password, number);
				}
			} else {
				number = "+254738643284";
//				smsService.sendmesage("Password Rest new password" + password, number);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Alias AddAliasesToAcount(String account, String aliasAccount) {
		// TODO Auto-generated method stub
		gooogleDirectoryApi = getDirectoryService();
		try {
			Alias content = new Alias();
			content.setAlias(aliasAccount);
			Alias result = gooogleDirectoryApi.users().aliases().insert(account, content).execute();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getDomainUsersUsingServiceAccount(String myEmail, String Adminemail) {
		// TODO Auto-generated method stub

		try {

			gooogleDirectoryApi = getDirectoryService();
			com.google.api.services.admin.directory.model.Users result = gooogleDirectoryApi.users().list()
					.setCustomer("my_customer").setOrderBy("email").execute();
			return result.getUsers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getDomainUserUsingServiceAccount(String myEmail, String Adminemail, String emailAddress) {
		// TODO Auto-generated method stub

		try {
			gooogleDirectoryApi = getDirectoryService();
			com.google.api.services.admin.directory.model.User result = gooogleDirectoryApi.users().get(emailAddress)
					.execute();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean getDomainUserRole(String userId) {
		// TODO Auto-generated method stub

		gooogleDirectoryApi = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();

		boolean isAdmin = false;
		try {
			com.google.api.services.admin.directory.model.Roles role = gooogleDirectoryApi.roles().list("my_customer")
					.execute();
			List<Role> rolesList = role.getItems();
			System.out.println("RoleList  =====  " + rolesList);
			for (int a = 0; a < rolesList.size(); a++) {
				if (Utilities.getBoolean(rolesList.get(a).getIsSuperAdminRole())) {
					RoleAssignments roleAssignmentList = gooogleDirectoryApi.roleAssignments().list("my_customer")
							.execute();
					for (int c = 0; c < roleAssignmentList.getItems().size(); c++) {
						if ((roleAssignmentList.getItems().get(c).getAssignedTo().equals(userId))) {
							System.out.println(
									"ROLE ASSIGNED TO  =====  " + roleAssignmentList.getItems().get(c).getAssignedTo());
							isAdmin = true;
							break;
						}
					}
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return isAdmin;
		}

		return isAdmin;
	}

	@Override
	public String getcustomer(String userId) {

		gooogleDirectoryApi = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();

		String customerId = null;
		try {
			customerId = gooogleDirectoryApi.users().get(userId).execute().getCustomerId();

			return customerId;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerId;

	}

	@Override
	public boolean updateCustomerInfo(String customerId) {

		gooogleDirectoryApi = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();

		try {
			Customer customer = gooogleDirectoryApi.customers().get("my_customer").execute();
			Company company = new Company();
			cloudit.africa.GMS.Entity.Package packageSubscription = new cloudit.africa.GMS.Entity.Package();
			packageSubscription.setId(101);
			company.setCompanyId(customer.getId());
			company.setEmail(customer.getAlternateEmail());
			company.setPhoneNumber(customer.getPhoneNumber());
			company.setName(customer.getPostalAddress().getOrganizationName());
			company.setDomain(customer.getCustomerDomain());
			company.setPackageActive(false);
			company.setCustomerDomainVerified(true);
			company.setLogo("");
			company.setCountryCode(customer.getPostalAddress().getCountryCode());
			company.setLocality(customer.getPostalAddress().getLocality());
			company.setPostalCode(customer.getPostalAddress().getPostalCode());
			company.setRegion(customer.getPostalAddress().getRegion());
			company.setCreatedDate(new Date());
			company.setUpdateDate(new Date());
			company.setPackages(packageSubscription);

			if (!companyRepository.findById(customer.getId()).isPresent()) {
				companyRepository.saveAndFlush(company);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean getCustomerDirectory(String customerId) {

		gooogleDirectoryApi = new Directory.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		try {
			com.google.api.services.admin.directory.model.Users result = gooogleDirectoryApi.users().list()
					.setCustomer("C04dso38j").setOrderBy("email").execute();

			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public User updatePassword(final User user, final String password) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("MD5"); // I've been warned that this is not thread-safe
		final byte[] digested = md.digest(password.getBytes("UTF-8"));
		final String newHashword = DatatypeConverter.printHexBinary(digested);
		return user.setHashFunction("MD5").setPassword(newHashword); // only accepts MD5, SHA-1, or CRYPT
	}

	@Override
	public List<User> getDomainUsers(String domainserviceAccount) {
		// TODO Auto-generated method stub
	
		try {
			gooogleDirectoryApi =ServiceAccount.getDirectoryServices(domainserviceAccount);
			List<User> usersList = new ArrayList<User>();
			com.google.api.services.admin.directory.Directory.Users.List ul = gooogleDirectoryApi.users().list()
					.setCustomer("my_customer").setMaxResults(500);
			do {
				Users curPage = ul.execute();
				usersList.addAll(curPage.getUsers());
				ul.setPageToken(curPage.getNextPageToken());
			} while (ul.getPageToken() != null && ul.getPageToken().length() > 0);

			return usersList;

		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
