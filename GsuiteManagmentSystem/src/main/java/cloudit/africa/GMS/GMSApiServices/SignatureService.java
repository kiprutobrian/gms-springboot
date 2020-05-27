package cloudit.africa.GMS.GMSApiServices;

import cloudit.africa.GMS.Model.KeyValue;

public interface SignatureService {

	boolean signatureUpdatesAccounts(KeyValue KeyValue);

	boolean signatureDeleteAccounts(KeyValue keyValue);

}
