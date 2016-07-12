package gpr.com.gprapplication.fe.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import gpr.com.gprapplication.R;
import gpr.com.gprapplication.service.asynctask.UserLoginTask;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.User;
import gpr.com.gprapplication.utility.GPRContext;

public class SigninActivity extends AppCompatActivity implements GenericCallback<User> {

    private UserLoginTask mLoginTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }

    public void signIn(View view) {
        toastScreenDensity();
        attemptLogin();
    }

    private void toastScreenDensity() {
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(this, "LDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(this, "MDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(this, "HDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Toast.makeText(this, "XHDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                Toast.makeText(this, "XXHDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                Toast.makeText(this, "XXXHDPI", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid userId, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (!isNetworkConnected(this)) {
            Toast.makeText(this, "R.string.no_internet", Toast.LENGTH_LONG).show();
            return;
        }

        if (mLoginTask != null) {
            return;
        }

        TextView userIdView = (TextView) this.findViewById(R.id.userIdText);
        TextView passwordView = (TextView) this.findViewById(R.id.passwordText);

        // Reset errors.
        userIdView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String userId = userIdView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        } else {
            if (password.length() < 4) {
                passwordView.setError(getString(R.string.error_invalid_password));
                focusView = passwordView;
                cancel = true;
            }
        }

        // Check for a valid userId.
        if (TextUtils.isEmpty(userId)) {
            userIdView.setError(getString(R.string.error_field_required));
            focusView = userIdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            mLoginTask = new UserLoginTask();
            mLoginTask.setCallback(this);
            String tenant_name = "";
            try {
                ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                tenant_name = appInfo.metaData.getString("gpr.tenant.name");

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mLoginTask.execute(userId, password,tenant_name);
        }
    }


    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void onComplete(User user) {
        mLoginTask = null;
        showProgress(false);

        if (user != null && !TextUtils.isEmpty(user.getToken())) {
            GPRContext.getInstance().setCurrentUser(this, user);

            finish();
            Intent intent = new Intent(this, PhysicianHomeActivity.class);
            startActivity(intent);
        } else {
            GPRContext.getInstance().setCurrentUser(this, null);

            new AlertDialog.Builder(this).setMessage(R.string.invalid_login).setPositiveButton(R.string.ok, null).show();
            findViewById(R.id.passwordText).requestFocus();
        }
    }

    @Override
    public void onError(User user, GPRException exception) {
    }

    @Override
    public void onCancel() {
        mLoginTask = null;
        showProgress(false);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        final View loginFormView = (View) findViewById(R.id.loginView);
        View loginStatusView = (View) findViewById(R.id.login_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            loginStatusView.setVisibility(View.VISIBLE);
            loginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loginFormView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            loginFormView.setVisibility(View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loginFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }




}
