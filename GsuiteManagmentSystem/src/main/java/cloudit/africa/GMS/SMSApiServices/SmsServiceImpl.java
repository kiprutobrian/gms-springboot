package cloudit.africa.GMS.SMSApiServices;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsServiceImpl implements SmsService{

	 private final static String ACCOUNT_SID = "AC513ff74334a70344c9d50dcc0c3fb3a7";
	  private final static String AUTH_ID = "77ebff5d15ad30a616007d362c301fa7";

	  static {
	      Twilio.init(ACCOUNT_SID, AUTH_ID);
	   }
	  
	  @Override
	   public void sendmesage(String message,String toNumber) {
	      Message.creator(new PhoneNumber(toNumber), new PhoneNumber("+16696002283"),
	    		  message).create();
	   }
}
