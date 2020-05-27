package cloudit.africa.GMS.Entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class WorkFlow {

	
	
	@Id
	@GeneratedValue
	private Integer Id;
	private String name;
	private String Key;
	private String urlType;
	private String token;
	private Date tokenExpirationTime;
	private boolean executed = false;
	private String approver;
	@ManyToOne
	private Company company; 
	@ManyToOne
	private UserApp createdBy;	
	private Date createdAt;

	@ManyToMany
	private List<OrganizationMembers> accountsAction;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<OrganizationMembers> getAccountsAction() {
		return accountsAction;
	}

	public void setAccountsAction(List<OrganizationMembers> accountsAction) {
		this.accountsAction = accountsAction;
	}

	public Date getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Date tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public UserApp getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserApp createdBy) {
		this.createdBy = createdBy;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
