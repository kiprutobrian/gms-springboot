package cloudit.africa.GMS.GoogleApiServices;

import java.util.List;

import com.google.api.services.admin.datatransfer.model.ApplicationsListResponse;
import com.google.api.services.admin.datatransfer.model.DataTransfer;

public interface DataTransferService {

	ApplicationsListResponse getApplications();

	DataTransfer StartDataTransferMigration(Long applicationId, String oldOwnerUserId, String newOwnerUserId);

	List<DataTransfer> GetApplicationTransfers();

}
