package cloudit.africa.GMS.GMSApiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.model.ListThreadsResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.MailMetrix.MailMetrix;
import cloudit.africa.GMS.MailMetrix.MessagesMetrix;
import cloudit.africa.GMS.MailMetrix.MetricModelData;
import cloudit.africa.GMS.Utilities.Utilities;

@Service
public class MailMetrixServiceImpl implements MailMetrixService {

	@Autowired
	DirectoryService directoryService;

	@Autowired
	GmailApiServices gmailService;

	@Override
	public MetricModelData getAllMailCurrentYear() {

		String account = "mulunda@dev.businesscom.dk";

		List<MessagesMetrix> messagesMetrix = new ArrayList<MessagesMetrix>();
		List<Message> allmyMessages=new ArrayList<>();
		
		MetricModelData metricModelData=new MetricModelData();
		
		Integer time = 0;
		Integer messages = 0;

		List<com.google.api.services.gmail.model.Thread> threadList = gmailService.getThread(account);
		try {
			for (int a = 0; a < threadList.size(); a++) {
				List<Message> messesList = gmailService.getThreadMessages(account, threadList.get(a).getId());
				allmyMessages.addAll(messesList);
				messages += messesList.size();
				for (int mess = 0; mess < messesList.size(); mess++) {
					Message messag = messesList.get(mess);
					if (mess <= (messesList.size() - 1)) {
						System.out.println("MESSAGE=======" + messag.toString());
						Message messagSecond = null;
						if (mess == messesList.size() - 1) {
							messagSecond = messesList.get(mess);
						} else {
							messagSecond = messesList.get(mess + 1);
						}
						Date dateSent = new Date(messag.getInternalDate());
						Date dateSentSecond = new Date(messagSecond.getInternalDate());
						String sentby = getSentBy(messag.getPayload().getHeaders());
						String sentbySecond = getSentBy(messagSecond.getPayload().getHeaders());
						MessagesMetrix messmetrix = new MessagesMetrix();
						messmetrix.setSender(sentby);
						messmetrix.setTime(dateSent);
						Integer reponseTime=0;
						if (!sentby.equals(sentbySecond)) {
							reponseTime=Integer.parseInt((Utilities.getMinutesDiff(dateSent, dateSentSecond)).replace(",", ""));
							time += reponseTime;
						}
						messmetrix.setReponseTime(reponseTime);
						messagesMetrix.add(messmetrix);
					}
				}
			}
			System.out.println(messages);
			System.out.println(time);
			System.out.println(messagesMetrix.size());
			
			metricModelData.setAllmessage(messagesMetrix);
			metricModelData.setResponseTime(time);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metricModelData;
	}

	public String getSentBy(List<MessagePartHeader> headers) {
		for (int a = 0; a < headers.size(); a++) {
			if (headers.get(a).getName().equals("From")) {
				return headers.get(a).values().toString();
			}
		}
		return null;
	}

}
