package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.groupssettings.Groupssettings;
import com.google.api.services.groupssettings.model.Groups;

import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class GroupssettingsServiceImpl implements GroupssettingsService {

	
	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Groupssettings googleGroupssettingsApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	
	public Groupssettings getGroupssettingss() {
		googleGroupssettingsApi = new Groupssettings.Builder(new NetHttpTransport(), JSON_FACTORY,
				myAuthenticationManager.getGoogleCredential()).setApplicationName("GSUIT").build();
		return googleGroupssettingsApi;
	}


	@Override
	public void getGroups() {
		// TODO Auto-generated method stub
		try {
			
			Groups group=getGroupssettingss().groups().get("groupssettingsService@dev.businesscom.dk").execute();
			
			group.toString();
			System.out.println("GROUPS			================="+group.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
