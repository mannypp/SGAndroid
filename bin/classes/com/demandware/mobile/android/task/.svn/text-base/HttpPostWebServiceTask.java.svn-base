package com.demandware.mobile.android.task;

import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

/**
 * This class is used to issue post requests to the server.
 * 
 * @author mannyp
 */
public class HttpPostWebServiceTask extends HttpGetWebServiceTask {
	/**
	 * Constructor
	 * 
	 * @param pOperationName the name of the operation
	 * @param pActivity the activity which created the task
	 * @param pResources a resource object for strings
	 */
	public HttpPostWebServiceTask(String pOperationName, Activity pActivity, Resources pResources) {
		super(pOperationName, pActivity, pResources);
	}
	
	/**
	 * Called by the base class after execute is called. Initiates the POST request.
	 * 
	 * @param params the variable length parameters, 2 parameters must be supplied, the first is the url string,
	 * 			the second is the json input string
	 * @return the json response string
	 */
	protected String doInBackground(Object... params) {
		if (params == null || params.length != 2)
			throw new RuntimeException(getResources().getString(R.string.invalidNumberOfParameters));
		
		try {
			String url = (String) params[0];
			JSONObject body = (JSONObject) params[1];
	    	return WebApi.getInstance().executeHttpPost(url, body);
		}
		catch(IOException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		
		return null;
	}
}
