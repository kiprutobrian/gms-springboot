package cloudit.africa.GMS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PortalHtmlTemplates {
	
	@Id
	@GeneratedValue
	private Integer Id;
	private String name;
	private String description;
	private String htmlTemplate;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHtmlTemplate() {
		return htmlTemplate;
	}
	public void setHtmlTemplate(String htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}
	
	
	
}
