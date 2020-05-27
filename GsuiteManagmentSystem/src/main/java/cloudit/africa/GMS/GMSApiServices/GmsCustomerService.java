package cloudit.africa.GMS.GMSApiServices;

import java.util.List;

import com.google.api.services.reseller.model.Subscription;

import cloudit.africa.GMS.Entity.Company;

public interface GmsCustomerService {

	boolean updateCustomers();

	List<Company> getAllCustomers();

}
