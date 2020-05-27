package cloudit.africa.GMS.Controller.LoginRegistration;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.Package;
import cloudit.africa.GMS.Entity.Registration;
import cloudit.africa.GMS.GMSApiServices.LoginService;
import cloudit.africa.GMS.GoogleApiServices.DirectoryService;
import cloudit.africa.GMS.GoogleApiServices.GroupssettingsService;
import cloudit.africa.GMS.Model.DomainRegistration;
import cloudit.africa.GMS.Model.KeyValue;
import cloudit.africa.GMS.Model.RegistrationForm;
import cloudit.africa.GMS.Repository.CompanyRepository;
import cloudit.africa.GMS.Repository.PackageRepository;
import cloudit.africa.GMS.Repository.RegistrationRepository;
import cloudit.africa.GMS.SecurityConfigurations.GMSAuthenticationManager;
import cloudit.africa.GMS.ServiceAccount.GMSFilesPath;
import cloudit.africa.GMS.Utilities.ServiceResponse;

@RestController
public class RegistrationRest {

	@Autowired
	GroupssettingsService groupssettingsService;

	@Autowired
	RegistrationRepository registrationRepository;

	@Autowired
	DirectoryService direcService;

	@Autowired
	PackageRepository packageRepository;

	@Autowired
	CompanyRepository companyRepositiry;

	@Autowired
	GMSAuthenticationManager authenticationManager;

	@Autowired
	LoginService loginService;

	@RequestMapping("postRegestartionForm")
	public ServiceResponse PostRegistrationForm(@RequestBody DomainRegistration registrationForm) {
		ServiceResponse serviceResponse = new ServiceResponse();

		String domainnmae = registrationForm.getDomain().replace(".", "");
		byte[] decodedJsonFileBytes = Base64.getDecoder().decode(registrationForm.getBase64JsonFile());
		byte[] decodedP12FileBytes = Base64.getDecoder().decode(registrationForm.getBase64P12File());

		if (registrationForm.getBase64JsonFile().length() > 10 && registrationForm.getBase64P12File().length() > 10) {
			try {
				serviceResponse.setStatus("Welcome To GMS");
				serviceResponse.setPresent(true);

				String filepath = GMSFilesPath.readLineByLineJava8();
				File p12File = new File(filepath + domainnmae + ".p12");
				File jsonFile = new File(filepath + domainnmae + ".json");

				FileUtils.writeByteArrayToFile(jsonFile, decodedJsonFileBytes);
				FileUtils.writeByteArrayToFile(p12File, decodedP12FileBytes);

				cloudit.africa.GMS.Entity.Package packageSelected = packageRepository.findById(Integer.parseInt(registrationForm.getPackageId())).get();
				Company company = companyRepositiry.findById(registrationForm.getCompanyId()).get();
				company.setPackages(packageSelected);
				company.setServiceAccountPresent(true);
				company.setPackageActive(true);

				Company OnUpdateCompany = companyRepositiry.saveAndFlush(company);
				Registration registration = new Registration();
				registration.setDomain(company.getDomain());
				registration.setFileName(domainnmae);
				registration.setFileType(".p2");
				registration.setFileContent("" + p12File.getAbsoluteFile());
				registration.setFileJsonBase64("" + registrationForm.getBase64JsonFile());
				registration.setFilep12Base64("" + registrationForm.getBase64P12File());
				registration.setUploader(registrationForm.getUserId());
				registration.setCompany(company);
				registration.setPackageSubscription(packageSelected);
				registration.setProjectName(company.getName());
				registration.setServiceAccountEmail("CLOUDITAFRICA");
				Registration response = registrationRepository.saveAndFlush(registration);
				if (response != null) {
					loginService.superAdminRegestration(OnUpdateCompany);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				serviceResponse.setStatus("Registartion Failed");
				serviceResponse.setPresent(false);
				e.printStackTrace();
			}
		} else {
			serviceResponse.setPresent(false);
			serviceResponse.setStatus("Registartion Failed");
		}
		serviceResponse.setData(registrationForm);
		return serviceResponse;
	}

	@RequestMapping("/SetUpServiceAcounts")
	public ServiceResponse GetSetUpServiceAcounts() throws IOException {
		ServiceResponse serviceResponse = new ServiceResponse();
		List<Registration> regestrationforms = registrationRepository.findAll();
		String filepath = GMSFilesPath.readLineByLineJava8();

		for (int a = 0; a < regestrationforms.size(); a++) {
			Registration registrationForm = regestrationforms.get(a);
			String domainName = "" + registrationForm.getCompany().getDomain();
			String newname = ("" + domainName).replace(".", "");

			if (registrationForm.getFileJsonBase64() != null) {
				File jsonFile = new File(filepath + newname + ".json");
				if (!jsonFile.exists())
					jsonFile.createNewFile();
				byte[] decodedJsonFileBytes = Base64.getDecoder().decode(registrationForm.getFileJsonBase64());
				FileUtils.writeByteArrayToFile(jsonFile, decodedJsonFileBytes);
			}
			if (registrationForm.getFilep12Base64() != null) {
				File p12File = new File(filepath + newname + ".p12");
				if (!p12File.exists())
					p12File.createNewFile();
				byte[] decodedP12FileBytes = Base64.getDecoder().decode(registrationForm.getFilep12Base64());
				FileUtils.writeByteArrayToFile(p12File, decodedP12FileBytes);
			}
			continue;
		}
		return serviceResponse;
	}
	
	
	
	
	

}
