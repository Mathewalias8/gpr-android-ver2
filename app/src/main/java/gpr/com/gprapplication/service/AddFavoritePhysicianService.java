package gpr.com.gprapplication.service;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.AbstractRESTService;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.utility.ExtractFromJsonServiceUtil;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public class AddFavoritePhysicianService extends AbstractRESTService {
	public static final String CLASS_NAME = "CreateReferralService";

	public AddFavoritePhysicianService(String token) throws GPRException {
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
				userData.put("enrollmentId", new Long(args[0]).longValue());

			}
			userData.put("requestId", generateRandomReqId());
			JSONObject jsonCreateResponse = null;
				jsonCreateResponse = HttpClientUtil.sendHttpPost(
						GPRConstants.API_URL + GPRConstants.ADD_FAVORITE_URL, token,
						userData);

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
