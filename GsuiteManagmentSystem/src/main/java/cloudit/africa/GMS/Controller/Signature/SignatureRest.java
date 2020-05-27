package cloudit.africa.GMS.Controller.Signature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.SendAs;
import com.opencsv.CSVReader;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.SignatureTemplate;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.GMSApiServices.AdminSettingService;
import cloudit.africa.GMS.GMSApiServices.SignatureService;
import cloudit.africa.GMS.GoogleApiServices.GmailApiServices;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.Signature;
import cloudit.africa.GMS.Repository.CheckerRepository;
import cloudit.africa.GMS.Repository.GmsUrlsRepository;
import cloudit.africa.GMS.Repository.ImageUrlRepository;
import cloudit.africa.GMS.Repository.NotificationRepository;
import cloudit.africa.GMS.Repository.PortalHtmlTemplatesRepository;
import cloudit.africa.GMS.Repository.SignatureRepository;
import cloudit.africa.GMS.Repository.UserAppRepositiry;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.ServiceAccount;
import cloudit.africa.GMS.Utilities.Utilities;

@RestController
public class SignatureRest {

	@Autowired
	UserAppRepositiry usrRepo;

	@Autowired
	SignatureRepository signaterRepo;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	GmailApiServices gmailApiServices;

	@Autowired
	CheckerRepository markerCheckerRepository;

	@Autowired
	PortalHtmlTemplatesRepository portalHtmlTemplatesRepository;

	@Autowired
	GmsUrlsRepository gmsUrlsRepository;

	@Autowired
	ImageUrlRepository imageUrlRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	SignatureService signatureService;

	@Autowired
	AdminSettingService adminSetting;

	@RequestMapping("/postSelectedUpdateSignature")
	public KeyValue postSelectedUpdateSignature(@RequestBody KeyValue KeyValue, Model model) {

		UserApp user = authenticationManager.getUserCredentials();

		Checker checker = adminSetting.getChecker("" + 1);

		boolean isPresent = false;
		try {
			if (checker.getId().equals(null)) {
			}
			isPresent = true;

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		if (isPresent) {
			if (checker.isActive()) {
				System.out.println("MARKER CHECKER iS ENABLED");
				if (checker.getChecker().getId().equals(user.getId())) {
					System.out.println("USER IS CHECKER");

					signatureService.signatureUpdatesAccounts(KeyValue);
				} else {
					System.out.println("" + KeyValue);
					System.out.println("USER IS NOT CHECKER");
					authenticationManager.executeWorkFlowCheckerNew(checker, "UPDATE_SIGNATURE", KeyValue);
				}
			} else {
				System.out.println("MARKER CHECKER iS DISABLED");
				signatureService.signatureUpdatesAccounts(KeyValue);
			}
		} else {
			System.out.println("MARKER CHECKER iS NOT PRESENT");
			signatureService.signatureUpdatesAccounts(KeyValue);
		}

		return KeyValue;

	}

	@RequestMapping("/UpdateAllUsersSignature/{templateId}")
	public String UpdateAllUsersSignature(@PathVariable("templateId") String templateId, Model model)
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

		return "Success";

	}

	@RequestMapping(value = "/createdSignature", method = RequestMethod.POST)
	public Signature createSignatureToDatabase(@RequestBody Signature signature) {

		UserApp user = authenticationManager.getUserCredentials();
		SignatureTemplate templaSignature = new SignatureTemplate();
		templaSignature.setCompany(user.getCompany());
		templaSignature.setCreatedBy(user.getId());
		templaSignature.setCreatedDate(new Date());
		templaSignature.setUpdateDate(new Date());
		templaSignature.setUpdateBy(user.getId());
		templaSignature.setIsActive(false);
		templaSignature.setName(signature.getSignatureName());
		templaSignature.setSignatureBody(signature.getSignatureTemplate());
		signaterRepo.saveAndFlush(templaSignature);

		return signature;
	}

	@RequestMapping("DeleteSignature/{templateId}")
	public String deleteSignatureFromDatabase(@PathVariable("templateId") String templateId, Model model)
			throws GeneralSecurityException, IOException, URISyntaxException {
		signaterRepo.deleteById(Integer.parseInt(templateId));
		System.out.println("Id To Delete" + templateId);
		return "success";
	}

