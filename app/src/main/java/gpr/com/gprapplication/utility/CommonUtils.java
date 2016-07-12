package gpr.com.gprapplication.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager.BadTokenException;


import java.util.Calendar;
import java.util.Locale;

import gpr.com.gprapplication.R;

public class CommonUtils {
	private static final String TAG = "CommonUtils";
	public static final String RECENT_LIST_LIMIT = "10";

	public static long getXDaysBackInMillis(int x) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -25);
		return cal.getTimeInMillis();
	}

	public static ProgressDialog createProgressDialog(Context mContext) {
		ProgressDialog dialog = new ProgressDialog(mContext);
		try {
			dialog.show();
		} catch (BadTokenException e) {

		}
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.progressdialog);
//		 dialog.setMessage(Message);
		return dialog;
	}

	public static String makeBlank(String input) {
		final String BLANK = "";
		final String ZERO = "0";
		final String NULL = "null";
		if (null == input || ZERO.equals(input) || NULL.equals(input))
			return BLANK;
		else
			return input;
	}

	public static boolean isValidEmailAddress(String email) {
		boolean stricterFilter = true;
		String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
		String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
		String emailRegex = stricterFilter ? stricterFilterString : laxString;
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isEmpty(String string) {
		if (null == string)
			return true;
		else if ("".equals(string.trim()))
			return true;
		else
			return false;
	}

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	public static void track(Activity activity, String path) {
//		Tracker t = ((GPRApplication) activity.getApplication())
//				.getTracker(TrackerName.APP_TRACKER);
//		t.setScreenName(path);
//		t.send(new HitBuilders.AppViewBuilder().build());
	}

	public static void trackReferral(Activity activity, String path) {
//		Tracker t = ((GPRApplication) activity.getApplication())
//				.getTracker(TrackerName.APP_TRACKER);
//		t.setScreenName(path);
//		t.send(new HitBuilders.EventBuilder()
//				.setCategory("REFERRAL")
//				.setAction("SENT")
//				.build());
	}
	
	public static String getCountryCodeIso(Context context) {
		String countryCodeIso = null;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if(telephonyManager != null) {
				countryCodeIso = telephonyManager.getNetworkCountryIso();
				if(TextUtils.isEmpty(countryCodeIso)) {
					countryCodeIso = telephonyManager.getSimCountryIso();
				}
			}
		}
		catch(Exception e) {
		}
		
		if(TextUtils.isEmpty(countryCodeIso)) {
			countryCodeIso =  context.getResources().getConfiguration().locale.getCountry();
		}
		
		if(!TextUtils.isEmpty(countryCodeIso)) {
			Locale locale = new Locale("", countryCodeIso);
		    return locale.getISO3Country();
		}
		else {
			return "";
		}
	}
	
//	public static void saveSearchPreferences(Context context, SearchFilter filter) {
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		Editor editor = preferences.edit();
//		editor.putLong(GPRConstants.PREF_SEARCH_SPECIALITY, filter.getSpecialityId());
//		editor.putLong(GPRConstants.PREF_SEARCH_SUPER_SPECIALITY, filter.getSuperSpecialityId());
//		editor.putString(GPRConstants.PREF_SEARCH_STATE, filter.getState());
//		editor.putLong(GPRConstants.PREF_SEARCH_COUNTRY, filter.getCountryId());
//		editor.commit();
//	}
//
//	public static SearchFilter getSavedSearchPreferences(Context context) {
//		SearchFilter filter = new SearchFilter();
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		filter.setSpecialityId(preferences.getLong(GPRConstants.PREF_SEARCH_SPECIALITY, 0));
//		filter.setSuperSpecialityId(preferences.getLong(GPRConstants.PREF_SEARCH_SUPER_SPECIALITY, 0));
//		filter.setState(preferences.getString(GPRConstants.PREF_SEARCH_STATE, ""));
//		filter.setCountryId(preferences.getLong(GPRConstants.PREF_SEARCH_COUNTRY, 0));
//
//		return filter;
//	}
	
	public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
	
	public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
        String registrationId = prefs.getString(GPRConstants.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(GPRConstants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = CommonUtils.getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }
	
	public static void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		int appVersion = CommonUtils.getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(GPRConstants.PROPERTY_REG_ID, regId);
		editor.putInt(GPRConstants.PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
}
