package cloudit.africa.GMS.Controller.Signature;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.SendAs;

import cloudit.africa.GMS.Controller.ModelViews.GlobalModelView;
import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.Services;
import cloudit.africa.GMS.Entity.SignatureTemplate;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.GeneralDelegation;
import cloudit.africa.GMS.Model.Signature;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;
import cloudit.africa.GMS.Utilities.Config.PageRedirection;

@Controller
public class SignatureView {

	@Autowired
	UserAppRepositiry usrRepo;

	@Autowired
	SignatureRepository signaterRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GmailApiServices gmailApiServices;

	@Autowired
	DirectoryService directoryServies;
	
	@Autowired
	GlobalModelView globalModelView;


	@RequestMapping("SignatureSetting")
	public String AccountDeligation(Model model, HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);

		List<User> myOrgUsers = directoryServies.getDomainUsers();
		List<SignatureTemplate> signatureTemplatesList = signaterRepo.findByCompany(authenticatedUser.getCompany());
		model.addAttribute("users", myOrgUsers);
		model.addAttribute("mysignature", signatureTemplatesList);

		return "gms-signatureuerslist";
	}

	@RequestMapping("createSignatureTemplate")
	public String getSignatureLayout(Model model,HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		
		List<SignatureTemplate> signatureTemplatesList = signaterRepo.findByCompany(authenticatedUser.getCompany());
		Collections.reverse(signatureTemplatesList);
		model.addAttribute("mysignature", signatureTemplatesList);
		model.addAttribute("Signature", new Signature());
		model.addAttribute("buttontype", "submit");

		return "gms-signaturecreate";
	}

	@RequestMapping("/SignatureGallary")
	public String SignatureGalary(Model model,HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		
		
		List<SignatureTemplate> signatureTemplatesList = signaterRepo.findByCompany(authenticatedUser.getCompany());
		Collections.reverse(signatureTemplatesList);
		model.addAttribute("mysignature", signatureTemplatesList);
		model.addAttribute("buttontype", "submit");

		return "gms_template_signature";
	}

	// Generic function to split a list into two sublists in Java
	public static <T> List[] split(List<T> list) {
		// get size of the list
		int size = list.size();

		// construct new list from the returned view by list.subList() method
		List<T> first = new ArrayList<>(list.subList(0, (size + 1) / 2));
		List<T> second = new ArrayList<>(list.subList((size + 1) / 2, size));

		// return an List array to accommodate both lists
		return new List[] { first, second };
	}

	@RequestMapping("SetSignature/{templateId}")
	public String setSignatureForAll(@PathVariable("templateId") String templateId, Model model)
			throws GeneralSecurityException, IOException, URISyntaxException {

		UserApp user = authenticationManager.getUserCredentials();
		List<UserApp> userList = usrRepo.findByCompany(user.getCompany());

		String signatureBody;
		SignatureTemplate signatureTemplateSelected = signaterRepo.findById(Integer.parseInt(templateId)).get();
		signatureBody = signatureTemplateSelected.getSignatureBody();

		for (int a = 0; a < userList.size(); a++) {

			Gmail signatureAccountUpdate = ServiceAccount.getGmailService(userList.get(a).getEmail());
			SendAs sendAs = new SendAs();

			String finalSignature = (signatureBody.replaceAll("FirstName", userList.get(a).getFirstName()))
					.replaceAll("LastName", userList.get(a).getLastName())
					.replaceAll("PhoneNumber", Utilities.getEmptyNullStringValue(userList.get(a).getPhoneNumber()))
					.replaceAll("emailAddress", userList.get(a).getEmail());
			System.out.println("" + finalSignature);
			sendAs.setSignature(finalSignature);
			System.out.println("Email Address " + userList.get(a).getEmail());
			sendAs.setSendAsEmail(userList.get(a).getEmail());

			signatureAccountUpdate.users().settings().sendAs()
					.patch(userList.get(a).getEmail(), userList.get(a).getEmail(), sendAs).execute();
		}
		SignatureTemplate oldsignature = signaterRepo.findByIsActive(true);
		oldsignature.setIsActive(false);

		signatureTemplateSelected.setIsActive(true);
		List<SignatureTemplate> signatureTemplateUpdate = new ArrayList<>();
		signatureTemplateUpdate.add(oldsignature);
		signatureTemplateUpdate.add(signatureTemplateSelected);
		signaterRepo.saveAll(signatureTemplateUpdate);

		return "redirect:/SignatureSetting";

	}

	@RequestMapping("/UpdateSignature")
	public String postSelectedUpdateSignature(Model model, final HttpServletRequest request) {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		if (authenticatedUser == null)return PageRedirection.AUTHENRICATED_USER_NULL;
		globalModelView.getViewStaticData(model, authenticatedUser, request);
		
		Company compny = authenticatedUser.getCompany();

		List<UserApp> myOrgUsers = usrRepo.findByCompany(compny);
		List<SignatureTemplate> signatureTemplatesList = signaterRepo.findByCompany(compny);

		model.addAttribute("mysignature", signatureTemplatesList);
		model.addAttribute("Signature", new Signature());
		model.addAttribute("GeneralDelegation", new GeneralDelegation());

		model.addAttribute("users", myOrgUsers);

		return "signatureUpdate";
	}

	@RequestMapping("UpdateSignature/{userId}")
	public String UpdateSingleUserSignature(@PathVariable("userId") String userId, Model model,
			HttpServletRequest request) throws GeneralSecurityException, IOException, URISyntaxException {

		UserApp authenticatedUser = authenticationManager.getUserCredentials();
		Company compny = authenticatedUser.getCompany();

		List<SignatureTemplate> signatureTemplatesList = signaterRepo.findByCompany(compny);
		model.addAttribute("userId", "" + authenticatedUser.getId());
		model.addAttribute("mysignature", signatureTemplatesList);
		model.addAttribute("Signature", new Signature());
		model.addAttribute("GeneralDelegation", new GeneralDelegation());

		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("_csrf.token", token.getToken());
		model.addAttribute("_csrf.parameterName", token.getParameterName());
		model.addAttribute("_csrf_header", token.getHeaderName());

		System.out.println("token" + token.getToken());

		return "signature_singleuser";
	}

//

}
