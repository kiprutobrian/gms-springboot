package cloudit.africa.GMS.Model;

public class Analysis {
	
	private String account;
	private Integer number;
	
	public Analysis(String account, Integer number) {
		super();
		this.account = account;
		this.number = number;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

}
