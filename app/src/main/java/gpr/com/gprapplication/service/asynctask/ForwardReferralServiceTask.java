package gpr.com.gprapplication.service.asynctask;

import android.app.Activity;
import android.util.Log;

import java.util.List;

import gpr.com.gprapplication.service.ForwardReferralService;
import gpr.com.gprapplication.service.UpdateReferralStatusService;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;
import gpr.com.gprapplication.utility.CredentialManager;

public class ForwardReferralServiceTask extends
		BaseAsyncTask<String, String, List<CreateResponse>> {

	public static final String CLASS_NAME = "JsonUpdateReferralStatusServiceTask";
	private Activity activity;
	private Referral currentReferral;
	private SimpleEnrollment referringTo;


	public ForwardReferralServiceTask(Activity activity, Referral currentReferral,SimpleEnrollment referringTo) {
		super();
		this.activity = activity;
		this.currentReferral = currentReferral;
		this.referringTo = referringTo;
//		toast  = new Toast(getCib
//				activity, Toast.LENGTH_LONG	);
	}

	@Override
	protected void onPreExecute() {
		final String TAG = "onPreExecute";
		Log.i(TAG, "Updating referral...");

		/* Show Progress dialog */
//		superActivityToast.setText("Sending...");
//		superActivityToast.setIndeterminate(true);
//		superActivityToast.setMaxProgress(100);
//		superActivityToast.setProgress(20);
//		superActivityToast.setAnimations(SuperToast.Animations.POPUP);
//		superActivityToast.show();
	}

	protected List<CreateResponse> doInBackground(String... param) {
		final String TAG = "doInBackground";
		Log.i(TAG, "Attempting to update  referral status");
//		superActivityToast.setProgress(40);
		publishProgress(param);
		List<CreateResponse> ret;
		try {
			ForwardReferralService service = new ForwardReferralService(CredentialManager.getInstance().get(null));
			ret = service.getParseJson(new Long ( currentReferral.getId()).toString(), referringTo.getId().toString(),  referringTo.getFullName());
//			superActivityToast.setProgress(60);
		} catch (Exception e) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			ex.takeAction("Send Failed!", activity);
			return null;
		}
		return ret;
	}

//	protected void onPostExecute(List<CreateResponse> ret) {
//		final String TAG = "onPostExecute";
//		Log.i(TAG, "Successfully completed sending referral");
//		if (ret != null && !ret.isEmpty()) {
//			CreateResponse response = ret.get(0);
//			if ("CREATED".equalsIgnoreCase(response.getStatus())) {
//				if(this.callback != null) {
//					this.callback.onClearForm();
//					//showToast("Referral Sent", "UNDO CLEAR");
//					Toast.makeText(activity, "Referral Sent",
//							Toast.LENGTH_SHORT).show();
//				}
//				if(this.callback != null) {
//					this.callback.onSuccess(response);
//				}
//			} else {
//				Toast.makeText(activity, response.getMessage(), Toast.LENGTH_LONG)
//						.show();
//			}
//		} else {
//			Toast.makeText(activity, "Failed to send referral!",
//					Toast.LENGTH_LONG).show();
//			Log.e(TAG, "Failed to send referral!");
//		}
//
//		superActivityToast.setProgress(100);
//		superActivityToast.dismiss();
//	}
//
//	@SuppressWarnings("unused")
//	private void showToast(String messageLabel, String buttonActionLabel) {
//		SuperActivityToast superActivityToast = new SuperActivityToast(
//				activity, SuperToast.Type.BUTTON);
//		superActivityToast.setDuration(SuperToast.Duration.EXTRA_LONG);
//		superActivityToast.setText(messageLabel);
//		superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO,
//				buttonActionLabel);
//		superActivityToast.setOnClickWrapper(onClickWrapper);
//		superActivityToast.show();
//	}
//
//	@Override
//	protected void onCancelled(List<CreateResponse> result) {
//		super.onCancelled(result);
//		superActivityToast.setProgress(0);
//		superActivityToast.dismiss();
//	}
	
	/**
	 * The OnClickWrapper is needed to reattach SuperToast.OnClickListeners on
	 * orientation changes. It does this via a unique String tag defined in the
	 * first parameter so each OnClickWrapper's tag should be unique.
	 */
//	OnClickWrapper onClickWrapper = new OnClickWrapper("superactivitytoast",
//			new SuperToast.OnClickListener() {
//
//				@Override
//				public void onClick(View view, Parcelable token) {
//
//					View rootView = activity.findViewById(R.id.fragment_new_referral);
//					((TextView) rootView.findViewById(R.id.text_patient))
//							.setText(CommonUtils.makeBlank(currentReferral.getPatientName()));
//					((TextView) rootView
//							.findViewById(R.id.text_patient_address))
//							.setText(CommonUtils.makeBlank(currentReferral.getPatientContactAddress()));
//					((TextView) rootView.findViewById(R.id.text_patient_phone))
//							.setText(CommonUtils.makeBlank(currentReferral.getPatientContactPhone()));
//					((TextView) rootView.findViewById(R.id.text_patient_email))
//							.setText(CommonUtils.makeBlank(currentReferral.getPatientContactEmail()));
//					((TextView) rootView.findViewById(R.id.text_reason))
//							.setText(CommonUtils.makeBlank(currentReferral
//									.getReason()));
//					((TextView) rootView.findViewById(R.id.text_age))
//							.setText(CommonUtils.makeBlank(currentReferral.getPatientAge()));
//					String urgency = currentReferral.getUrgency();
//					if (urgency.equalsIgnoreCase("ASAP")) {
//						((ToggleButton) rootView.findViewById(R.id.toggleAsap))
//								.setChecked(true);
//					} else {
//						int intUrgency = Integer.parseInt(urgency);
//						Spinner daysSpinner = (Spinner) rootView
//								.findViewById(R.id.spinnerDays);
//						switch (intUrgency) {
//						case 30:
//							daysSpinner.setSelection(1);
//							break;
//						case 60:
//							daysSpinner.setSelection(2);
//							break;
//						case 90:
//							daysSpinner.setSelection(3);
//							break;
//						default:
//							break;
//						}
//					}
//				}
//
//			});
//
//	public void setCallback(CreateReferralCallback callback) {
//		this.callback = callback;
//	}

}