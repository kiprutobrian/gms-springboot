package cloudit.africa.GMS.SecurityConfigurations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.gmail.model.Delegate;

import cloudit.africa.GMS.Model.TemplateType;
import cloudit.africa.GMS.Utilities.Utilities;

@Configuration
public class ModelDataView {

	public Model getModelData(HttpServletRequest request, Model model, User user, List<Delegate> delegateList,List<User> accounts,TemplateType templateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

		String JobPosition = Utilities.getValues(user.getOrganizations(), "title");
		String department = Utilities.getValues(user.getOrganizations(), "department");
		String phonenumber = Utilities.getValue(user.getPhones());
		String domain = user.getPrimaryEmail().split("@")[1];

		map.put("accountId", user.getId());
		map.put("familyName", user.getName().getFamilyName());
		map.put("givenName", user.getName().getGivenName());
		map.put("emailName", user.getPrimaryEmail());
		map.put("aliasesList", user.getAliases());
		map.put("delegateList", delegateList);
		map.put("phonenumber", phonenumber);
		map.put("jobtitle", JobPosition);
		map.put("department", department);
		map.put("potentailDelegate", accounts);
		map.put("TemplateType", templateType);
		
		model.addAttribute("domain", domain);

		map.put("_csrf.token", token.getToken());
		map.put("_csrf.parameterName", token.getParameterName());
		map.put("_csrf_header", token.getHeaderName());

		return model.addAllAttributes(map);
	}

}
