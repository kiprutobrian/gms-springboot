package cloudit.africa.GMS.Utilities;

import cloudit.africa.GMS.Entity.UserApp;

public class HtmlTemplates {

	public static String makerCheckerTemlate(UserApp user, String name, String Template, String Title,
			String Approvelink, String gmsLogo) {

		String sendTemplate = (Template.replace("checkerName", name).replace("ChekerActionTitle", Title).replace("authUsername", user.getUsername()));
//		String templateImageEmail = ((sendTemplate.replace("authUsername", user.getUsername())).replace("gmslogo",
//				gmsLogo)).replace("authUserImage", user.getImageUrl());
		
		String templateUrlLinks = sendTemplate.replace("approveLink", Approvelink);
		
		return templateUrlLinks;
	}

	public static String appointmentBooking(String link) {

		String calenderAppointmentHtml = "  <p style=\"display:none;\">appointmentstart</p> <div style=\"margin-top:5px\">\n"
				+ "            <hr>\n"
				+ "            <p ><span style=\"color: blue;margin-left: 20px;font-family: initial\">Click to Request For An Appoint</span></p>\n"
				+ "            <a href=\"" + link + "\"height=\"50px\" width=\"180px\" target=\"_blank\">\n"
				+ "            <img   src=\"http://www.purebridaliowa.com/wp-content/uploads/2019/02/appointment-request-icon.57110256_std.png\" width=\"250px\" height=\"70px\"></a>\n"
				+ "        </div>  <p style=\"display:none;\">appointmentend</p>";

		return calenderAppointmentHtml;
	}

	public static String templateReturn(String Title, String names, String phoneNumber, String location,
			String emailAdress, String company, String time, String toTime) {

		String descriptionMessage = "<a href=\\" + ""
				+ "\\rel=\"nofollow\" target=\"_blank\" title=\"W3C HTML validator\">" + names + ":" + emailAdress
				+ "</a>" + " form " + company + " Located in" + location
				+ " Nairobi KENYA I Would Like To Book An Appointment With You At";

		String xx = "<h2>" + Title + "</h2>\n" + "<p>Hi Am" + descriptionMessage + "<em> " + time + " </em> To <em> "
				+ toTime + " </em>.</p>\n";

		return xx;
	}
	
	public static String templateReturn(String Title, String names, String phoneNumber, String location,
			String emailAdress, String company,String description) {

		String descriptionMessage = "<a href=\\" + ""
				+ "\\rel=\"nofollow\" target=\"_blank\" title=\"W3C HTML validator\">" + names + ":" + emailAdress
				+ "</a>" + " form " + company + " Located in" + location
				+ " Nairobi Kenya I would like to book an appointment concerning  "+description;

		String xx = "<h2>" + Title + "</h2>\n" + "<p>Hi Am" + descriptionMessage+".</p>\n";

		return xx;
	}
}
