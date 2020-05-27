package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.datatransfer.DataTransfer;
import com.google.api.services.admin.datatransfer.DataTransfer.Applications;
import com.google.api.services.admin.datatransfer.model.Application;
import com.google.api.services.admin.datatransfer.model.ApplicationDataTransfer;
import com.google.api.services.admin.datatransfer.model.ApplicationTransferParam;
import com.google.api.services.admin.datatransfer.model.ApplicationsListResponse;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.gmail.Gmail;

import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;

@Service
public class DataTransferServiceImpl implements DataTransferService {

	@Autowired
	GMSAuthenticationManager authenticationManager;

	DataTransfer datatransferApi=null;

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	public DataTransfer getDataTransferService() {
		UserApp AuthenticatedUser = authenticationManager.getUserCredentials();
		return datatransferApi = new DataTransfer.Builder(new NetHttpTransport(), JSON_FACTORY,authenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
	}
	
	
	@Override
	public ApplicationsListResponse getApplications(){
		try {
			ApplicationsListResponse x=getDataTransferService().applications().list().execute();
			return x;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public com.google.api.services.admin.datatransfer.model.DataTransfer StartDataTransferMigration(Long applicationId,String oldOwnerUserId,String newOwnerUserId){
		com.google.api.services.admin.datatransfer.model.DataTransfer content=new com.google.api.services.admin.datatransfer.model.DataTransfer();
		content.setApplicationDataTransfers(applicationDataTransfersgetapplication(applicationId));
		content.setOldOwnerUserId(oldOwnerUserId);
		content.setNewOwnerUserId(newOwnerUserId);
		
		try {
			com.google.api.services.admin.datatransfer.model.DataTransfer transcation=getDataTransferService().transfers().insert(content).execute();
			return transcation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<ApplicationDataTransfer> applicationDataTransfersgetapplication(Long  applicationId){
		List<ApplicationDataTransfer> applicationDataTransferList=new ArrayList<ApplicationDataTransfer>();
		ApplicationDataTransfer applicationDataTransfer=new ApplicationDataTransfer();
		applicationDataTransfer.setApplicationId(applicationId);
		
		List<String> values=new ArrayList<>();
		values.add("PRIVATE");
		values.add("SHARED");
		java.util.List<ApplicationTransferParam> applicationTransferParams=new ArrayList<>();
		ApplicationTransferParam appl=new ApplicationTransferParam();
		appl.setKey("PRIVACY_LEVEL");
		appl.setValue(values);
		applicationTransferParams.add(appl);
		applicationDataTransfer.setApplicationTransferParams(applicationTransferParams);
		
		applicationDataTransferList.add(applicationDataTransfer);
		return applicationDataTransferList;
	}


	@Override
	public List<com.google.api.services.admin.datatransfer.model.DataTransfer> GetApplicationTransfers() {
		// TODO Auto-generated method stub
		
		try {
			return getDataTransferService().transfers().list().execute().getDataTransfers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
