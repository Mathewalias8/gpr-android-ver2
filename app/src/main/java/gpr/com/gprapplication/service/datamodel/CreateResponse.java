package gpr.com.gprapplication.service.datamodel;

public class CreateResponse extends BaseModel {
	private static final long serialVersionUID = -3365231492960409488L;
	int requestId;
	String status;
	String message;
	int referralId;

	public synchronized int getRequestId() {
		return requestId;
	}

	public synchronized void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public synchronized String getStatus() {
		return status;
	}

	public synchronized void setStatus(String status) {
		this.status = status;
	}

	public synchronized String getMessage() {
		return message;
	}

	public synchronized void setMessage(String message) {
		this.message = message;
	}

	public synchronized int getReferralId() {
		return referralId;
	}

	public synchronized void setReferralId(int referralId) {
		this.referralId = referralId;
	}
}
