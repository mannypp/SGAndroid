package com.demandware.mobile.android.task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.adapter.ModelListViewAdapter;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

/**
 * A specialized subclass of AsyncTask which is used to issue requests that will return lists of objects, like
 * products and categories. 
 * 
 * @author mannyp
 */
public class ListModelWebServiceTask extends AsyncTask<String, Void, ModelObject[]> {
	//-----------------------------
	private Class<? extends ModelObject> mModelClass;
	
	public Class<? extends ModelObject> getModelClass() {
		return mModelClass;
	}

	public void setModelClass(Class<? extends ModelObject> pModelClass) {
		mModelClass = pModelClass;
	}

	//-----------------------------
	private String mJSONKeyName;
	
	public String getJSONKeyName() {
		return mJSONKeyName;
	}

	public void setJSONKeyName(String pJSONKeyName) {
		mJSONKeyName = pJSONKeyName;
	}

	//-----------------------------	
	private ListActivity mActivity;

	public ListActivity getActivity() {
		return mActivity;
	}

	public void setActivity(ListActivity pActivity) {
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
	/**
	 * Constructor
	 * 
	 * @param pModelClass a class object which represents a subclass of ModelObject, like Product or Category
	 * @param pKeyName the key in the json response from which to retrieve the results from
	 * @param pActivity the activity which created this task
	 * @param pResources a Resource object for strings
	 */
	public ListModelWebServiceTask(Class<? extends ModelObject> pModelClass, String pKeyName, ListActivity pActivity, Resources pResources) {
		setModelClass(pModelClass);
		setJSONKeyName(pKeyName);
		setActivity(pActivity);
		setResources(pResources);
	}
	
	/**
	 * Initiates the request after execute is called in the superclass.
	 * 
	 * @param urls a variable length param, one parameter which is the url is the only one used
	 * @return an array of objects representing the type of object expected
	 */
	protected ModelObject[] doInBackground(String... urls) {
		try {
	    	String responseString = WebApi.getInstance().executeHttpGet(urls[0]);
	        if (responseString == null)
	        	return null;
	        
	        return createModelObjectsFromJSON(responseString, getModelClass());
		}
		catch(IOException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		catch(JSONException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		catch(InstantiationException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		catch(IllegalAccessException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		catch(URISyntaxException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		catch(RuntimeException e) {
			Log.e(getResources().getString(R.string.error), e.getMessage(), e);
			Utility.displayError(e.getMessage(), getActivity(), getResources());
		}
		
		return null;
	}
	
	/**
	 * Called after the response is received. This method will set the objects into the list adapter.
	 * 
	 * @param objects the objects representing the products or categories
	 */
	protected void onPostExecute(ModelObject[] objects) {
        ModelListViewAdapter listAdapter = (ModelListViewAdapter) ((ListActivity) getActivity()).getListAdapter();
        listAdapter.setActivity(getActivity());
        ListView listView = (ListView) getActivity().getListView();
        listView.setAdapter(listAdapter);

        if (objects != null && objects.length > 0) {
	        listAdapter.setObjects(objects);
		}
		else {
			listAdapter.setObjects(new ModelObject[0]);
			Toast toast = Toast.makeText(getActivity(), R.string.noResultsFound, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	/**
	 * Converts the json response string into objects representing the products or categories.
	 * 
	 * @param jsonString the json response string
	 * @param pModelClass the class object representing the type of object to construct
	 * @return an array of objects
	 * @throws JSONException if a json error occurs
	 * @throws IllegalAccessException if an error occurs constructing the objects
	 * @throws InstantiationException if an error occurs constructing the objects
	 */
    private ModelObject[] createModelObjectsFromJSON(String jsonString, Class<? extends ModelObject> pModelClass)
    		throws JSONException, IllegalAccessException, InstantiationException
    {
    	JSONObject json = new JSONObject(jsonString);
    	JSONArray jarray = json.getJSONArray(getJSONKeyName());
    	List<ModelObject> objects = new ArrayList<ModelObject>(jarray.length());
    	
    	for (int i = 0; i < jarray.length(); i++) {
    		JSONObject jmodel = (JSONObject) jarray.get(i);
			ModelObject object = pModelClass.newInstance();
			object.create(jmodel);
			if (object.isEnabled())
				objects.add(object);
    	}
    	
    	return objects.toArray(new ModelObject[objects.size()]);
    }    
}
