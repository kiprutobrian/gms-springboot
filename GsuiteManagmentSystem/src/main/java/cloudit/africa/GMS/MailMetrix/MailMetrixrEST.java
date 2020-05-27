package cloudit.africa.GMS.MailMetrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.model.Message;

import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.MailMetrixService;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController
public class MailMetrixrEST {

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	MailMetrixService mailMetrixService;

	@RequestMapping("/GetMetrixData")
	public List<BarChartData>  getMailMaatrixView(HttpServletRequest request, Model model) {
		
		MetricModelData metricModelData = mailMetrixService.getAllMailCurrentYear();
		List<BarChartData> barChartDataList = new ArrayList<BarChartData>();

		Map<String, List<MessagesMetrix>> postsPerType = metricModelData.getAllmessage().stream()
				.collect(Collectors.groupingBy(MessagesMetrix::getMonthAndYear));

		for (String name : postsPerType.keySet()) {
			String key = name.toString();
			List<MessagesMetrix> value = postsPerType.get(name);
			System.out.println(key + " VALUE " + value);
			Integer response=0;
			for(int x=0;x<value.size();x++){
				response+=value.get(x).getReponseTime();
			}
			BarChartData barChartData = new BarChartData();
			barChartData.setMessagessize(value.size());
			barChartData.setMonth(Utilities.getMonthOnly(value.get(0).getTime().getTime()));
			barChartData.setYear(Utilities.getYearOnly(value.get(0).getTime().getTime()));
			barChartData.setResponseTime(response);
			barChartDataList.add(barChartData);
		}
		return barChartDataList;
	}

}
