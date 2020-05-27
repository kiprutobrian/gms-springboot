package cloudit.africa.GMS.Model;

import java.util.List;

public class GeneralDelegation {
	
	private String deleageeEmailAdress;
	private List<String> delegateAccounts;
	
	public String getDeleageeEmailAdress() {
		return deleageeEmailAdress;
	}
	public void setDeleageeEmailAdress(String deleageeEmailAdress) {
		this.deleageeEmailAdress = deleageeEmailAdress;
	}
	public List<String> getDelegateAccounts() {
		return delegateAccounts;
	}
	public void setDelegateAccounts(List<String> delegateAccounts) {
		this.delegateAccounts = delegateAccounts;
	}
	
	@Override
	public String toString() {
		return "GeneralDelegation [deleageeEmailAdress=" + deleageeEmailAdress + ", delegateAccounts="
				+ delegateAccounts + ", getDeleageeEmailAdress()=" + getDeleageeEmailAdress()
				+ ", getDelegateAccounts()=" + getDelegateAccounts() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
