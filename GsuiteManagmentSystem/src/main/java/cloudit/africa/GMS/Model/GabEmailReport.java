package cloudit.africa.GMS.Model;


public class GabEmailReport {

	private String messageId;
	private String threadId;
	private String historyId;
	private String from;
	private String to;
	private String subject;
	private String snippet;
	private String hasAttachment;
	private String dateSent;
	private String resourceurl;
	private String hasDriveAttachment;

	public GabEmailReport() {
	}

	public GabEmailReport(String messageId, String threadId, String historyId, String from, String to, String subject,
			String snippet, String hasDriveAttachment ,String hasAttachment, String dateSent, String resourceurl) {
		super();
		this.messageId = messageId;
		this.threadId = threadId;
		this.historyId = historyId;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.snippet = snippet;
		this.hasAttachment = hasAttachment;
		this.hasDriveAttachment=hasDriveAttachment;
		this.dateSent = dateSent;
		this.resourceurl = resourceurl;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public String getDateSent() {
		return dateSent;
	}

	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	@Override
	public String toString() {
		return "GabEmailReport [messageId=" + messageId + ", threadId=" + threadId + ", historyId=" + historyId
				+ ", from=" + from + ", to=" + to + ", subject=" + subject + ", snippet=" + snippet + ", hasAttachment="
				+ hasAttachment + ", dateSent=" + dateSent + ", resourceurl=" + resourceurl + "]";
	}

	public String getHasDriveAttachment() {
		return hasDriveAttachment;
	}

	public void setHasDriveAttachment(String hasDriveAttachment) {
		this.hasDriveAttachment = hasDriveAttachment;
	}
	
	
	
	
}
