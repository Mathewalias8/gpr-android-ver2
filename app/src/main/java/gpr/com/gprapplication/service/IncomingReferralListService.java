package gpr.com.gprapplication.service;

import android.util.Log;


import org.apache.http.auth.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.ReferralType;
import gpr.com.gprapplication.utility.ExtractFromJsonServiceUtil;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public class IncomingReferralListService extends BaseReferralListService {
	public static final String CLASS_NAME = "IncomingReferralService";


	public IncomingReferralListService(String token) throws GPRException {
		super(token);
	}


	@Override
	protected String getType() {
		return "INCOMING";
	}
}
