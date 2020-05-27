package cloudit.africa.GMS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Configeration {

	@Id
	@GeneratedValue
	private Integer id;
	private String keyValue;
	@ManyToOne
	private ConfigerationType configerationtype;
	@ManyToOne
	private Company company;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public ConfigerationType getConfigerationtype() {
		return configerationtype;
	}
	public void setConfigerationtype(ConfigerationType configerationtype) {
		this.configerationtype = configerationtype;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
