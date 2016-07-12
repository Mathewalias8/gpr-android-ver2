package gpr.com.gprapplication.service.asynctask;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gpr.com.gprapplication.service.APIService;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.User;
import gpr.com.gprapplication.utility.GPRConstants;

public class UserLoginTask extends BaseAsyncTask<String, Void, User> {

	private static final String TAG = "UserLoginTask";


	@Override
	protected User doInBackground(String... params) {
		User user = null;
		try {

			user = loginUser(params[0],params[1],params[2]);
		} catch (Exception e) {
		}

		return user;
	}



	public User loginUser(String userId, String password,String tenantName) {
		User user = null;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("j_username", cleanse(userId));
			headers.put("j_password", password);

			headers.put("tenant_name", tenantName);

			String result = APIService.getInstance().post(GPRConstants.LOGIN_API, "", headers);

			if(result != null) {
				JSONObject resultObject = new JSONObject(result);
				if(resultObject != null && resultObject.has("username")) {
					user = new User();
					user.setToken(resultObject.getString("token"));
					user.setUsername(resultObject.getString("username"));
					user.setFullName(resultObject.getString("fullName"));
					user.setEnrollmentId(resultObject.getLong("enrollmentId"));
					user.setProfileImage(resultObject.getString("profileImage"));
					user.setImageUrlPrefix(resultObject.getString("imageUrlPrefix"));
				}
			}
		}
		catch(Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return user;
	}

	private String cleanse(String s) {
		if(s == null) {
			s = "";
		}
		else if(s.indexOf("@") == -1) {
			s = s.replaceAll("\\D", "");
		}

		return s;
	}
}
