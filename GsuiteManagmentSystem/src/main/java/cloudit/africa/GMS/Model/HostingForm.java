package cloudit.africa.GMS.Model;

import java.io.Serializable;

public class HostingForm implements Serializable {

	private String display;
    private String cdn;
    private String hosting;
    private String paas;
    private String whoisPattern;
    private String id;
    private String domain;
    private String name;
    private String desc;
    private String tags;
    private String affLink;
    private String imageUrl;
    private String favUrl;
	public String isDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String isCdn() {
		return cdn;
	}
	public void setCdn(String cdn) {
		this.cdn = cdn;
	}
	public String isHosting() {
		return hosting;
	}
	public void setHosting(String hosting) {
		this.hosting = hosting;
	}
	public String isPaas() {
		return paas;
	}
	public void setPaas(String paas) {
		this.paas = paas;
	}
	public String getWhoisPattern() {
		return whoisPattern;
	}
	public void setWhoisPattern(String whoisPattern) {
		this.whoisPattern = whoisPattern;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getAffLink() {
		return affLink;
	}
	public void setAffLink(String affLink) {
		this.affLink = affLink;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getFavUrl() {
		return favUrl;
	}
	public void setFavUrl(String favUrl) {
		this.favUrl = favUrl;
	}
	@Override
	public String toString() {
		return "HostingForm [display=" + display + ", cdn=" + cdn + ", hosting=" + hosting + ", paas=" + paas
				+ ", whoisPattern=" + whoisPattern + ", id=" + id + ", domain=" + domain + ", name=" + name + ", desc="
				+ desc + ", tags=" + tags + ", affLink=" + affLink + ", imageUrl=" + imageUrl + ", favUrl=" + favUrl
				+ ", isDisplay()=" + isDisplay() + ", isCdn()=" + isCdn() + ", isHosting()=" + isHosting()
				+ ", isPaas()=" + isPaas() + ", getWhoisPattern()=" + getWhoisPattern() + ", getId()=" + getId()
				+ ", getDomain()=" + getDomain() + ", getName()=" + getName() + ", getDesc()=" + getDesc()
				+ ", getTags()=" + getTags() + ", getAffLink()=" + getAffLink() + ", getImageUrl()=" + getImageUrl()
				+ ", getFavUrl()=" + getFavUrl() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
    
    
    
}
