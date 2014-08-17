package exceptions;

public class ChatDbFailure extends Exception {
	private static final long serialVersionUID = 1L;
	public static final int STMT_FAILED = 0;
	public static final int BAD_USER = 1;
	public static final int INVALID_CREDENTIAL = 2;
	
	private int failureReason;
	
	public ChatDbFailure(int failureReason) {
		this.failureReason = failureReason;
	}
	
	public int getFailureReason() {
		return failureReason;
	}
	
	public String getReasonStr() {
		switch (failureReason) {
		case STMT_FAILED:
			return "Failure Executing Statement";
		case BAD_USER:
			return "Bad User Id";
		case INVALID_CREDENTIAL:
			return "Access Denied! \nInvalid UserName and/or Password";
		default:
			return "Unknown Reason";
		}
	}

}
