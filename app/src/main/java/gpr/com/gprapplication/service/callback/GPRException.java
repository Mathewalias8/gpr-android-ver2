package gpr.com.gprapplication.service.callback;

import android.app.Activity;
import android.graphics.PathDashPathEffect;
import android.util.Log;
import android.widget.Toast;


public class GPRException extends Exception {
	private static final long serialVersionUID = -6021137106051620382L;
	private Exception exception;
	private String className;
	private String methodName;
	private String message = "";
	private ExceptionType type;

	public enum ExceptionType {
		MINOR, MEDIUM, SEVERE, FATAL
	}

	public GPRException(ExceptionType type, Exception exception,
						String className, String methodName) {
		super();
		this.type = type;
		this.exception = exception;
		this.className = className;
		this.methodName = className + "." + methodName;
	}
	
	public GPRException(ExceptionType type,
						String className, String methodName) {
		super();
		this.type = type;
		this.className = className;
		this.methodName = className + "." + methodName;
	}
	
	public GPRException(ExceptionType type,
						String className, String methodName, String message) {
		super();
		this.type = type;
		this.className = className;
		this.methodName = className + "." + methodName;
		this.message = message;
	}
	
	public void takeAction(final String message, final Activity activity){
		if(null != activity){
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  /*Toast toast = new Toast(activity);
					  toast.setGravity(Gravity.CENTER_HORIZONTAL, 20, 0);
					  toast.setText(message);
					  toast.setDuration(Toast.LENGTH_LONG);
					  toast.show();*/
					  if(null != activity){
						  Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();


					  }
				  }
				});
		}
		Log.e(className + "." + methodName, toString());
		if(null != exception)
			exception.printStackTrace();
	}

	@Override
	public String toString() {
		if(null != exception)
			return "Exception of type " + type.toString() + " raised in " + methodName + ":\n"
					+ exception.getLocalizedMessage() + "\n\n"
					+ exception.getStackTrace() + "\n";
		else
			return "Exception of type " + type.toString() + " raised in " + methodName + " : " + message;
	}
	
	public String getMethodName(){
		return this.methodName;
	}
	
	public Exception getOriginalException() {
		return this.exception;
	}
}
