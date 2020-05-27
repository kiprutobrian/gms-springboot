package cloudit.africa.GMS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne
	private UserApp userApp;
	@ManyToOne
	private Role role;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserApp getUserApp() {
		return userApp;
	}
	public void setUserApp(UserApp userApp) {
		this.userApp = userApp;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserRole [Id=" + id + ", userApp=" + userApp + ", role=" + role + "]";
	}

	
}
