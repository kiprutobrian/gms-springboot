package cloudit.africa.GMS.Entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//

@Entity
@Table(name = "user_app")
public class UserApp {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String imageUrl;
	private String email;
	private String phoneNumber;
	private Date updatedAt;
	private String password;
	private String serviceEmailAdress;
	@OneToMany
	private List<Role> roles;
	@ManyToOne
	private Company company;
	private boolean active = false;
	private boolean isSuperAdmin = false;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServiceEmailAdress() {
		return serviceEmailAdress;
	}

	public void setServiceEmailAdress(String serviceEmailAdress) {
		this.serviceEmailAdress = serviceEmailAdress;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public UserApp() {

	}

	public UserApp(String email) {
		// TODO Auto-generated constructor stub
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserApp [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", imageUrl=" + imageUrl + ", email=" + email + ", phoneNumber=" + phoneNumber + ", updatedAt="
				+ updatedAt + ", password=" + password + ", serviceEmailAdress=" + serviceEmailAdress + ", roles="
				+ roles + ", company=" + company + ", active=" + active + ", isSuperAdmin=" + isSuperAdmin + "]";
	}
	
	
	
	
}