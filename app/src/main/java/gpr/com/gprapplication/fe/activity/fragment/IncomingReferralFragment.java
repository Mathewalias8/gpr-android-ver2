package gpr.com.gprapplication.fe.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.adapter.ReferralListViewAdapter;
import gpr.com.gprapplication.service.asynctask.IncomingReferralListTask;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.utility.CredentialManager;
import gpr.com.gprapplication.utility.GPRConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class IncomingReferralFragment extends BaseReferralListFragment implements GenericCallback<List<Referral>>{



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IncomingReferralFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IncomingReferralFragment newInstance(int columnCount) {
        IncomingReferralFragment fragment = new IncomingReferralFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incoming_referral, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            listAdapter = new ReferralListViewAdapter(new ArrayList<Referral>(), mListener,true){
                @Override
                public void loadMore() {
                    fillIncomingReferralList();
                }
            };
            recyclerView.setAdapter(listAdapter);


        }
        firstPage = true;
        isLoading = false;
        lastPageRetrieved = false;
        fillIncomingReferralList();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void fillIncomingReferralList(){
//        ListMetaData md = DataHolder.getIncomingListMetaData();
        if (!isLoading && !lastPageRetrieved ) {
            IncomingReferralListTask task = new IncomingReferralListTask();
            isLoading = true;
            if (firstPage ) firstPage = false;

            task.setCallback(this);
            Date fetchTime = new Date();
            fetchTime.setYear(1970);
            task.execute(CredentialManager.getInstance().get(getActivity()),
                    Long.toString(0), Integer.toString(listAdapter.getItemCount()),
                    Integer.toString(GPRConstants.REFERRAL_LIST_SIZE));
        }
    }




}
