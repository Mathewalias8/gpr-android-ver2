package gpr.com.gprapplication.fe.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.fragment.BaseReferralListFragment;
import gpr.com.gprapplication.fe.activity.fragment.dummy.DummyContent.DummyItem;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link BaseReferralListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecentReferralPhysicianListViewAdapter extends ArrayAdapter<SimpleEnrollment> {

    private final BaseReferralListFragment.OnListFragmentInteractionListener mListener;

    public RecentReferralPhysicianListViewAdapter(Context context, List<SimpleEnrollment> items, BaseReferralListFragment.OnListFragmentInteractionListener listener) {
        super(context, R.layout.recent_referred_physician_listitem, items);
        mListener = listener;
        initImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SimpleEnrollment user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.recent_referred_physician_listitem, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.mItem = getItem(position);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), viewHolder.mPhysicianImage);

        // Return the completed view to render on screen
        return convertView;
    }


    public class ViewHolder  {
        public final View mView;
        public final ImageView mPhysicianImage;
        public SimpleEnrollment mItem;

        public ViewHolder(View view) {
            mView = view;
            mPhysicianImage = (ImageView) view.findViewById(R.id.recentPhysicianPicture);

        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
    private void initImageLoader() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(false).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext()).defaultDisplayImageOptions(displayImageOptions)
                .denyCacheImageMultipleSizesInMemory().build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
