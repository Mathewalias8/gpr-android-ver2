package gpr.com.gprapplication.service.asynctask;

import android.os.AsyncTask;

import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.User;

/**
 * Created by jaya on 2/25/2016.
 */
public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected GenericCallback<Result> callback;

    @Override
    protected void onPostExecute(final Result result) {
        if(this.callback != null) {
            this.callback.onComplete(result);
        }
    }

    @Override
    protected void onCancelled() {
        if(this.callback != null) {
            this.callback.onCancel();
        }
    }

    public void setCallback(GenericCallback<Result> callback) {
        this.callback = callback;
    }
}
