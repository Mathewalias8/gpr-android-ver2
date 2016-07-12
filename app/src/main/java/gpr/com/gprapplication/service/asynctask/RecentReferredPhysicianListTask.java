package gpr.com.gprapplication.service.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.auth.AuthenticationException;

import java.util.List;

import gpr.com.gprapplication.service.RecentReferredPhysicianListService;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;
import gpr.com.gprapplication.utility.GPRConstants;

/**
 * Created by jaya on 2/25/2016.
 */
public class RecentReferredPhysicianListTask  extends BaseAsyncTask<String, String, List<SimpleEnrollment>> {

        ProgressDialog dialog;
        public static final String TAG = "RecentReferredPhysicianListTask";

        @Override
        protected void onPreExecute() {
            //// TODO: 2/25/2016  
//            Log.i(TAG, "Loading recent referrals...");
//            if (dialog == null) {
//                dialog = CommonUtils.createProgressDialog(getActivity());
//                dialog.show();
//            } else {
//                dialog.show();
//            }
        }

        protected List<SimpleEnrollment> doInBackground(String... param) {
//            Log.i(TAG, "Attempting to load recent referrals");
            publishProgress(param);
            List<SimpleEnrollment> recentReferrals = null;
            try {
                String limit = GPRConstants.RECENT_LIST_LIMIT;

                recentReferrals = new RecentReferredPhysicianListService(param[0])
                        .getParseJson(limit);

            } catch (AuthenticationException e) {
                //// TODO: 2/25/2016
//                ((BaseFragmentActivity) getActivity()).onSigninClick(null);
            } catch (Exception e) {
                GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e,
                        TAG, TAG);
//                ex.takeAction("Failed to Load Recent Referrals!", getActivity());
                return null;
            }
            return recentReferrals;
        }
//
//        protected void onPostExecute(List<SimpleEnrollment> ret) {
//            Log.i(TAG, "Successfully completed loading recent referrals");
//            if (ret != null) {
//                List<SimpleEnrollment> listOfRecent = DataHolder
//                        .getListOfRecentReferrals();
//
//                if (listOfRecent == null || listOfRecent.size() == 0)
//                    listOfRecent.addAll(ret);
//                else {
//                    listOfRecent.clear();
//                    listOfRecent.addAll(ret);
//                }
//
//                // Populate List View.
//                populateListView(listOfRecent);
//            } else {
//                Log.e(TAG, "Failed to load recent referrals");
//            }
//            dialog.dismiss();
//        }
//
//        @Override
//        protected void onCancelled(List<SimpleEnrollment> result) {
//            super.onCancelled(result);
//        }

//    }

}
