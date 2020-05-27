package cloudit.africa.GMS.Model;

public class Checkers {

	private String id;
	private String checkerEmailAddress;
	private boolean active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckerEmailAddress() {
		return checkerEmailAddress;
	}

	public void setCheckerEmailAddress(String checkerEmailAddress) {
		this.checkerEmailAddress = checkerEmailAddress;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Checkers [id=" + id + ", checkerEmailAddress=" + checkerEmailAddress + ", active=" + active + "]";
	}

}
