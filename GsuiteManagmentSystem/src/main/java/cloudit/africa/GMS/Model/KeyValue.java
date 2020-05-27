package cloudit.africa.GMS.Model;

import java.io.Serializable;
import java.util.List;

import cloudit.africa.GMS.Entity.OrganizationMembers;



public class KeyValue  implements Serializable{
	
	private String key;
	private List<OrganizationMembers> orgUser;
	
	public String getKey() {
		return key.replace("\"", "");
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<OrganizationMembers> getOrgUser() {
		return orgUser;
	}
	public void setOrgUser(List<OrganizationMembers> orgUser) {
		this.orgUser = orgUser;
	}
	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", orgUser=" + orgUser + "]";
	}
	

}
