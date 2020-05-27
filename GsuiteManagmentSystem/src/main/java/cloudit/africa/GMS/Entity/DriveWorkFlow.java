package cloudit.africa.GMS.Entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class DriveWorkFlow {

	@Id
	@GeneratedValue
	private Integer Id;
	private String name;
	private String key;
	private boolean owner;
	private String urlType;
	private String token;
	private Date tokenExpirationTime;
	private boolean executed=false;
	
	@ManyToMany
	private List<GmsFile> gmsFiles;

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
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public List<GmsFile> getGmsFiles() {
		return gmsFiles;
	}

	public void setGmsFiles(List<GmsFile> gmsFiles) {
		this.gmsFiles = gmsFiles;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "DriveWorkFlow [Id=" + Id + ", name=" + name + ", key=" + key + ", owner=" + owner + ", urlType="
				+ urlType + ", token=" + token + ", tokenExpirationTime=" + tokenExpirationTime + ", executed="
				+ executed + ", gmsFiles=" + gmsFiles + "]";
	}
	
	
	

	
	}
