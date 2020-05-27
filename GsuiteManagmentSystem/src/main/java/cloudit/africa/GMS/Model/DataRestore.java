package cloudit.africa.GMS.Model;

public class DataRestore {

	
	private String lableId;
	private String account;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLableId() {
		return lableId.replaceAll("^\"|\"$", "");
	}
	public void setLableId(String lableId) {
		this.lableId = lableId;
	}
	@Override
	public String toString() {
		return "DataRestore [lableId=" + lableId + ", account=" + account + "]";
	}
	
	
	
}
