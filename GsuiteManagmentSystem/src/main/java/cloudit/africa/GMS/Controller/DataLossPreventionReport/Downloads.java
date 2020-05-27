package cloudit.africa.GMS.Controller.DataLossPreventionReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloudit.africa.GMS.GoogleApiServices.GmailDataMigrationService;


@RestController
public class Downloads {

	@Autowired
	GmailDataMigrationService gmailApiServices;

	@RequestMapping("/File/{account}/{messageid}")
	public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("account") String account,
			@PathVariable("messageid") String messageid, HttpServletRequest request, Model model) {
		String bath = gmailApiServices.getfilepth(account, messageid);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + bath);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		File file = new File(bath);
		InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
