package gpr.com.gprapplication.service;

import android.util.Log;


import org.apache.http.auth.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;
import gpr.com.gprapplication.utility.ExtractFromJsonServiceUtil;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public class RecentReferredPhysicianListService extends AbstractRESTService {
	public static final String CLASS_NAME = "RecentReferralService";
	public static int offset = 0;

	public RecentReferredPhysicianListService(String token) throws GPRException {
		this.token = token;
	}

	@Override
	public List<SimpleEnrollment> getParseJson(String... strArgs) throws GPRException, AuthenticationException {
		final String TAG = "getParseJson";
		Log.d(TAG, "Start getParseJson method");
		List<SimpleEnrollment> listData = new ArrayList<SimpleEnrollment>();
		try {
			JSONObject userData = new JSONObject();
//			userData.put("limit", strArgs[0]);
//			userData.put("offset", offset);
			userData.put("requestId", reqeustId);
			JSONObject jsonResponse = HttpClientUtil.sendHttpPost(
					GPRConstants.API_URL + GPRConstants.GET_FAVORITE_URL, token,
					userData);

			JSONArray jsonArray = jsonResponse.getJSONArray("favorites");
			String imageBaseUrl = jsonResponse.getString("imageUrlPrefix");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject r = jsonArray.getJSONObject(i);
				listData.add(ExtractFromJsonServiceUtil.extractRecentReferrals(
						r, imageBaseUrl));
			}
		} catch (AuthenticationException e1) {
			throw e1;
		} catch (Exception e1) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e1, CLASS_NAME, TAG);
			throw ex;
		}
		Log.d(TAG, "Exit getParseJson method");
		return listData;
	}
}
