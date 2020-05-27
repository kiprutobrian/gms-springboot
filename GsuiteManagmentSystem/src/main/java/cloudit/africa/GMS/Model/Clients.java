package cloudit.africa.GMS.Model;

public class Clients {
	
	private String names;
	private String email;
	private String date;
	private String company;
	
	public Clients(String email) {
		this.email = email;
	}
	
	
	public Clients(String names, String email, String date, String company) {
		super();
		this.names = names;
		this.email = email;
		this.date = date;
		this.company = company;
	}
	
	public Clients() {
		// TODO Auto-generated constructor stub
	}

	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	

}
