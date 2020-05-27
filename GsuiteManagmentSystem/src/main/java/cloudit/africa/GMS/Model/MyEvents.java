package cloudit.africa.GMS.Model;

public class MyEvents {
	
	String title;
	Long start;
	Long end;
	String className;
	
	
	
	public MyEvents(String title, Long start, Long end, String className) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
		this.className = className;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public String toString() {
		return "[title:'" + title + "', start:" + start + ", end:" + end + ", className:'" + className + "']";
	}
	

}
