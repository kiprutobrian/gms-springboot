package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.cloudresourcemanager.CloudResourceManager;
import com.google.api.services.cloudresourcemanager.model.Operation;
import com.google.api.services.cloudresourcemanager.model.Project;

import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class CloudResourceManagerServiceImpl implements CloudResourceManagerService{

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	CloudResourceManager gooogleCloudResourceManagerApi;
	
	
	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	public CloudResourceManager getCloudResourceManagerService() {
		gooogleCloudResourceManagerApi = new CloudResourceManager.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return gooogleCloudResourceManagerApi;
	}

	@Override
	public Operation CreateCloudResourceManagerProject() {
		 Project requestBody = new Project();
		 requestBody.setName("gsuitmangmentsystem2019");
		 requestBody.setProjectId("gms-456");
		 
		try {
			CloudResourceManager.Projects.Create request = getCloudResourceManagerService().projects().create(requestBody);
			
		    Operation response = request.execute();
		    // TODO: Change code below to process the `response` object:
		    System.out.println(response);
		    return response;
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
}
