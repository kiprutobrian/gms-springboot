package cloudit.africa.GMS.Controller.CalenderNew;

import java.util.Date;

public class myCalender {

	private Integer id;
	private String day;
	private Date from;

	private String to;
	private boolean isPm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public boolean getIsPm() {
		return isPm;
	}

	public void setIsPm(boolean isPm) {
		this.isPm = isPm;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	@Override
	public String toString() {
		return "myCalender [id=" + id + ", day=" + day + ", from=" + from + ", to=" + to + ", isPm=" + isPm + "] \n";
	}
	
	
	

}
