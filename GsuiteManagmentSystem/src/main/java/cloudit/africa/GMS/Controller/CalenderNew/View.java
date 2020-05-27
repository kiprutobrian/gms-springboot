package cloudit.africa.GMS.Controller.CalenderNew;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.people.v1.model.Date;

@Controller
public class View {

	@RequestMapping("/AppointmentBooking/{email}/")
	public String getView(@PathVariable("email") String email, Model model, HttpServletRequest request)
			throws GeneralSecurityException, IOException {

		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("_csrf.token", token.getToken());
		model.addAttribute("_csrf.parameterName", token.getParameterName());
		model.addAttribute("_csrf_header", token.getHeaderName());

		System.out.printf(" TOKEN " + token.getToken());
		model.addAttribute("myEmailAddress", email);
		model.addAttribute("myName", email.split("@")[0]);

		return "gms-calenderapoointmentnew";
	}
}
