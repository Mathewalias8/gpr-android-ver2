package gpr.com.gprapplication.fe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.service.datamodel.Physician;
import gpr.com.gprapplication.service.datamodel.User;
import gpr.com.gprapplication.utility.CommonUtils;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.GPRContext;

public class LauncherActivity extends AppCompatActivity {

    private final String TAG = "LauncherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();




    }

    private void init() {
        User currentUser = GPRContext.getInstance().getCurrentUser(this);

        CommonUtils.track(this, TAG);

        if(currentUser != null) {
            if(currentUser.isValid()) {
//                initLookupData();
                finish();
                Intent intent = new Intent(this, PhysicianHomeActivity.class);
                startActivity(intent);

            }
            else {
                finish();
                Intent intent = new Intent(this, SigninActivity.class);
                if(!TextUtils.isEmpty(currentUser.getUsername())) {
                    intent.putExtra(GPRConstants.EXTRA_USER_ID, currentUser.getUsername());
                }
                startActivity(intent);
            }
        }
        else
        {
            finish();
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        }

    }

}
