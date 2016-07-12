package gpr.com.gprapplication.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CredentialManager {
	private static final String TAG = "CredentialManager";
	
	private static CredentialManager instance;
	private static String token = null;
	
	private CredentialManager() {}
	
	public static CredentialManager getInstance() {
		if(instance == null) {
			instance = new CredentialManager();
		}
		
		return instance;
	}

	public String get(Context context) {
		if(CredentialManager.token == null && context != null) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			CredentialManager.token =  preferences.getString(GPRConstants.TOKEN, null);
		}
		
		return CredentialManager.token;
	}
	
	public void remove() {
		CredentialManager.token = null;
	}
}
