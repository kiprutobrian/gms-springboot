package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.reseller.Reseller;
import com.google.api.services.reseller.model.Customer;
import com.google.api.services.reseller.model.Subscription;
import com.google.api.services.reseller.model.Subscriptions;

import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.ServiceResponse;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class ResellerServiceImpl implements ResellerService {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Reseller gooogleResellerApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;


	@Override
	public List<List<Subscription>> getCustommers() {
		// TODO Auto-generated method stub
		gooogleResellerApi = new Reseller.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		Subscriptions result = null;
		List<List<Subscription>> subscribersListPagination = new ArrayList<List<Subscription>>();
		try {
			result = gooogleResellerApi.subscriptions().list().setMaxResults(100L).setPageToken("test").execute();
			subscribersListPagination.add(result.getSubscriptions());
			System.out.println("SIZE" + result.getSubscriptions().size());
			System.out.println("Token" + result.getNextPageToken());
			while (!(Utilities.getEmptyNullStringValue(result.getNextPageToken()).equals("null"))) {
				System.out.println("PAGE TOKEN OLD" + result.getNextPageToken());
				result = gooogleResellerApi.subscriptions().list().setMaxResults(100L)
						.setPageToken(result.getNextPageToken()).execute();
				System.out.println("PAGE TOKEN NEW" + result.getNextPageToken());
				subscribersListPagination.add(result.getSubscriptions());
			}
			return subscribersListPagination;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subscribersListPagination;
	}

	@Override
	public ServiceResponse getCustommersNextPage(String nextPageToken) {
		// TODO Auto-generated method stub
		gooogleResellerApi = new Reseller.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		ServiceResponse serviceResponse = new ServiceResponse();
		Subscriptions result = null;
		List<Subscription> cd = null;
		try {
			result = gooogleResellerApi.subscriptions().list().setMaxResults(100L).setPageToken(nextPageToken)
					.execute();
			serviceResponse.setData(result);
			if (result.getNextPageToken().isEmpty()) {
				serviceResponse.setPresent(true);
			}
			return serviceResponse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serviceResponse;
	}

	@Override
	public Customer getCustomer(String customerId) {
		// TODO Auto-generated method stub

		gooogleResellerApi = new Reseller.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		Customer customer = null;
		try {
			customer = gooogleResellerApi.customers().get(customerId).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}
	
	
	@Override
	public Subscription getCustomerSubscritption(String customerId,String subscriptionId) {
		// TODO Auto-generated method stub

		gooogleResellerApi = new Reseller.Builder(new NetHttpTransport(), JSON_FACTORY, myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		Subscription customer = null;
		try {
			
			customer =gooogleResellerApi.subscriptions().get(customerId, subscriptionId).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}

	public List<Subscription> addClinetList(List<Subscription> result, String set) {
		List<Subscription> subscriptions = result;

		System.out.println("" + set);
		System.out.println("===================================================================");
		if (subscriptions == null || subscriptions.size() == 0) {
			System.out.println("No subscriptions found.");
		} else {
			System.out.println("Subscriptions:");
			for (Subscription sub : subscriptions) {
				System.out.printf("%s (%s, %s)\n", sub.getCustomerDomain(), sub.getCustomerId(), sub.getSkuId(),
						sub.getPlan().getPlanName());
			}
		}
		System.out.println("===================================================================");

		return subscriptions;
	}

}
