package gpr.com.gprapplication.fe.activity;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.fe.activity.fragment.AnalyticsFragment;
import gpr.com.gprapplication.fe.activity.fragment.BaseReferralListFragment;
import gpr.com.gprapplication.fe.activity.fragment.IncomingReferralFragment;
import gpr.com.gprapplication.fe.activity.fragment.NewReferralFragment;
import gpr.com.gprapplication.fe.activity.fragment.OutgoingReferralFragment;
import gpr.com.gprapplication.fe.activity.fragment.ReferralDetailFragment;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.Referral_Status;

public class PhysicianHomeActivity extends AppCompatActivity implements
        NewReferralFragment.OnFragmentInteractionListener,
        IncomingReferralFragment.OnListFragmentInteractionListener,
        ReferralDetailFragment.OnFragmentInteractionListener {

    private boolean showFilterIcon = true;
    private IncomingReferralFragment incomingReferralFragment;
    private OutgoingReferralFragment outgoingReferralFragment;
    private ReferralDetailFragment referralDetailFragment;
    private AnalyticsFragment analyticsFragment;

    private NewReferralFragment newReferralFragment;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician_home);

        //action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        //menu at the bottom
//        Toolbar toolbar_bottom = (Toolbar) findViewById(R.id.main_toolbar_bottom);
//        toolbar_bottom.inflateMenu(R.menu.physician_home_bottom_menu_items);
//        toolbar_bottom.getMenu().getItem(0).setChecked(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.physician_home_top_menu_items, menu);
        menu.getItem(0).setVisible(showFilterIcon);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO
    }

    protected void onStart() {
        super.onStart();
        newReferralClicked(findViewById(R.id.item_new_referral));
    }

    public void newReferralClicked(View view) {

        if (newReferralFragment == null )
            newReferralFragment = new NewReferralFragment();
        updateCurrentFragment(newReferralFragment);

//        findViewById(R.id.item_new_referral).getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
//        findViewById(R.id.item_incoming_referral).getBackground().clearColorFilter();
//        findViewById(R.id.item_outgoing_referral).getBackground().clearColorFilter();

        ((ImageButton)findViewById(R.id.item_new_referral)).setImageResource(R.drawable.ic_action_new_referral);
        ((ImageButton)findViewById(R.id.item_incoming_referral)).setImageResource(R.drawable.ic_action_incoming_referral_off);
        ((ImageButton)findViewById(R.id.item_outgoing_referral)).setImageResource(R.drawable.ic_action_outgoing_referral_off);
        ((ImageButton)findViewById(R.id.item_analytics)).setImageResource(R.drawable.ic_action_analytics_off);



        getSupportActionBar().setTitle("New Referral");
        showFilterIcon = false;
        getSupportActionBar().invalidateOptionsMenu();

    }

    public void incomingReferralClicked(View view) {

        if ( incomingReferralFragment == null )
             incomingReferralFragment= new IncomingReferralFragment();
        updateCurrentFragment(incomingReferralFragment);

        ((ImageButton)findViewById(R.id.item_new_referral)).setImageResource(R.drawable.ic_action_new_referral_off);
        ((ImageButton)findViewById(R.id.item_incoming_referral)).setImageResource(R.drawable.ic_action_incoming_referral);
        ((ImageButton)findViewById(R.id.item_outgoing_referral)).setImageResource(R.drawable.ic_action_outgoing_referral_off);
        ((ImageButton)findViewById(R.id.item_analytics)).setImageResource(R.drawable.ic_action_analytics_off);


        showFilterIcon = true;

        getSupportActionBar().setTitle("Incoming Referrals");
        getSupportActionBar().invalidateOptionsMenu();


    }

    public void outgoingReferralClicked(View view) {
        if ( outgoingReferralFragment == null )
            outgoingReferralFragment = new OutgoingReferralFragment();
        updateCurrentFragment(outgoingReferralFragment);

        ((ImageButton)findViewById(R.id.item_new_referral)).setImageResource(R.drawable.ic_action_new_referral_off);
        ((ImageButton)findViewById(R.id.item_incoming_referral)).setImageResource(R.drawable.ic_action_incoming_referral_off);
        ((ImageButton)findViewById(R.id.item_outgoing_referral)).setImageResource(R.drawable.ic_action_outgoing_referral);
        ((ImageButton)findViewById(R.id.item_analytics)).setImageResource(R.drawable.ic_action_analytics_off);


        showFilterIcon = true;
        getSupportActionBar().setTitle("Outgoing Referrals");
        getSupportActionBar().invalidateOptionsMenu();


    }

    public void analyticsClicked(View view) {
        if ( analyticsFragment == null )
            analyticsFragment = new AnalyticsFragment();
        updateCurrentFragment(analyticsFragment);

        ((ImageButton)findViewById(R.id.item_new_referral)).setImageResource(R.drawable.ic_action_new_referral_off);
        ((ImageButton)findViewById(R.id.item_incoming_referral)).setImageResource(R.drawable.ic_action_incoming_referral_off);
        ((ImageButton)findViewById(R.id.item_outgoing_referral)).setImageResource(R.drawable.ic_action_outgoing_referral_off);
        ((ImageButton)findViewById(R.id.item_analytics)).setImageResource(R.drawable.ic_action_analytics);


        showFilterIcon = false;
        getSupportActionBar().setTitle("Analytics");
        getSupportActionBar().invalidateOptionsMenu();


    }
    public void filterMenuClicked(MenuItem menuItem) {

    }


    public void filterStatusClicked(MenuItem menuItem) {
        int itemId =  menuItem.getItemId();
        Referral_Status statusChosen = Referral_Status.REFERRAL_SENT ;
        switch (itemId) {
//            case R.id.item_status_bounced :
//                statusChosen = DummyReferrals.Referral_Status.BOUNCED;
//                break;
            case R.id.item_status_rejected :
                statusChosen = Referral_Status.REFERRAL_REJECTED;
                break;
            case R.id.item_status_forwarded :
                statusChosen = Referral_Status.REFERRAL_FORWARDED;
                break;
            case R.id.item_status_accepted :
                statusChosen = Referral_Status.REFERRAL_ACCEPTED;
                break;
            case R.id.item_status_default :
                statusChosen = Referral_Status.REFERRAL_SENT;
                break;
        }
        ((BaseReferralListFragment)currentFragment).filterReferrals(statusChosen);

    }


    public void onListFragmentInteraction(Referral referral)
    {
        if ( referralDetailFragment == null )
            referralDetailFragment = new ReferralDetailFragment();
        referralDetailFragment.setReferralModel(referral);
        referralDetailFragment.setReferralType(currentFragment instanceof IncomingReferralFragment);

        updateCurrentFragment(referralDetailFragment);
        showFilterIcon = false;
        getSupportActionBar().invalidateOptionsMenu();


    }

    private void updateCurrentFragment(Fragment fragment)
    {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stac
        transaction.replace(R.id.physicianHomeContainer, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        currentFragment = fragment;

    }

}