	@RequestMapping("/UploadSignatureCSVFile")
	public List<SigCsvFile> getCSVReader(@RequestBody SigCsvFile bse64Strings) {
//		ServiceResponse serviceResponse = new ServiceResponse();

		String bse64String = new String(bse64Strings.getBasefile());
		String id = "" + System.currentTimeMillis();
		byte[] decodedBytes = Base64.getDecoder().decode(bse64String);

		List<List<String>> records = new ArrayList<List<String>>();
		List<SigCsvFile> templatesUpdated = new ArrayList<SigCsvFile>();

		try {
			FileUtils.writeByteArrayToFile(new File("src/main/resources/SignatureFiles/" + id + ".csv"), decodedBytes);
			CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/SignatureFiles/" + id + ".csv"));
			String[] values = null;
			while ((values = csvReader.readNext()) != null) {
				records.add(Arrays.asList(values));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String bankname = "Kenya Women Microfinance Bank";
		String phonenumber = "Tel 0703067000";
		String hq = "Akira House, Kiambere Road, Upperhill";
		String website = "www.kwftbank.com";

		String rows = "" + records.get(0).size();

		for (int a = 0; a < records.get(0).size(); a++) {
			
		}

		for (int a = 1; a < records.size(); a++) {

//			String firstElement = "" + records.get(0).get(1);
//			String secondElement = "" + records.get(0).get(2);
//			String thirdElement = "" + records.get(0).get(3);
//			String fourthElement = records.get(0).get(4);
//			String fifthElement = records.get(0).get(5);

			String value="<div>";
			SigCsvFile sigCsvFile = new SigCsvFile();
			sigCsvFile.setId(records.get(a).get(0));
		
			for (int b = 0; b < records.get(0).size(); b++) {
				value = value + getType(records.get(0).get(b),records.get(a).get(b));
			}
			System.out.print(":: "+value+"</div> /n/n");
			
			sigCsvFile.setBasefile(value+"</div>");
			
			
			
//			String firstName = "" + records.get(a).get(1);
//			String secondName = "" + records.get(a).get(2);
//			String jobtitle = "" + records.get(a).get(3);
//			String department = records.get(a).get(4);
//			String officelocation = records.get(a).get(5);

//			String signatureTemplate = " <div>\r\n" + "        <p style=\"margin: 0;padding: 0;\">" + firstName + " "
//					+ secondName + "</p>\r\n" + "        <p style=\"margin: 0;padding: 0;\">" + jobtitle + "</p>\r\n"
//					+ "        <p style=\"margin: 0;padding: 0;\">" + department + "</p>\r\n"
//					+ "        <p style=\"margin: 0;padding: 0;\">" + officelocation + "</p>\r\n"
//					+ "        <p style=\"margin: 0;padding: 0;\">" + bankname + "</p>\r\n"
//					+ "        <p style=\"margin: 0;padding: 0;\">" + hq + "</p>\r\n"
//					+ "        <p style=\"margin: 0;padding: 0;\">" + phonenumber + "</p>\r\n"
//					+ "        <a style=\"margin: 0;padding: 0;\" href=\"\\" + website + "\">www.kwftbank.com</a>\r\n"
//					+ "        <img src='" + bse64Strings.getId() + "'>\r\n" + "    </div>";

			
			templatesUpdated.add(sigCsvFile);
		}
		new File("src/main/resources/SignatureFiles/" + id + ".csv").delete();
		return templatesUpdated;
	}

	public String getType(String elementPosition,String value) {
		if (elementPosition.equals("text")) {
			return "<p style=\"margin: 0px;padding: 0px;\">" + value + "</p>";
		} else if (elementPosition.equals("image")) {
			return  "<img style=\\\"margin: 0px;padding: 0px;\\\" src='" + value + "'>";
		} else if (elementPosition.equals("link")) {
			return  "<div><a style=\"margin: 0px;padding: 0px;\" href=\"\\" + value + "\">"+value+"</a></div>";
		} else {
			return "";
		}
	}

}
