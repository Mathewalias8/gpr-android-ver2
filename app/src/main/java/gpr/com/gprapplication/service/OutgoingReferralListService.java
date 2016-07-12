package gpr.com.gprapplication.service;

import gpr.com.gprapplication.service.callback.GPRException;

public class OutgoingReferralListService extends BaseReferralListService {
	public static final String CLASS_NAME = "OutgoingReferralListService";


	public OutgoingReferralListService(String token) throws GPRException {
		super(token);
	}


	@Override
	protected String getType() {
		return "OUTGOING";
	}
}
