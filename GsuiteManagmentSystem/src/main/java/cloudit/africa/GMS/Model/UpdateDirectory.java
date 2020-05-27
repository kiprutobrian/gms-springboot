package cloudit.africa.GMS.Model;

public class UpdateDirectory {
	
	private String id;
	private String givenName;
	private String fullname;
	private String familyname;
	private String emailAdress;
	private String imageUrl;
	private String createdById;
	private String passsword;
	
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmailAdress() {
		return emailAdress;
	}
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFamilyname() {
		return familyname;
	}
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	@Override
	public String toString() {
		return "UpdateDirectory [id=" + id + ", givenName=" + givenName + ", fullname=" + fullname + ", familyname="
				+ familyname + ", emailAdress=" + emailAdress + ", imageUrl=" + imageUrl + "]";
	}
	public String getPasssword() {
		return passsword;
	}
	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}
	
	

}
