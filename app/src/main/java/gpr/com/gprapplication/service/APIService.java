package gpr.com.gprapplication.service;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.util.Map;

import gpr.com.gprapplication.utility.GPRConstants;

public class APIService {
	private static final String TAG = "APIService";
	
	private static final String ACCEPT_HEADER_NAME = "Accept";
	private static final String ACCEPT_HEADER_VALUE = "application/json";
	private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
	private static final String CONTENT_TYPE__HEADER_VALUE = "application/json";
	
	private static APIService instance;
	
	private APIService() {}
	
	public static APIService getInstance() {
		if(instance == null) {
			instance = new APIService();
		}
		
		return instance;
	}
	
	public String post(String api, String request, Map<String, String> headers) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(GPRConstants.API_URL + api);
		postRequest.addHeader(ACCEPT_HEADER_NAME, ACCEPT_HEADER_VALUE);
		postRequest.addHeader(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE__HEADER_VALUE);
		
		if(headers != null) {
			for(Map.Entry<String, String> e : headers.entrySet()) {
				postRequest.addHeader(e.getKey(), e.getValue());
			}
		}
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = null;
		try {
			StringEntity entity = new StringEntity(request, HTTP.UTF_8);
			entity.setContentType(CONTENT_TYPE__HEADER_VALUE);
			postRequest.setEntity(entity);
			
			result = httpclient.execute(postRequest, handler);
			Log.d(TAG, result);
		}
		catch(Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		
		return result;
	}
}
