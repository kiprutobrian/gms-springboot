package cloudit.africa.GMS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Checker {

	
	@Id
	private String id;
	private boolean active=false;
	@ManyToOne
	private MarkerCheckers makerCheckers;
	@ManyToOne
	private UserApp checker;
	@ManyToOne
	private Company company;
	
	
	 public Checker() {}
	
	public Checker(MarkerCheckers makerCheckers, Company company) {
		super();
		this.makerCheckers = makerCheckers;
		this.company = company;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MarkerCheckers getMakerCheckers() {
		return makerCheckers;
	}
	public void setMakerCheckers(MarkerCheckers makerCheckers) {
		this.makerCheckers = makerCheckers;
	}
	public UserApp getChecker() {
		return checker;
	}
	public void setChecker(UserApp checker) {
		this.checker = checker;
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

	public Checker orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Checker [id=" + id + ", active=" + active + ", makerCheckers=" + makerCheckers + ", checker=" + checker
				+ ", company=" + company + "]";
	}
	
	
	
	
}
