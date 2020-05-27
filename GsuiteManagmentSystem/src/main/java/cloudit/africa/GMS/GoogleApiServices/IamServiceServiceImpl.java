package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.cloudresourcemanager.CloudResourceManager;
import com.google.api.services.cloudresourcemanager.model.Operation;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.CreateServiceAccountRequest;
import com.google.api.services.iam.v1.model.ServiceAccount;

import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class IamServiceServiceImpl implements IamServiceService {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Iam gooogleIamApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	public Iam getIamService() {
		gooogleIamApi = new Iam.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return gooogleIamApi;
	}

	@Override
	public ServiceAccount CreateserviceAccounts() {
		String name = "projects/gsuitmangmentsystem2019"; // TODO: Update placeholder value.

		// TODO: Assign values to desired fields of `requestBody`:
		CreateServiceAccountRequest requestBody = new CreateServiceAccountRequest();
		requestBody.setAccountId("gsuitservice");

		Iam iamService = getIamService();
		Iam.Projects.ServiceAccounts.Create request;
		try {
			request = iamService.projects().serviceAccounts().create(name, requestBody);
			ServiceAccount response = request.execute();

			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
