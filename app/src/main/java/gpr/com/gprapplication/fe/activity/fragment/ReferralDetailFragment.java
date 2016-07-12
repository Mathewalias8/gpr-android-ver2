package gpr.com.gprapplication.fe.activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.service.asynctask.ForwardReferralServiceTask;
import gpr.com.gprapplication.service.asynctask.UpdateReferralStatusServiceTask;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.Referral_Status;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReferralDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReferralDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferralDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Referral referral;
    private boolean isIncoming;

    private OnFragmentInteractionListener mListener;

    public ReferralDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReferralDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReferralDetailFragment newInstance(String param1, String param2) {
        ReferralDetailFragment fragment = new ReferralDetailFragment();
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
        final View view = inflater.inflate(R.layout.fragment_referral_detail, container, false);
        updateDetail(view);

        view.findViewById(R.id.referral_accept_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralAccepted(view);
            }
        });

        view.findViewById(R.id.referral_forward_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralForwarded(view);
            }
        });

        view.findViewById(R.id.referral_reject_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralRejected(view);
            }
        });

        return view;
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

    public void referralRejected(final View view) {
//        referral.setStatus(DummyReferrals.Referral_Status.REJECTED.toString());
//        updateDetail(view);

        UpdateReferralStatusServiceTask task = new UpdateReferralStatusServiceTask(this.getActivity(), referral,Referral_Status.REFERRAL_REJECTED.toString());
        task.setCallback(new GenericCallback<List<CreateResponse>>() {
            @Override
            public void onComplete(List<CreateResponse> response) {
                referral.setStatus(Referral_Status.REFERRAL_REJECTED.toString());

                updateDetail(view);


            }

            @Override
            public void onError(List<CreateResponse> response, GPRException exception) {

            }

            @Override
            public void onCancel() {

            }
        });
        task.execute();

    }

    public void referralAccepted(final View view) {
////        referral.setStatus(DummyReferrals.Referral_Status.ACCEPTED.toString());
//        updateDetail(view);

        UpdateReferralStatusServiceTask task = new UpdateReferralStatusServiceTask(this.getActivity(), referral,Referral_Status.REFERRAL_ACCEPTED.toString());
        task.setCallback(new GenericCallback<List<CreateResponse>>() {
            @Override
            public void onComplete(List<CreateResponse> response) {
                referral.setStatus(Referral_Status.REFERRAL_ACCEPTED.toString());

                updateDetail(view);


            }

            @Override
            public void onError(List<CreateResponse> response, GPRException exception) {

            }

            @Override
            public void onCancel() {

            }
        });
        task.execute();

    }

    public void referralForwarded(final View view) {
        SimpleEnrollment referringTo = new SimpleEnrollment();
        referringTo.setFullName("Mathew Alias");
        referringTo.setId(1L);
        ForwardReferralServiceTask task = new ForwardReferralServiceTask(this.getActivity(), referral,referringTo);
        task.setCallback(new GenericCallback<List<CreateResponse>>() {
            @Override
            public void onComplete(List<CreateResponse> response) {
                referral.setStatus(Referral_Status.REFERRAL_FORWARDED.toString());

                updateDetail(view);


            }

            @Override
            public void onError(List<CreateResponse> response, GPRException exception) {

            }

            @Override
            public void onCancel() {

            }
        });
        task.execute();

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

    public void setReferralModel(Referral model ) {
        this.referral = model;

    }

    public void updateDetail(View view ) {
        ImageView physicianPicView =  (ImageView) view.findViewById(R.id.physicianPicture);
        TextView referralInfoTextView =  (TextView) view.findViewById(R.id.referralInfoTxt);
        TextView patientNameTextView  =  (TextView) view.findViewById(R.id.patientNameText);
        TextView patientReasonTextView =  (TextView) view.findViewById(R.id.patientReasonText);

        TextView statusTextView =  (TextView) view.findViewById(R.id.statusText);
        ImageView statusPicView =  (ImageView) view.findViewById(R.id.statusPicture);


        String content;
        if (  isIncoming ) {
            content = " " + referral.getReferringFrom().getFullName() + " | " + referral.getReferringFrom().getLocation() + "  \n " +
                    referral.getReferringFrom().getSpeciality() + "\n";
            ImageLoader.getInstance().displayImage(referral.getReferringFrom().getProfileImageUrl(), physicianPicView);

        }
        else {
            content = " " + referral.getReferringTo().getFullName() + " | " + referral.getReferringTo().getLocation() + "  \n " +
                    referral.getReferringTo().getSpeciality() + "\n";
            ImageLoader.getInstance().displayImage(referral.getReferringTo().getProfileImageUrl(), physicianPicView);

        }

        referralInfoTextView.setText(content);
        patientNameTextView.setText( referral.getPatientName());
        patientReasonTextView.setText( referral.getReason());

        int statusImageId = R.drawable.referral_accepted;
        if ( referral.getStatus().equals(Referral_Status.REFERRAL_ACCEPTED.toString()))
            statusImageId = R.drawable.referral_accepted;
        if ( referral.getStatus().equals(Referral_Status.REFERRAL_SENT.toString()))
            statusImageId = R.drawable.referral_default;
//        if ( referral.getStatus().equals(DummyReferrals.Referral_Status.BOUNCED.toString()))
//            statusImageId = R.drawable.referral_bounced;
        if ( referral.getStatus().equals(Referral_Status.REFERRAL_FORWARDED.toString()))
            statusImageId = R.drawable.referral_forward;
        if ( referral.getStatus().equals(Referral_Status.REFERRAL_REJECTED.toString()))
            statusImageId = R.drawable.referral_rejected;

        statusPicView.setImageResource(statusImageId);
        statusTextView.setText(referral.getStatus());

        //if incoming and no action has been taken , show the action options
        if ( isIncoming &&  referral.getStatus().equals(Referral_Status.REFERRAL_SENT.toString() )) {
            view.findViewById(R.id.referralActionLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.statusLayout).setVisibility(View.GONE);

        }
        else {
            view.findViewById(R.id.referralActionLayout).setVisibility(View.GONE);
            view.findViewById(R.id.statusLayout).setVisibility(View.VISIBLE);


        }
    }

    public void setReferralType(boolean isIncoming){
        this.isIncoming = isIncoming;
    }

}
