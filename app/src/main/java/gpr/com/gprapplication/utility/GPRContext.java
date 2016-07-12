package gpr.com.gprapplication.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;


import java.util.HashMap;
import java.util.Map;

import gpr.com.gprapplication.service.datamodel.User;

public class GPRContext {
	private static final String TAG = "GPRContext";
	
	private static GPRContext instance;
	
	private User currentUser;
	private Map<String, Fragment> fragments = new HashMap<String, Fragment>();
	
	private GPRContext() {}
	
	public User getCurrentUser(Context context) {
		if(this.currentUser == null) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			if(preferences.contains(GPRConstants.TOKEN) && !TextUtils.isEmpty(preferences.getString(GPRConstants.TOKEN, null))) {
				currentUser = new User();
				currentUser.setToken(preferences.getString(GPRConstants.TOKEN, null));
				currentUser.setUsername(preferences.getString(GPRConstants.USERNAME, null));
				currentUser.setFullName(preferences.getString(GPRConstants.FULL_NAME, null));
				currentUser.setEnrollmentId(preferences.getLong(GPRConstants.ENROLLMENT_ID, 0));
				currentUser.setProfileImage(preferences.getString(GPRConstants.PROFILE_IMAGE, null));
				currentUser.setImageUrlPrefix(preferences.getString(GPRConstants.IMAGE_URL_PREFIX, null));
			}
		}
		
		return currentUser;
	}

	public void setCurrentUser(Context context, User user) {
		this.currentUser = user;

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		if(currentUser != null) {
			editor.putString(GPRConstants.TOKEN, user.getToken());
			editor.putString(GPRConstants.USERNAME, user.getUsername());
			editor.putString(GPRConstants.FULL_NAME, user.getFullName());
			editor.putLong(GPRConstants.ENROLLMENT_ID, user.getEnrollmentId());
			editor.putString(GPRConstants.PROFILE_IMAGE, user.getProfileImage());
			editor.putString(GPRConstants.IMAGE_URL_PREFIX, user.getImageUrlPrefix());
		}
		else {
			editor.putString(GPRConstants.TOKEN, "");
			editor.putString(GPRConstants.USERNAME, "");
			editor.putString(GPRConstants.FULL_NAME, "");
			editor.putLong(GPRConstants.ENROLLMENT_ID, 0);
			editor.putString(GPRConstants.PROFILE_IMAGE, "");
			editor.putString(GPRConstants.IMAGE_URL_PREFIX, "");
		}
		editor.apply();
	}

	public static GPRContext getInstance() {
		if(instance == null) {
			instance = new GPRContext();
		}
		
		return instance;
	}
	
	public Map<String, Fragment> getFragments() {
		return fragments;
	}

	public void setFragments(Map<String, Fragment> fragments) {
		this.fragments = fragments;
	}
}
