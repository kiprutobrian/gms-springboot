package cloudit.africa.GMS.Utilities;

public class ServiceResponse {

	private String status;
	private Object data;
	private boolean isPresent;
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

	@Override
	public String toString() {
		return "ServiceResponse [status=" + status + ", data=" + data + ", isPresent=" + isPresent + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
