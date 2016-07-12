package gpr.com.gprapplication.utility;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GPRException.ExceptionType;


public class HttpClientUtil {
	public static final String CLASS_NAME = "HttpClientUtil";

	public static JSONObject sendHttpPost(String URL, String token,
			JSONObject jsonObjSend) throws AuthenticationException,
			GPRException {
		final String TAG = "sendHttpPost";
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			HttpPost post = new HttpPost(URL);
			post.addHeader(new BasicHeader("accept", "application/json"));
			post.addHeader(new BasicHeader("content-type", "application/json"));
			post.addHeader(new BasicHeader(
					"SPRING_SECURITY_REMEMBER_ME_COOKIE", token));

			if ( jsonObjSend != null )
			{
				Log.i("Request: " + URL, jsonObjSend.toString());
				StringEntity entity = new StringEntity(jsonObjSend.toString());
				post.setEntity(entity);

			}

			HttpResponse resp = httpClient.execute(post);
			validateResponse(resp);

			String respStr = EntityUtils.toString(resp.getEntity());
			Log.i("Response: " + URL, respStr);

			jsonObject = new JSONObject(respStr);

		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception e) {
			GPRException ex = new GPRException(GPRException.ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			throw ex;
		}
		return jsonObject;
	}

	private static void validateResponse(HttpResponse resp)
			throws AuthenticationException {
		if (resp != null) {
			switch (resp.getStatusLine().getStatusCode()) {
			case 401:
				CredentialManager.getInstance().remove();
				throw new AuthenticationException("Unauthorized");
			}
		}
	}

	public static JSONObject sendNoAuthHttpPost(String URL,
			JSONObject jsonObjSend) throws GPRException {
		final String TAG = "sendHttpPost";
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			HttpPost post = new HttpPost(URL);
			post.addHeader(new BasicHeader("accept", "application/json"));
			post.addHeader(new BasicHeader("content-type", "application/json"));

			Log.i("Request: " + URL, jsonObjSend.toString());
			StringEntity entity = new StringEntity(jsonObjSend.toString());
			post.setEntity(entity);

			HttpResponse resp = httpClient.execute(post);
			Log.i("Status: ", resp.getStatusLine().getReasonPhrase());
			
			String respStr = EntityUtils.toString(resp.getEntity());
			Log.i("Response: " + URL, respStr);

			jsonObject = new JSONObject(respStr);

		} catch (Exception e) {
			GPRException ex = new GPRException(ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			throw ex;
		}
		return jsonObject;
	}

	public static JSONObject sendAuthHttpPost(String URL, String username,
			String password) throws GPRException {
		final String TAG = "sendAuthHttpPost";
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			HttpPost post = new HttpPost(URL);
			post.addHeader(new BasicHeader("accept", "application/json"));
			post.addHeader(new BasicHeader("content-type", "application/json"));
			post.addHeader(new BasicHeader("j_username", username));
			post.addHeader(new BasicHeader("j_password", password));

			HttpResponse resp = httpClient.execute(post);
			String respStr = EntityUtils.toString(resp.getEntity());
			jsonObject = new JSONObject(respStr);

		} catch (Exception e) {
			GPRException ex = new GPRException(ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			throw ex;
		}
		return jsonObject;
	}

	public static boolean sendImageAsHttpPost(final String URL,
			final String uploadFileWithPath, final String token)
			throws GPRException, AuthenticationException {
		final String TAG = "sendImageAsHttpPost";
		try {
			String upLoadServerUri = URL;
			JSONObject jsonObject = null;
			String status = null;
			HttpURLConnection conn = null;
			DataOutputStream dos = null;
			String lineEnd = "\r\n";
			int serverResponseCode = 0;
			String twoHyphens = "--";
			String boundary = "*****";
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
			File sourceFile = new File(uploadFileWithPath);
			if (!sourceFile.isFile()) {
				Log.e(TAG, "Source File Does not exist");
				return false;
			}
			try { // open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				java.net.URL url = new URL(upLoadServerUri);
				conn = (HttpURLConnection) url.openConnection(); // Open a HTTP
																	// connection
																	// to
																	// the URL
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("SPRING_SECURITY_REMEMBER_ME_COOKIE",
						token);
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("file", uploadFileWithPath);
				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
						+ uploadFileWithPath + "\"" + lineEnd);
				dos.writeBytes(lineEnd);

				bytesAvailable = fileInputStream.available(); // create a buffer
																// of
																// maximum size

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();

				Scanner s;
				String successResponse = null;
				if (serverResponseCode == 200) {
					s = new Scanner(conn.getInputStream());
					s.useDelimiter("\\Z");
					successResponse = s.next();
					jsonObject = new JSONObject(successResponse);
					status = jsonObject.getString("status");
					Log.i("uploadFile", "HTTP Response is : " + successResponse
							+ ": " + serverResponseCode);
					s.close();
				} else {
					fileInputStream.close();
					dos.flush();
					dos.close();
					if (serverResponseCode == 401) {
						AuthenticationException ex = new AuthenticationException(
								"Unauthorized");
						throw ex;
					} else {
						GPRException gprex = new GPRException(
								ExceptionType.SEVERE, CLASS_NAME, TAG);
						throw gprex;
					}
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (AuthenticationException aex) {
				throw aex;
			} catch (MalformedURLException ex) {
				GPRException gprex = new GPRException(ExceptionType.SEVERE, ex,
						CLASS_NAME, TAG);
				throw gprex;
			} catch (Exception e) {
				GPRException gprex = new GPRException(ExceptionType.SEVERE, e,
						CLASS_NAME, TAG);
				throw gprex;
			}
			if (status.equalsIgnoreCase(GPRConstants.SUCCESS)) {
				return true;
			} else {
				return false;
			}
		} catch (AuthenticationException aex) {
			throw aex;
		} catch (Exception e) {
			GPRException ex = new GPRException(ExceptionType.SEVERE, e,
					CLASS_NAME, TAG);
			throw ex;
		}
	}
}
