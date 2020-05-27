package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class GoogleOuth2Impl implements GoogleOuthAPI {

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Override
	public Userinfoplus getUserInfoPluse() {
		Userinfoplus userinfo = null;
		Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(),myAuthenticationManager.getGoogleCredential()).setApplicationName("Oauth2").build();
		try {
			userinfo = oauth2.userinfo().get().execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userinfo;
	}
}
