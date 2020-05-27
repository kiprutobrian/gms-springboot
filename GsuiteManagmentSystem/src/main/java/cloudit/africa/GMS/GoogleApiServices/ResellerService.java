package cloudit.africa.GMS.GoogleApiServices;

import java.util.List;

import com.google.api.services.reseller.model.Customer;
import com.google.api.services.reseller.model.Subscription;

import cloudit.africa.GMS.Utilities.ServiceResponse;

public interface ResellerService {

	List<List<Subscription>> getCustommers();

	Customer getCustomer(String customerId);

	ServiceResponse getCustommersNextPage(String string);

	Subscription getCustomerSubscritption(String customerId, String subscriptionId);

}
