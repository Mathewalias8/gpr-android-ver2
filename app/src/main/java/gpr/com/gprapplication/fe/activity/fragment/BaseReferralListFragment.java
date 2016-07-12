package gpr.com.gprapplication.fe.activity.fragment;


import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.fe.activity.adapter.ReferralListViewAdapter;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.Referral_Status;
import gpr.com.gprapplication.utility.GPRConstants;

/**
 * Created by jaya on 2/22/2016.
 */
public abstract class BaseReferralListFragment extends Fragment  implements GenericCallback<List<Referral>>{
    // TODO: Customize parameter argument names
    protected static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    protected int mColumnCount = 1;
    protected OnListFragmentInteractionListener mListener;
    protected ReferralListViewAdapter listAdapter;
    List<Referral> originalRefferralList = null ;
    boolean firstPage = true;
    boolean isLoading = false;
    boolean lastPageRetrieved = false;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Referral referral);
    }

    public void filterReferrals(Referral_Status status)
    {
        if (originalRefferralList == null) {
            originalRefferralList = new ArrayList<>();
            originalRefferralList.addAll(listAdapter.getData());
        }
        List<Referral> filteredReferral = new ArrayList<>();
        for (Referral referral:originalRefferralList ) {
            if  ( referral.getStatus().equals(status.toString()))
                filteredReferral.add(referral);

        }
        listAdapter.clear();
        listAdapter.addAll(filteredReferral);
        listAdapter.notifyDataSetChanged();


   }

    @Override
    public void onCancel() {

    }

    @Override
    public void onComplete(List<Referral> response) {
        populateListView(response);
        isLoading = false;


    }

    @Override
    public void onError(List<Referral> response, GPRException exception) {
        isLoading = false;


    }

    private void populateListView(final List<Referral> listIncoming) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (firstPage)  listAdapter.clear();
                listAdapter.addAll(listIncoming);
                listAdapter.notifyDataSetChanged();
                if (listIncoming.size() < GPRConstants.REFERRAL_LIST_SIZE) // last set
                {
                    lastPageRetrieved = true;
                }
            }
        });


    }
}
