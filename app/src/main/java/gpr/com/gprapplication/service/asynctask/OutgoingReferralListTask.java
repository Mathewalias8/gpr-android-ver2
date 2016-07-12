package gpr.com.gprapplication.service.asynctask;

import android.app.ProgressDialog;
import android.util.Log;

import org.apache.http.auth.AuthenticationException;

import java.util.List;

import gpr.com.gprapplication.service.IncomingReferralListService;
import gpr.com.gprapplication.service.OutgoingReferralListService;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.datamodel.Referral;

public class OutgoingReferralListTask extends BaseReferralListTask {

    ProgressDialog dialog;
    public static final String CLASS_NAME = "OutgoingReferralListTask";

    @Override
    protected void onPreExecute() {
//        final String TAG = "onPreExecute";
//        Log.i(TAG, "Loading incoming referrals...");
//        if (dialog == null) {
//            dialog = CommonUtils.createProgressDialog(getActivity());
//            dialog.show();
//        } else {
//            dialog.show();
//        }
    }

    protected List<Referral> doInBackground(String... param) {
        final String TAG = "doInBackground";
        Log.i(TAG, "Attempting to load incoming referrals");
        publishProgress(param);
        List<Referral> incomingReferrals = null;
        try {
            incomingReferrals = new OutgoingReferralListService(param[0])
                    .getParseJson(param[1], param[2], param[3]);

        } catch (AuthenticationException e) {
//            ((BaseFragmentActivity) getActivity()).onSigninClick(null);
        } catch (Exception e) {
            GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e,
                    CLASS_NAME, TAG);
//            ex.takeAction("Failed to Load Incoming Referrals!",
//                    getActivity());
            return null;
        }
        return incomingReferrals;
    }

//    protected void onPostExecute(List<Referral> ret) {
//        final String TAG = "onPostExecute";
//        Log.i(TAG, "Successfully completed loading incoming referrals");
//        if (ret != null) {
//            if (ret.size() > 0) {
//                ListMetaData md = DataHolder.getIncomingListMetaData();
//                IncomingListArrayAdapter adapter = (IncomingListArrayAdapter) incomingReferralListView
//                        .getAdapter();
//                if (adapter.getCount() == 0) {
//                    adapter.addAll(ret);
//                    hasMore = md.getOffset() + ret.size() <  md.getTotalCount();
//                } else {
//                    if (md.getOffset() > 0) {
//                        adapter.addAll(ret);
//                        hasMore = md.getOffset() + ret.size() <  md.getTotalCount();
//                    } else {
//                        if (md.getPrevFetchTime() <= 0) {
//                            adapter.clear();
//                            adapter.addAll(ret);
//                            hasMore = md.getOffset() + ret.size() <  md.getTotalCount();
//                        } else if (md.getPrevFetchTime() > 0) {
//                            if (ret.size() > GPRConstants.REFERRAL_LIST_SIZE) {
//                                adapter.clear();
//                                adapter.addAll(ret);
//                                hasMore = true;
//                            } else {
//                                for (Referral r : ret) {
//                                    adapter.insert(r, 0);
//                                }
//                                hasMore = true;
//                            }
//                        }
//                    }
//                }
//
//                adapter.notifyDataSetChanged();
//            } else {
//                Log.i(TAG, "No new records");
//            }
//
//            mListDataInitialized = true;
//        } else {
//            GPRException ex = new GPRException(ExceptionType.SEVERE,
//                    CLASS_NAME, TAG);
//            ex.takeAction("Failed to Load Incoming Referrals!",
//                    getActivity());
//        }
//
//        dialog.dismiss();
//
//        if (swipeLayout.isRefreshing()) {
//            swipeLayout.setRefreshing(false);
//        }
//
//        onRefresh = false;
//        mLoadMore.setVisibility(View.GONE);
//    }


}