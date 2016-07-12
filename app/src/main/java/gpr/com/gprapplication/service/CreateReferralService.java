package gpr.com.gprapplication.service;

import android.util.Log;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.utility.ExtractFromJsonServiceUtil;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public class CreateReferralService extends AbstractRESTService {
	public static final String CLASS_NAME = "CreateReferralService";

	public CreateReferralService(String token) throws GPRException {
		this.token = token;
	}

	@Override
	public List<CreateResponse> getParseJson(String... strArgs) throws GPRException{
		final String TAG = "getParseJson";
		Log.d(TAG, "Start getParseJson method");
		List<CreateResponse> listResponse = new ArrayList<CreateResponse>();
		try {
			JSONObject userData = new JSONObject();
			{ // Fill json elements from the method params
				if(!"0".equals(strArgs[0])) {
					userData.put("referringTo", strArgs[0]);
				}
				userData.put("patientName", strArgs[1]);
				userData.put("urgency", "ASAP");
//				if(!"0".equals(strArgs[3])) {
//					userData.put("patientAge", strArgs[3]);
//				}
				userData.put("patientContactPhone", strArgs[2]);
				userData.put("patientContactEmail", strArgs[3]);
				userData.put("patientContactAddress", strArgs[4]);
				userData.put("reason", strArgs[5]);
				userData.put("referringToEntry", strArgs[6]);
			}
			userData.put("requestId", generateRandomReqId());
			JSONObject jsonCreateResponse = HttpClientUtil.sendHttpPost(
					GPRConstants.API_URL + GPRConstants.CREATE_URL, token,
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
