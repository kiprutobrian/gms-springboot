package cloudit.africa.GMS.GMSApiServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.reseller.model.Customer;
import com.google.api.services.reseller.model.Subscription;
import com.google.api.services.reseller.model.Subscriptions;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.ResellerService;
import cloudit.africa.GMS.Repository.CompanyRepository;
import cloudit.africa.GMS.Utilities.ServiceResponse;

@Service
public class GmsCustomerServiceImpl implements GmsCustomerService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ResellerService resellerService;

	@Autowired
	DirectoryService directoryService;

	@Override
	public boolean updateCustomers() {
		// TODO Auto-generated method stub

		List<List<Subscription>> results = resellerService.getCustommers();
		for (int s = 0; s < results.size(); s++) {
			List<Subscription> resellerSubscriptions = results.get(s);
			updateCompanies(resellerSubscriptions);
		}
		return false;
	}

	@Override
	public List<Company> getAllCustomers() {
		// TODO Auto-generated method stub
		return companyRepository.findAll();
	}

	public void updateCompanies(List<Subscription> resellerSubscriptions) {

		System.out.println("=====================================");
		List<Company> compinesList = new ArrayList<Company>();
		for (int x = 0; x < resellerSubscriptions.size(); x++) {

			Subscription subscription = resellerSubscriptions.get(x);
			Customer customer = resellerService.getCustomer(subscription.getCustomerId());

			if (subscription.getSeats().getNumberOfSeats() != null) {
				System.out.println("SUCCESS  :  " + subscription.getSeats().getNumberOfSeats());
				System.out.println("MaximumNumberOfSeats  :  " + subscription.getSeats().getMaximumNumberOfSeats());

				Company company = new Company();
				cloudit.africa.GMS.Entity.Package packageSubscription = new cloudit.africa.GMS.Entity.Package();
				packageSubscription.setId(0);
				company.setCompanyId(customer.getCustomerId());
				company.setEmail(customer.getAlternateEmail());
				company.setPhoneNumber(customer.getPhoneNumber());
				company.setName(customer.getPostalAddress().getOrganizationName());
				company.setDomain(customer.getCustomerDomain());
				company.setPackageActive(false);
				company.setCustomerDomainVerified(customer.getCustomerDomainVerified());
				company.setLogo("");
				if (subscription.getSeats().getMaximumNumberOfSeats() != 50000) {
					company.setMaximumNumberOfSeats(null);
					company.setNumberOfSeats(subscription.getSeats().getMaximumNumberOfSeats());
				} else {
					company.setNumberOfSeats(subscription.getSeats().getNumberOfSeats());
					company.setMaximumNumberOfSeats(subscription.getSeats().getMaximumNumberOfSeats());
				}
				company.setUnderSupport(true);
				company.setSubscriptionPlan(subscription.getSubscriptionId());
				company.setResourcUrl(subscription.getResourceUiUrl());
				company.setLicensedNumberOfSeats(subscription.getSeats().getLicensedNumberOfSeats());
				company.setCountryCode(customer.getPostalAddress().getCountryCode());
				company.setLocality(customer.getPostalAddress().getLocality());
				company.setPostalCode(customer.getPostalAddress().getPostalCode());
				company.setRegion(customer.getPostalAddress().getRegion());
				company.setCreatedDate(new Date());
				company.setUpdateDate(new Date());
				company.setPackages(packageSubscription);
				compinesList.add(company);
			}

			System.out.println("=====================================");
		}

		companyRepository.saveAll(compinesList);
	}

}
