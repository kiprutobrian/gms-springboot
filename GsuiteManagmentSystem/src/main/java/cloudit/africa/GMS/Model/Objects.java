package cloudit.africa.GMS.Model;

import java.io.Serializable;

public class Objects implements Serializable {

	private String Objects;
	private String type;
	public String getObjects() {
		return Objects;
	}
	public void setObjects(String objects) {
		Objects = objects;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Objects [Objects=" + Objects + ", type=" + type + "]";
	}
	


	
	
	
}
