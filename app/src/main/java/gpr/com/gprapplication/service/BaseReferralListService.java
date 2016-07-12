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

public abstract class BaseReferralListService extends AbstractRESTService {
	public static final String CLASS_NAME = "IncomingReferralService";

	public BaseReferralListService(String token) throws GPRException {
		this.token = token;
	}

	@Override
	public List<Referral> getParseJson(String... strArgs) throws GPRException, AuthenticationException {
		final String TAG = "getParseJson";
		Log.d(TAG, "Start getParseJson method");
		List<Referral> referrals = new ArrayList<Referral>();
		try {
			JSONObject userData = new JSONObject();
			
			userData.put("since", strArgs[0]);
			userData.put("offset", strArgs[1]);
			userData.put("limit", strArgs[2]);
			userData.put("type", getType());
			userData.put("requestId", generateRandomReqId());
			
			JSONObject jsonResponse = HttpClientUtil.sendHttpPost(
					GPRConstants.API_URL + GPRConstants.INCOMING_URL, token,
					userData);

			JSONArray jsonArray = jsonResponse.getJSONArray("referrals");
			String imageBaseUrl = jsonResponse.getString("imageUrlPrefix");
			
			long since = jsonResponse.getLong("since");
			long fetchTime = jsonResponse.getLong("fetchTime");
			int count = jsonResponse.getInt("count");
			int offset = jsonResponse.getInt("offset");
			int limit = jsonResponse.getInt("limit");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject r = jsonArray.getJSONObject(i);
				Referral incomingReferral = ExtractFromJsonServiceUtil
						.extractReferralFields(r, imageBaseUrl);
				incomingReferral.setReferralType(ReferralType.INCOMING);
				referrals.add(incomingReferral);
			}
			
//			DataHolder.getIncomingListMetaData().setValues(since, fetchTime, offset, limit, count);
		} catch (AuthenticationException e1) {
			throw e1;			
		} catch (Exception e1) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e1, CLASS_NAME, TAG);
			throw ex;
		}
		Log.d(TAG, "Exit getParseJson method");
		return referrals;

	}

	protected abstract String getType();

}
