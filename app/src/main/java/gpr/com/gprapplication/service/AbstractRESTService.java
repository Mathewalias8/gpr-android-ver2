package gpr.com.gprapplication.service;

import android.util.Log;


import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.BaseModel;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.HttpClientUtil;

public abstract class AbstractRESTService {

	private static final String TAG = "AbstractRESTService";

	protected String token;
	protected int reqeustId;
	private String userId;
	private String password;
	private long enrollmentId;
	private String fullName;
	private String imageUrlPrefix;
	private String profileImage;

	protected int generateRandomReqId() {
		return new Random().nextInt(Integer.MAX_VALUE - 1);
	}

	protected String getInitLoginToken(String userId, String password) throws GPRException {
		try {
			this.userId = userId;
			this.password = password;
			this.reqeustId = generateRandomReqId();

			JSONObject jsonLoginResponse = HttpClientUtil.sendAuthHttpPost(
					GPRConstants.API_URL + GPRConstants.LOGIN_URL, userId,
					password);

			this.token = jsonLoginResponse.getString("token");
			this.enrollmentId = jsonLoginResponse.getLong("enrollmentId");
			this.fullName = jsonLoginResponse.getString("fullName");
			this.imageUrlPrefix = jsonLoginResponse.getString("imageUrlPrefix");
			this.profileImage = jsonLoginResponse.getString("profileImage");
			Log.d(TAG, token);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return this.token;
	}

	protected String getUserId() {
		return userId;
	}

	protected void setUserId(String userId) {
		this.userId = userId;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	public long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImageUrlPrefix() {
		return imageUrlPrefix;
	}

	public void setImageUrlPrefix(String imageUrlPrefix) {
		this.imageUrlPrefix = imageUrlPrefix;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	abstract protected List<? extends BaseModel> getParseJson(
			String... strArgs) throws GPRException, AuthenticationException;
}
