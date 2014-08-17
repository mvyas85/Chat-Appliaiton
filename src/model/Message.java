package model;

public class Message {
	
	private int messegeId;
	private String content;
	private String date;
	private int senderId;
	
	
	public Message() {
		super();
	}


	public Message( String content, int senderId) {
		super();
		this.content = content;
		this.senderId = senderId;
	}


	@Override
	public String toString() {
		return "Message [messegeId=" + messegeId + ", content=" + content
				+ ", date=" + date + ", senderId=" + senderId + "]";
	}


	public int getMessegeId() {
		return messegeId;
	}


	public String getContent() {
		return content;
	}


	public String getDate() {
		return date;
	}


	public int getSenderId() {
		return senderId;
	}


	public void setMessegeId(int messegeId) {
		this.messegeId = messegeId;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	
	
	

	
}
