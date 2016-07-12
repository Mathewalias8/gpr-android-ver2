package gpr.com.gprapplication.service.callback;


public interface GenericCallback<T> {
	public void onComplete(T response);
	public void onError(T response, GPRException exception);
	public void onCancel();
}
