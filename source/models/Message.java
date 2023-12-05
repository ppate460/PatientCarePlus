package models;

public class Message {
	int rootMessageId;
	int messageId;
	int fromId;
	int toId;
	int senderType;
	String subject;
	String message;
	String sentDate;
	public Message(int rootMessageId,int messageId, int fromId, int toId, int senderType, String subject, String message,
			String sentDate) {
		super();
		this.rootMessageId=rootMessageId;
		this.messageId = messageId;
		this.fromId = fromId;
		this.toId = toId;
		this.senderType = senderType;
		this.subject = subject;
		this.message = message;
		this.sentDate = sentDate;
	}
	public int getRootMessageId() {
		return rootMessageId;
	}
	public void setRootMessageId(int rootMessageId) {
		this.rootMessageId = rootMessageId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
	}
	public int getSenderType() {
		return senderType;
	}
	public void setSenderType(int senderType) {
		this.senderType = senderType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public String getListRow()
	{
		return String.format("%s - %s",sentDate, subject);
	}
	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", fromId=" + fromId + ", toId=" + toId + ", senderType="
				+ senderType + ", subject=" + subject + ", message=" + message + ", sentDate=" + sentDate + "]";
	}
	
}
