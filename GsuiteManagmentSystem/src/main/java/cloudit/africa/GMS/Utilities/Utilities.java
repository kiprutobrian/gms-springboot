package cloudit.africa.GMS.Utilities;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.ArrayMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.RoleAccess;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Model.Checkers;
import cloudit.africa.GMS.Model.MyDriveFiles;

public class Utilities {

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYY-MM-DD");

	public static Date getpreviousWeek() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		c.add(Calendar.DATE, -i - 7);
		Date start = c.getTime();
		return start;
	}

	public static String convertTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		return format.format(date);
	}

	public static String getYearOnly(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("YYY");
		return format.format(date);
	}
	public static String getMonthOnly(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("MMM");
		return format.format(date);
	}
	
	
	
	public static String getYearMonthOnly(Date time) {
		SimpleDateFormat format = new SimpleDateFormat("MMM.YYY");
		return format.format(time);
	}

	public static String getStringNull(Object data) {
		try {
			return data.toString();
		} catch (Exception e) {
			return ",";
			// TODO: handle exception
		}
	}

	public static String getEmptyNullLongValue(DateTime longValue) {

		if (longValue != null) {

			return simpleDateFormat.format(longValue.getValue());
		} else {
			return "";
		}
	}

	public static String getEmptyNullStringValue(String string) {
		try {
			return "" + string;
		} catch (NullPointerException e) {
			return " ";
			// TODO: handle exception
		}
	}

	public static String getValues(Object obj, String key) {
		List<Object> org = (List<Object>) obj;
		if (org != null) {
			for (int a = 0; a < org.size(); a++) {
				ArrayMap<String, String> map = (ArrayMap<String, String>) org.get(a);
				return map.get(key);
			}
		}
		return "";
	}

	public static String getValue(Object obj) {

		String phone = "null";
		try {
			phone = getEmptyNullStringValue(obj.toString());
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
		if (!phone.equals("null")) {
			int lethsize = phone.length();
			if (lethsize > 5) {
				String phoneArray = phone.replace("{[", "").replace("]}", "");
				String[] phone1 = phoneArray.split("=");
				String phoneNumber = "" + phone1[1];
				String fetnumber[] = phoneNumber.split(",");
				return fetnumber[0];
			}
		}
		return null;
	}

	public static Integer getNullStringList(List<String> jsArray) {
		try {
			System.out.println("Json Array Size-----" + jsArray.size());
			return jsArray.size();
		} catch (NullPointerException npe) {
			// do something
			return 0;
		}
	}

	public static boolean getRightsAcess(boolean Service, boolean roleAcess) {

		if (Service && roleAcess) {
			if (Service) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static Date setDateExpirationTime(int hours) {
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, hours); // adds four hour
		Date date = cal.getTime(); // returns new date object

		;
		return date;
	}
	
	
	
	
	public static String getMinutesDiff(Date currentTime, Date tokenExpirationTime) {

		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
		// GMT represented by this Date object
		long diff = tokenExpirationTime.getTime() - currentTime.getTime();

		int diffmin = (int) (diff / (60 * 1000));
		System.out.println("difference between minutues: " + crunchifyFormatter.format(diffmin));

		return crunchifyFormatter.format(diffmin);
	}

	public static String getDateDiff(Date currentTime, Date tokenExpirationTime) {

		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
		// GMT represented by this Date object
		long diff = tokenExpirationTime.getTime() - currentTime.getTime();

		System.out.println("difference LONG VALUE: " + diff);
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		System.out.println("difference between days: " + diffDays);

		int diffhours = (int) (diff / (60 * 60 * 1000));
		System.out.println("difference between hours: " + crunchifyFormatter.format(diffhours));

		int diffmin = (int) (diff / (60 * 1000));
		System.out.println("difference between minutues: " + crunchifyFormatter.format(diffmin));

		int diffsec = (int) (diff / (1000));
		System.out.println("difference between seconds: " + crunchifyFormatter.format(diffsec));
		System.out.println("difference between milliseconds: " + crunchifyFormatter.format(diff));

		return crunchifyFormatter.format(diffhours);
	}

	public static Boolean checkTokenValidity(Date currentTime, Date tokenExpirationTime) {

		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
		// GMT represented by this Date object
		long diff = tokenExpirationTime.getTime() - currentTime.getTime();

		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		System.out.println("difference between days: " + diffDays);
		int diffhours = (int) (diff / (60 * 60 * 1000));
		System.out.println("difference between hours: " + crunchifyFormatter.format(diffhours));
		int diffmin = (int) (diff / (60 * 1000));
		System.out.println("difference between minutues: " + crunchifyFormatter.format(diffmin));
		int diffsec = (int) (diff / (1000));
		System.out.println("difference between seconds: " + crunchifyFormatter.format(diffsec));
		System.out.println("difference between milliseconds: " + crunchifyFormatter.format(diff));

		if (diffsec < 0) {
			System.out.println(false);
			return false;
		} else {
			System.out.println(true);
			return true;
		}

	}

	public static ServiceResponse getChecker(List<Checker> checkerlist, String MakerCheckerId) {
		ServiceResponse sc = new ServiceResponse();
		sc.setPresent(false);
		for (int x = 0; x < checkerlist.size(); x++) {

			if (checkerlist.get(x).getMakerCheckers().getId().equals(MakerCheckerId)) {
				sc.setData(checkerlist.get(x));
				sc.setPresent(true);
				return sc;
			}
		}
		return sc;
	}

	public static ServiceResponse getCheckerExistance(List<Checker> checkerlist, Checkers checkers, UserApp userApp) {
		ServiceResponse sc = new ServiceResponse();
		sc.setPresent(false);
		for (int x = 0; x < checkerlist.size(); x++) {
			Checker checkerexist = checkerlist.get(x);
			if (checkerexist.getMakerCheckers().getId().equals(checkers.getId())) {
				sc.setData(checkerlist.get(x));
				sc.setPresent(true);
				return sc;
			}
		}
		return sc;
	}

	public static boolean getBoolean(Boolean isSuperAdminRole) {
		// TODO Auto-generated method stub
		try {
			return isSuperAdminRole;
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		return false;
	}

	public static String getDateDiffInMinutes(Date currentTime, Date tokenExpirationTime) {

		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

		long diff = tokenExpirationTime.getTime() - currentTime.getTime();
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		int diffhours = (int) (diff / (60 * 60 * 1000));
		int diffmin = (int) (diff / (60 * 1000));
		int diffsec = (int) (diff / (1000));

		return crunchifyFormatter.format(diffmin);
	}

	public static boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
		return !(testDate.before(startDate) || testDate.after(endDate));
	}

	public static boolean isAfter(Date lastdate, Date datesent) {
		return (lastdate.before(datesent));
	}

	public static Date yesterday(int days) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date dat = cal.getTime();
		long milliseconds = dat.getTime();
		System.out.println("" + milliseconds);
		return dat;
	}

	public static boolean isIsIpAddressOutsideMyDomain(String myipAddress, String downloadip, String viewIp) {
		boolean isOutsideMydomain = false;
		if (downloadip == null) {
			isOutsideMydomain = true;
			return isOutsideMydomain;
		} else {
			if (downloadip.contains(myipAddress)) {
				isOutsideMydomain = false;
				return isOutsideMydomain;
			} else {
				isOutsideMydomain = true;
				return isOutsideMydomain;
			}
		}

	}

}
