package gpr.com.gprapplication.fe.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.adapter.BasicPhysicianSearchListViewAdapter;
import gpr.com.gprapplication.fe.activity.adapter.RecentReferralPhysicianListViewAdapter;
import gpr.com.gprapplication.service.asynctask.AddFavoritePhysicianServiceTask;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;
import gpr.com.gprapplication.service.datamodel.Suggest;
import gpr.com.gprapplication.utility.CommonUtils;
import gpr.com.gprapplication.utility.GPRConstants;

public class BasicPhysicianSearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private BasicPhysicianSearchListViewAdapter physicianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_physician_search);
        //action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView physicianSearchListView = (ListView)findViewById(R.id.physicianSearchListView);
        physicianSearchListView.setEmptyView(findViewById(R.id.searchEmptyText));

        physicianSearchListView.setAdapter(physicianAdapter =
                new BasicPhysicianSearchListViewAdapter(this, new ArrayList<Suggest>(), null));
        physicianAdapter.setFilterCallback(new GenericCallback<List<Suggest>>() {
            @Override
            public void onComplete(final List<Suggest> suggestions) {
                if (suggestions != null) {
                    if (suggestions.size() > 0) {
                        findViewById(R.id.selectionHintText).setVisibility(View.VISIBLE);


                    } else {
                        findViewById(R.id.selectionHintText).setVisibility(View.GONE);


                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            physicianAdapter.clear();
                            physicianAdapter.addAll(suggestions);
                        }
                    });
                }

            }

            @Override
            public void onError(List<Suggest> response, GPRException exception) {

            }

            @Override
            public void onCancel() {

            }
        });

        physicianSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                hideKeyboard();

                Suggest suggest = physicianAdapter.getItem(position);

                Intent resultIntent = new Intent();
                resultIntent.putExtra(GPRConstants.EXTRA_SUGGEST, suggest);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        physicianSearchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPhysicianDetail(physicianAdapter.getItem(position));
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.physician_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();

        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.requestFocus();
        mSearchView.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                physicianAdapter.getFilter().filter(buildFilterString(newText));
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                physicianAdapter.getFilter().filter(buildFilterString(query));
                return true;
            }
        };
        mSearchView.setOnQueryTextListener(textChangeListener);

        String searchTerm = getIntent().getStringExtra(
                GPRConstants.EXTRA_SEARCH_TERM);
        if (!TextUtils.isEmpty(searchTerm)) {
            mSearchView.setQuery(searchTerm, false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    private String buildFilterString(String query) {
        StringBuilder sb = new StringBuilder(query).append("\u0A00");
        sb.append("SPECIALITY").append("\u0A00").append("\u0A00");
        sb.append("SUPER_SPECIALITY").append("\u0A00").append("\u0A00");
        sb.append("COUNTRY").append("\u0A00").append("\u0A00");
        sb.append("STATE").append("\u0A00");

        return sb.toString();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            return;
        }
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showPhysicianDetail(Suggest physician) {
        Log.i("NewReferralFragment", "showPhysicianDetail");
        Intent phyDetailInfo = new Intent(this,
                PhysicianDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GPRConstants.PHYSICAN,new Long ( physician.getId()));
        phyDetailInfo.putExtras(bundle);
        startActivity(phyDetailInfo);

        new AddFavoritePhysicianServiceTask(this,physician).execute();



    }
}
