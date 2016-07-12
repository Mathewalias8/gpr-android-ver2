package gpr.com.gprapplication.service;


import org.apache.http.auth.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.BaseModel;
import gpr.com.gprapplication.service.datamodel.Suggest;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;
import gpr.com.gprapplication.utility.ImageUtilities;

/**
 * This class works for both the AutoComplete box in the New referral form
 * and also for the filter on the Physician search dialog box.
 */
public class PhysicianListService extends AbstractRESTService {
	public static final String CLASS_NAME = "PhyListService";

	private int offset = 0;

	public PhysicianListService(String token, int offset) throws GPRException {
		this.token = token;
		this.offset = offset;
	}

	@Override
	public List<Suggest> getParseJson(String... strings) throws GPRException, AuthenticationException {
		final String TAG = "getParseJson";
		List<Suggest> listData = new ArrayList<Suggest>();
		try {
			JSONObject userData = new JSONObject();
			userData.put("name", strings[0]);
			userData.put("requestId", this.reqeustId);
			JSONArray filterArray = new JSONArray();
			//If 1 Filter
			if(strings.length > 2){
				JSONObject filterData = new JSONObject();
				filterData.put("filterName", strings[1]);
				filterData.put("id", strings[2]);
				filterArray.put(filterData);
			}
			//If 2 Filters
			if(strings.length > 4){
				JSONObject filterData = new JSONObject();
				filterData.put("filterName", strings[3]);
				filterData.put("id", strings[4]);
				filterArray.put(filterData);
			}
			//If 3 Filters
			if(strings.length > 6){
				JSONObject filterData = new JSONObject();
				filterData.put("filterName", strings[5]);
				filterData.put("id", strings[6]);
				filterArray.put(filterData);
			}
			
			if(strings.length > 8){
				JSONObject filterData = new JSONObject();
				filterData.put("filterName", strings[7]);
				filterData.put("text", strings[8]);
				filterArray.put(filterData);
			}
			
			if(filterArray.length() > 0){
				userData.put("filters", filterArray);
			}
			
			userData.put("offset", offset);
			userData.put("limit", GPRConstants.REFERRAL_LIST_SIZE);
			
			JSONObject jsonResponse = HttpClientUtil.sendHttpPost(
					GPRConstants.API_URL + GPRConstants.PHY_BY_NAME_URL, token,
					userData);

			JSONArray jsonArray = jsonResponse.getJSONArray("result");
			String imageUrlPrefix = jsonResponse.getString("imageUrlPrefix");
//			DataHolder.lastSearchTotalCount = jsonResponse.getLong("count");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject r = jsonArray.getJSONObject(i);
				String enrollmentId = r.getString("id");
				String fullName = r.getString("fullName");
				String imageName = r.getString("profileImage");
				String location = r.getString("location");
				String speciality = r.getString("speciality");
				listData.add(new Suggest(enrollmentId, fullName, ImageUtilities
					.formUrl(imageUrlPrefix, imageName), location, speciality));
			}

		}  catch (JSONException e) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e, CLASS_NAME, TAG);
			throw ex;
		}
		return listData;

	}
}
