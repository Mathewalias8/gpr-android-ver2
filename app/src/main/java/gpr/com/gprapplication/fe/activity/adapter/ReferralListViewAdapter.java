package gpr.com.gprapplication.fe.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.fragment.BaseReferralListFragment;
import gpr.com.gprapplication.fe.activity.fragment.dummy.DummyContent.DummyItem;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.Referral_Status;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link BaseReferralListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public abstract class ReferralListViewAdapter extends RecyclerView.Adapter<ReferralListViewAdapter.ViewHolder> {

    private final List<Referral> mValues;
    private final BaseReferralListFragment.OnListFragmentInteractionListener mListener;
    boolean isIncoming ;

    public ReferralListViewAdapter(List<Referral> items, BaseReferralListFragment.OnListFragmentInteractionListener listener, boolean isIncoming) {
        mValues = items;
        mListener = listener;
        this.isIncoming = isIncoming;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_referral_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

//        holder.mPhysicianImage.setImageResource(R.drawable.doctor_icon);
        String content;
        //based on whether it is incoming or outgoing this will have to change
        if ( isIncoming ) {
            content = " " + holder.mItem.getReferringFrom().getFullName() + " | " + holder.mItem.getReferringFrom().getLocation() + "  \n " +
                    holder.mItem.getReferringFrom().getSpeciality() + "\n";
            ImageLoader.getInstance().displayImage(holder.mItem.getReferringFrom().getProfileImageUrl(), holder.mPhysicianImage);

        }
        else {
            content = " " + holder.mItem.getReferringTo().getFullName() + " | " + holder.mItem.getReferringTo().getLocation() + "  \n " +
                    holder.mItem.getReferringTo().getSpeciality() + "\n";
            ImageLoader.getInstance().displayImage(holder.mItem.getReferringTo().getProfileImageUrl(), holder.mPhysicianImage);

        }
        holder.mContentView.setText(content);
//        holder.mItem.setStatus(DummyReferrals.Referral_Status.ACCEPTED.toString());

        int statusImageId = R.drawable.referral_accepted;
        if ( holder.mItem.getStatus().equals(Referral_Status.REFERRAL_ACCEPTED.toString()))
            statusImageId = R.drawable.referral_accepted;
        if ( holder.mItem.getStatus().equals(Referral_Status.REFERRAL_CREATED.toString()) ||
                holder.mItem.getStatus().equals(Referral_Status.REFERRAL_SENT.toString()))
            statusImageId = R.drawable.referral_default;
//        if ( holder.mItem.getStatus().equals(DummyReferrals.Referral_Status.BOUNCED.toString()))
//            statusImageId = R.drawable.referral_bounced;
        if ( holder.mItem.getStatus().equals(Referral_Status.REFERRAL_FORWARDED.toString()))
            statusImageId = R.drawable.referral_forward;
        if ( holder.mItem.getStatus().equals(Referral_Status.REFERRAL_REJECTED.toString()))
            statusImageId = R.drawable.referral_rejected;

        holder.mStatusImage.setImageResource(statusImageId);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        if ( position >= getItemCount() -1 ){
            loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPhysicianImage;
        public final TextView mContentView;
        public final ImageView mStatusImage;

        public Referral mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPhysicianImage = (ImageView) view.findViewById(R.id.physicianPicture);
            mContentView = (TextView) view.findViewById(R.id.referralInfoTxt);
            mStatusImage = (ImageView) view.findViewById(R.id.statusIcon);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public List<Referral> getData() {
        return mValues;
    }

    public void clear() { mValues.clear();}

    public void addAll(List<Referral> data ) { mValues.addAll(data);}

    public abstract void loadMore() ;
}
