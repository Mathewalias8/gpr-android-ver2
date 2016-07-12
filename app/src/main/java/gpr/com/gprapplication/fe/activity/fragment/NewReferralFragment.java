package gpr.com.gprapplication.fe.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.BasicPhysicianSearchActivity;
import gpr.com.gprapplication.fe.activity.PhysicianDetailActivity;
import gpr.com.gprapplication.fe.activity.adapter.RecentReferralPhysicianListViewAdapter;
import gpr.com.gprapplication.service.asynctask.CreateReferralServiceTask;
import gpr.com.gprapplication.service.asynctask.RecentReferredPhysicianListTask;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;
import gpr.com.gprapplication.service.datamodel.Suggest;
import gpr.com.gprapplication.utility.CommonUtils;
import gpr.com.gprapplication.utility.CredentialManager;
import gpr.com.gprapplication.utility.GPRConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewReferralFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewReferralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewReferralFragment extends Fragment implements GenericCallback<List<SimpleEnrollment>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecentReferralPhysicianListViewAdapter recentReferralPhysicianListViewAdapter;
    private  SimpleEnrollment selectedPhysician;
    private Referral currentReferral;

    private static final int PHYSICIAN_SEARCH_REQUEST = 1;


    public NewReferralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewReferralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewReferralFragment newInstance(String param1, String param2) {
        NewReferralFragment fragment = new NewReferralFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_referral, container, false);
        TwoWayView recentPhsyicianView =  (TwoWayView) view.findViewById(R.id.recentReferralListView);
        recentPhsyicianView.setAdapter( recentReferralPhysicianListViewAdapter =
                new RecentReferralPhysicianListViewAdapter(getContext(),new  ArrayList<SimpleEnrollment>(), null));
        recentPhsyicianView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPhysician = recentReferralPhysicianListViewAdapter.getItem(position);
                updateReferringToPhysicianInfo();

            }
        });

        recentPhsyicianView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPhysicianDetail( recentReferralPhysicianListViewAdapter.getItem(position));
                return true;
            }
        });

        if ( recentReferralPhysicianListViewAdapter.getCount() == 0) {
            fillRecentReferralList();
        }

        view.findViewById(R.id.sendReferralButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReferral(v);
            }
        });

        view.findViewById(R.id.physicianNameSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicSearchPhysicians(v);

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void populateListView(final List<SimpleEnrollment> listIncoming) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recentReferralPhysicianListViewAdapter.clear();
                if ( listIncoming != null )
                    recentReferralPhysicianListViewAdapter.addAll(listIncoming);
            }
        });


    }

    private void fillRecentReferralList() {

        RecentReferredPhysicianListTask task = new RecentReferredPhysicianListTask();
        task.setCallback(this);
        task.execute(CredentialManager.getInstance().get(getActivity()));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onComplete(List<SimpleEnrollment> response) {
        populateListView(response);

    }

    @Override
    public void onError(List<SimpleEnrollment> response, GPRException exception) {

    }


    private boolean copyDataAndValidate() {
        TextView patientNameTextView =  (TextView) getActivity().findViewById(R.id.patient_name_text);
        TextView patientPhoneTextView = (TextView) getActivity().findViewById(R.id.patient_phone_text);
        TextView patientEmailTextView = (TextView) getActivity().findViewById(R.id.patient_email_text);
        TextView patientReasonTextView = (TextView) getActivity().findViewById(R.id.patient_notes_text);

        currentReferral = new Referral();
        currentReferral.setPatientName(patientNameTextView.getText().toString());
        currentReferral.setPatientContactAddress("");
        currentReferral.setPatientContactPhone(patientPhoneTextView.getText().toString());
        currentReferral.setPatientContactEmail(patientEmailTextView.getText().toString());
        currentReferral.setReason(patientReasonTextView.getText().toString());
        currentReferral.setReferringTo(selectedPhysician);
//        newReferral.setPatientAge();


		/* Validate */
        boolean isValid = true;
        View phyImgName = (View) getActivity().findViewById(R.id.physician_img_name);
        if (selectedPhysician == null) {
            phyImgName.setBackgroundResource(R.drawable.back_red);
            isValid = false;
        } else {
            phyImgName.setBackgroundResource(R.drawable.back_blue);
        }


        if (TextUtils.isEmpty(currentReferral.getPatientName())) {
            patientNameTextView.setBackgroundResource(R.drawable.back_red);
            isValid = false;
        } else {
            patientNameTextView.setBackgroundResource(R.drawable.back_blue);
        }


        if ("".equalsIgnoreCase(currentReferral.getPatientContactEmail())
                && !CommonUtils.isValidEmailAddress(currentReferral.getPatientContactEmail()) &&
                TextUtils.isEmpty(currentReferral.getPatientContactPhone()) ) {
            patientEmailTextView.setBackgroundResource(R.drawable.back_red);
            patientPhoneTextView.setBackgroundResource(R.drawable.back_red);

            isValid = false;
        } else {
            patientEmailTextView.setBackgroundResource(R.drawable.back_blue);
            patientPhoneTextView.setBackgroundResource(R.drawable.back_blue);

        }


        if (TextUtils.isEmpty(currentReferral.getReason())) {
            patientReasonTextView.setBackgroundResource(R.drawable.back_red);
            isValid = false;
        } else {
            patientReasonTextView.setBackgroundResource(R.drawable.back_blue);
        }
        if (!isValid) {
            Toast.makeText(getContext(), "Please fill highlighted fields", Toast.LENGTH_LONG).show();
        }
        return isValid;
    }

    private void updateReferringToPhysicianInfo(){
        TextView referringTo =  (TextView) getActivity().findViewById(R.id.physicianNameSearch);
        ImageView selectedPhysicianImage  = (ImageView)getActivity().findViewById(R.id.img_selected_physician);
        selectedPhysicianImage.setVisibility(View.VISIBLE);
        referringTo.setText(selectedPhysician.getFullName());
        ImageLoader.getInstance().displayImage(selectedPhysician.getProfileImageUrl(), selectedPhysicianImage);
        selectedPhysicianImage.setTag(selectedPhysician.getId());


    }

    public void sendReferral(View sendButton){
        if (copyDataAndValidate())
        {
            CreateReferralServiceTask task = new CreateReferralServiceTask(getActivity(),currentReferral);
            task.setCallback(new GenericCallback<List<CreateResponse>>() {
                @Override
                public void onComplete(List<CreateResponse> response) {
                    fillRecentReferralList();
                    Toast.makeText(getContext(), "Referral Sent",
                            Toast.LENGTH_SHORT).show();
                    clearForm();
                }

                @Override
                public void onError(List<CreateResponse> response, GPRException exception) {

                }

                @Override
                public void onCancel() {

                }
            });
            task.execute(CredentialManager.getInstance().get(getActivity()));


        }

    }


    private void clearForm() {
        ((TextView) getActivity().findViewById(R.id.patient_name_text)).setText("");
        ((TextView) getActivity().findViewById(R.id.patient_phone_text)).setText("");
        ((TextView) getActivity().findViewById(R.id.patient_email_text)).setText("");
        ((TextView) getActivity().findViewById(R.id.patient_notes_text)).setText("");
        ((TextView) getActivity().findViewById(R.id.physicianNameSearch)).setText("");
        ((ImageView)getActivity().findViewById(R.id.img_selected_physician)).setVisibility(View.GONE);

        selectedPhysician = null;
        currentReferral = null;

    }

    public void basicSearchPhysicians(View view) {
        Intent intent = new Intent(getActivity(), BasicPhysicianSearchActivity.class);
        intent.putExtra(GPRConstants.EXTRA_SEARCH_TERM, ((TextView)view) .getText().toString());
        startActivityForResult(intent, PHYSICIAN_SEARCH_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i(CLASS_NAME, requestCode + "--" + resultCode);
        if (requestCode == PHYSICIAN_SEARCH_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if(data.hasExtra(GPRConstants.EXTRA_SUGGEST)) {
                    Suggest suggest = (Suggest) data.getSerializableExtra(GPRConstants.EXTRA_SUGGEST);
                    if(suggest != null) {
                        selectedPhysician = new SimpleEnrollment();
                        selectedPhysician.setFullName(suggest.getName());
                        selectedPhysician.setProfileImageUrl(suggest.getImageUrl());
                        selectedPhysician.setId(new Long (suggest.getId()));

                        updateReferringToPhysicianInfo();

                    }
                }
            }
        }

    }


    public void showPhysicianDetail(SimpleEnrollment physician) {
        Log.i("NewReferralFragment", "showPhysicianDetail");
        Intent phyDetailInfo = new Intent(getActivity(),
                PhysicianDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GPRConstants.PHYSICAN, physician.getId());
        phyDetailInfo.putExtras(bundle);
        startActivity(phyDetailInfo);
    }
}
