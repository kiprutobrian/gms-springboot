package cloudit.africa.GMS.GMSApiServices;

import cloudit.africa.GMS.Model.KeyValue;

public interface MarketingService {

	boolean marketingUpdatesAccounts(KeyValue keyValue);

	boolean marketingRemoveFromAccounts(KeyValue keyValue);

}
