package gpr.com.gprapplication.service;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.Physician;
import gpr.com.gprapplication.utility.ExtractFromJsonServiceUtil;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public class GetPhysicianDetailService extends AbstractRESTService {
	public static final String CLASS_NAME = "PhysicianDetailService";

	public GetPhysicianDetailService(String token) throws GPRException {
		this.token = token;
	}

	@Override
	public List<Physician> getParseJson(String... strArgs) throws GPRException{
		final String TAG = "getParseJson";
		List<Physician> listData = new ArrayList<Physician>();
		try {
			JSONObject userData = new JSONObject();
			if (null != strArgs[0]) {
				userData.put("id", strArgs[0]);
			} else { // If Details of the logged in User.
				userData.put("id", getEnrollmentId());
			}
			userData.put("requestId", this.reqeustId);
			JSONObject jsonResponse = HttpClientUtil.sendHttpPost(
					GPRConstants.API_URL
							+ GPRConstants.PHY_DETAIL_URL, token,
					userData);

			JSONObject r = jsonResponse.getJSONObject(("result"));
			String baseImgUrl = jsonResponse.getString("imageUrlPrefix");
			Physician phy = ExtractFromJsonServiceUtil
					.extractPhysicianFields(r);
			phy.setImageBaseUrl(baseImgUrl);
			listData.add(phy);

		} catch (Exception e1) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e1, CLASS_NAME, TAG);
			throw ex;
		}
		return listData;

	}
}

