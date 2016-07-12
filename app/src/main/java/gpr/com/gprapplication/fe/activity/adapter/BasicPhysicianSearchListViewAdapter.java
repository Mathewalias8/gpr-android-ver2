package gpr.com.gprapplication.fe.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.auth.AuthenticationException;

import java.util.List;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.fragment.BaseReferralListFragment;
import gpr.com.gprapplication.fe.activity.fragment.dummy.DummyContent.DummyItem;
import gpr.com.gprapplication.service.PhysicianListService;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.BaseModel;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;
import gpr.com.gprapplication.service.datamodel.Suggest;
import gpr.com.gprapplication.utility.CommonUtils;
import gpr.com.gprapplication.utility.CredentialManager;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link BaseReferralListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BasicPhysicianSearchListViewAdapter extends ArrayAdapter<Suggest> {

    private final BaseReferralListFragment.OnListFragmentInteractionListener mListener;
    private GenericCallback<List<Suggest>> filterCallback;

    public BasicPhysicianSearchListViewAdapter(Context context, List<Suggest> items, BaseReferralListFragment.OnListFragmentInteractionListener listener) {
        super(context, R.layout.physician_search_result_listitem, items);
        mListener = listener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Suggest physician = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.physician_search_result_listitem, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.mItem = getItem(position);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        ImageLoader.getInstance().displayImage(physician.getImageUrl(), viewHolder.mPhysicianImage);
        viewHolder.mFullName.setText(physician.getName());
        viewHolder.mLocation.setText(physician.getLocation()   );
        viewHolder.mSpeciality.setText(physician.getSpeciality()   );
        viewHolder.mPhysicianImage.setTag(physician.getId());

        // Return the completed view to render on screen
        return convertView;
    }


    public class ViewHolder  {
        public final View mView;
        public final ImageView mPhysicianImage;
        public final TextView mFullName;
        public final TextView mLocation;
        public final TextView mSpeciality;
        public Suggest mItem;

        public ViewHolder(View view) {
            mView = view;
            mPhysicianImage = (ImageView) view.findViewById(R.id.physicianImage);
            mLocation = (TextView)view.findViewById(R.id.locationText);
            mFullName = (TextView)view.findViewById(R.id.fullNameText);
            mSpeciality = (TextView)view.findViewById(R.id.specialityText);

        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }



    @Override
    public Filter getFilter() {
        final String TAG = "getFilter";
        Filter myFilter = new PhysicianFilter();
        return myFilter ;
    }

    public void setFilterCallback(GenericCallback<List<Suggest>> filterCallback) {
        this.filterCallback = filterCallback;
    }

    private  class PhysicianFilter extends Filter  {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           FilterResults filterResults = new FilterResults();
            try {
                if (TextUtils.isEmpty(constraint)) {
                    clear();
                }
                else {
                    String input = constraint.toString();
                    if(input.indexOf("\u0A00") > 1) {
                        String[] params = input.split("\u0A00");
                        String token = CredentialManager.getInstance().get(getContext());
                        int offset = params.length > 9 ? Integer.parseInt(params[9]) : 0;
                        PhysicianListService physicianListService = new PhysicianListService(token, offset);
                        List<Suggest> new_suggestions = physicianListService.getParseJson(params);
//                        if(offset == 0) {
//                            clear();
//                        }
                        int size = new_suggestions.size();
                        if(size == 0) {
                            if(CommonUtils.isValidEmailAddress(params[0]) || TextUtils.isDigitsOnly(params[0])) {
                                Suggest suggest = new Suggest("0", params[0], "", "", "");
                                new_suggestions.add(suggest);
                            }
                        }
                        filterResults.values =  new_suggestions;
                        filterResults.count = getCount();

                    }
                }


            } catch (GPRException e) {
//                    GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e, CLASS_NAME, TAG);
//                    Log.e(ex.getMethodName(), "" + ex.getMessage());
//                    ex.printStackTrace();
            } catch (AuthenticationException e) {
//                    Toast.makeText(mContext, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence contraint,
                                      FilterResults results) {
            if (filterCallback != null) {
                filterCallback.onComplete((List<Suggest>)results.values);
            }
        }
    };

}
