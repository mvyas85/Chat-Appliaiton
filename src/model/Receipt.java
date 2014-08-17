package model;

public class Receipt {
	
	private String receiptId;
	private String messageId;
	private String userId;
	private int isRead;
	
	public static final int UNREAD= 0;
	public static final int READ= 1;
	
	public Receipt() {
		super();
	}
	
	

	public Receipt(String receiptId,String messageId, String userId, int isRead) {
		super();
		this.receiptId = receiptId;
		this.messageId = messageId;
		this.userId = userId;
		this.isRead = isRead;
	}



	public String getReceiptId() {
		return receiptId;
	}

	public String getUserId() {
		return userId;
	}

	public int getIsRead() {
		return isRead;
	}

	public String getMessageId() {
		return messageId;
	}


	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}



	@Override
	public String toString() {
		return "Receipt [receiptId=" + receiptId + ", messageId=" + messageId
				+ ", userId=" + userId + ", isRead=" + isRead + "]";
	}

	
	
}
