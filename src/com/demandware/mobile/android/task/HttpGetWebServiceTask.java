package com.demandware.mobile.android.task;

import java.io.IOException;
import java.net.URISyntaxException;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

/**
 * This class is an AsyncTask which is used for issuing GET requests. It allows the user to supply
 * an activity which created the AsyncTask, a name for the operation, and optional user data.
 * 
 * @author mannyp
 */
public class HttpGetWebServiceTask extends AsyncTask<Object, Void, String> {
	//-----------------------------	
	private Activity mActivity;

	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity pActivity) {
		mActivity = pActivity;
	}

	//-----------------------------
	private Resources mResources;
	
	public Resources getResources() {
		return mResources;
	}

	public void setResources(Resources pResources) {
		mResources = pResources;
	}
	
	//-----------------------------
	private String mOperationName;
	
	public String getOperationName() {
		return mOperationName;
	}

	public void setOperationName(String pOperationName) {
		mOperationName = pOperationName;
	}

	//-----------------------------
	private Object mUserData;
	
	public Object getUserData() {
		return mUserData;
	}

	public void setUserData(Object pUserData) {
		mUserData = pUserData;
	}

	//-----------------------------
	/**
	 * Constructor
	 * 
	 * @param pOperationName the name of the operation
	 * @param pActivity the activity which created the task
	 * @param pResources a Resource object for strings
	 */
	public HttpGetWebServiceTask(String pOperationName, Activity pActivity, Resources pResources) {
		setOperationName(pOperationName);
		setActivity(pActivity);
		setResources(pResources);
	}
	
	/**
	 * This method is called by the base class after execute is called by the activity. It initiates
	 * the call to the server and returns the response string.
	 * 
	 * @param params the variable length parameters, currently only one parameter is allowed and that is the url string.
	 * @return the response string
	 */
	protected String doInBackground(Object... params) {
		if (params == null || params.length != 1)
			throw new RuntimeException(getResources().getString(R.string.invalidNumberOfParameters));
		
		try {
			String url = (String) params[0];
	    	return WebApi.getInstance().executeHttpGet(url);
		}
		catch(IOException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		catch(URISyntaxException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		
		return null;
	}
	
	/**
	 * Called after the response is received. If the given Activity implements the HttpRequestListener interface, it will call
	 * the httpRequestComplete method passing the operation name, json response string, and user data supplied in the constructor.
	 * 
	 * @param json the json response string
	 */
	protected void onPostExecute(String json) {
		if (getActivity() instanceof HttpRequestListener)
			((HttpRequestListener) getActivity()).httpRequestComplete(getOperationName(), json, getUserData());
		else
			Log.i(getResources().getString(R.string.info), getResources().getString(R.string.activityNotHttpRequestListener, getOperationName()));
	}
}
