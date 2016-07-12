package gpr.com.gprapplication.service.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


import java.util.List;

import gpr.com.gprapplication.service.GetPhysicianDetailService;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.Physician;
import gpr.com.gprapplication.utility.CommonUtils;

public class GetPhyDetailServiceTask extends BaseAsyncTask<String, String, Physician> {



	ProgressDialog dialog;
	Activity currentActivity;
	public static final String CLASS_NAME = "JsonPhyDetailServiceTask";


	public GetPhyDetailServiceTask(Activity currentActivity) {
		super();
		this.currentActivity = currentActivity;
	}

	@Override
	protected void onPreExecute() {
		final String TAG = "onPreExecute";
		Log.i(TAG, "Loading physician details...");
		if (dialog == null) {
			dialog = CommonUtils.createProgressDialog(currentActivity);
			dialog.show();
		} else {
			dialog.show();
		}
	}

	protected Physician doInBackground(String... param) {
		final String TAG = "doInBackground";
		Log.i(TAG, "Attempting to load physician details");
		publishProgress(param);
		List<Physician> phyDetails = null;
		try {
			phyDetails = new GetPhysicianDetailService(param[0])
					.getParseJson(param[1]);

		} catch (Exception e) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			return null;
		}
		return phyDetails.get(0);
	}

	protected void onPostExecute(Physician ret) {
		final String TAG = "onPostExecute";
		Log.i(TAG, "Successfully completed loading physician details");
		if (ret != null) {
			Physician selectedPhysician = ret;
			callback.onComplete(selectedPhysician);
		} else {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE,
					CLASS_NAME, TAG);
			ex.takeAction("Failed to Load Physician Details!",
					currentActivity);
		}
		dialog.dismiss();
	}

	@Override
	protected void onCancelled(Physician result) {
		super.onCancelled(result);
		dialog.cancel();
	}
}

