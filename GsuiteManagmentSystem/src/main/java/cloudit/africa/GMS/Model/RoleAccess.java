package cloudit.africa.GMS.Model;

import java.io.Serializable;

public class RoleAccess implements Serializable {

	private String Id;
	private String emailAdress;
	private String roleId;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getEmailAdress() {
		return emailAdress;
	}
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
