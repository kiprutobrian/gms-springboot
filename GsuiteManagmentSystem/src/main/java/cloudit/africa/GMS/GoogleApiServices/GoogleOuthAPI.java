package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

public interface GoogleOuthAPI {

	Userinfoplus getUserInfoPluse();


	

}
