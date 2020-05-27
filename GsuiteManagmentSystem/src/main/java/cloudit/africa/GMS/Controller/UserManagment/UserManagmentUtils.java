package cloudit.africa.GMS.Controller.UserManagment;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.gmail.model.Delegate;

import cloudit.africa.GMS.Model.TemplateType;

public class UserManagmentUtils {

	public List<User> getAccountsAvailableForDelegation(List<User> domainUsers, List<Delegate> deligateList) {
		if (deligateList != null && deligateList.size() != 0) {
			for (int a = 0; a < domainUsers.size(); a++) {
				for (int b = 0; b < deligateList.size(); b++) {
					if (domainUsers.get(a).getPrimaryEmail().equals(deligateList.get(b).getDelegateEmail())) {
						domainUsers.remove(domainUsers.get(a));
						continue;
					}
				}
			}
		}
		return domainUsers;
	}

	public TemplateType getSignatureMaketingCalenderTemplate(String signature) {
		TemplateType templateType = new TemplateType();
		String calendertemplate="";
		String marketing ="";
		String sig ="";
		
		String sigtemp[] = signature.split("<p style=\"display:none\">");
		if (sigtemp.length > 1) {
			sig = sigtemp[0].replace("marketingbranding</p>", "").replace("appointmentstart</p>", "");
			
			for (int a = 1; a < sigtemp.length; a++) {
				boolean iscalender = sigtemp[a].contains("appointmentstart</p>");
				boolean ismarketing = sigtemp[a].contains("marketingbranding</p>");
				if (iscalender) {
					calendertemplate = sigtemp[a].replace("marketingbranding</p>", "")
							.replace("appointmentstart</p>", "");
					
				}
				if (ismarketing) {
					marketing = sigtemp[a].replace("marketingbranding</p>", "").replace("appointmentstart</p>",
							"");
					
				}
			}
		}
		
		if(sig.length()==0 && calendertemplate.length()==0 && marketing.length()==0)sig=""+signature;
		templateType.setSignature(sig);
		templateType.setCalender(calendertemplate);
		templateType.setMarketing(marketing);

		return templateType;
	}
}
