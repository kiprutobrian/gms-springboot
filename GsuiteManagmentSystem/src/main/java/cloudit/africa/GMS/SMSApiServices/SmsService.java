package cloudit.africa.GMS.SMSApiServices;

import org.springframework.stereotype.Service;


public interface SmsService {

	void sendmesage(String message, String toNumber);

}
