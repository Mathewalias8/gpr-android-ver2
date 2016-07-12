package gpr.com.gprapplication.service;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.service.datamodel.Referral_Status;
import gpr.com.gprapplication.utility.ExtractFromJsonServiceUtil;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public class UpdateReferralStatusService extends AbstractRESTService {
	public static final String CLASS_NAME = "CreateReferralService";

	public UpdateReferralStatusService(String token) throws GPRException {
		this.token = token;
	}

	@Override
	public List<CreateResponse> getParseJson(String... args) throws GPRException{
		final String TAG = "getParseJson";
		Log.d(TAG, "Start getParseJson method");
		List<CreateResponse> listResponse = new ArrayList<CreateResponse>();
		try {
			JSONObject userData = new JSONObject();
			{
				userData.put("referralId", new Long(args[0]).longValue());
			}
			userData.put("requestId", generateRandomReqId());
			JSONObject jsonCreateResponse = null;
			if (args[1].equals(Referral_Status.REFERRAL_ACCEPTED.toString()))
			{
				jsonCreateResponse = HttpClientUtil.sendHttpPost(
						GPRConstants.API_URL + GPRConstants.ACCEPT_REFFERRAL_URL, token,
						userData);
			}
			else if (args[1].equals(Referral_Status.REFERRAL_REJECTED.toString()) ){
				jsonCreateResponse = HttpClientUtil.sendHttpPost(
						GPRConstants.API_URL + GPRConstants.REJECT_REFFERRAL_URL, token,
						userData);
			}
			if (null != jsonCreateResponse) {
				listResponse.add(ExtractFromJsonServiceUtil
						.extractCreateResponse(jsonCreateResponse));
			}
		} catch (Exception e1) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e1, CLASS_NAME, TAG);
			throw ex;
		}
		Log.d(TAG, "Exit getParseJson method");
		return listResponse;

	}
}
