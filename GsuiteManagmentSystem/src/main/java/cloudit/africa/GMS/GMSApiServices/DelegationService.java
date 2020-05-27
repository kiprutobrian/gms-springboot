package cloudit.africa.GMS.GMSApiServices;

import cloudit.africa.GMS.Model.KeyValue;

public interface DelegationService {

	boolean delegationUpdatesAccounts(KeyValue KeyValue);

	boolean delegationDeleteAccounts(KeyValue keyValue);

}
