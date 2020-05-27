package cloudit.africa.GMS.GoogleApiServices;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;

import cloudit.africa.GMS.Model.DashBoardContactInfo;
import cloudit.africa.GMS.Model.myContact;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;

@Service
public class ContactApiServiceImpl implements ContactApiService {

	JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	Gmail gooogleGmailApi;

	@Autowired
	GMSAuthenticationManager myAuthenticationManager;

	@Override
	public DashBoardContactInfo getMyOtherContactList() {

		DashBoardContactInfo dashboardContactInfo = new DashBoardContactInfo();

		List<myContact> myContactList = new ArrayList<>();
		Integer numberOfContacts = 0;

		try {
			ContactsService myService = new ContactsService("GMS");
			myService.setOAuth2Credentials(myAuthenticationManager.getGoogleCredential());
			URL feedUrl;
			feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
			Query myQuery = new Query(feedUrl);
			myQuery.setMaxResults(1000);
			ContactFeed resultFeed = myService.query(myQuery, ContactFeed.class);
			numberOfContacts = resultFeed.getEntries().size();
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				for (com.google.gdata.data.extensions.Email email : resultFeed.getEntries().get(i)
						.getEmailAddresses()) {
					String[] emil = email.getAddress().split("@");
					myContactList.add(new myContact(email.getAddress(), emil[0]));
				}
			}

		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dashboardContactInfo.setMyContactList(myContactList);
		dashboardContactInfo.setNumberOfContacts(numberOfContacts);
		// TODO Auto-generated method stub
		return dashboardContactInfo;
	}

}
