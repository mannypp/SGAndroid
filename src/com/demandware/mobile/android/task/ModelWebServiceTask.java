package com.demandware.mobile.android.task;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

/**
 * A base class which is used to construct a single object from a request to the server
 * 
 * @author mannyp
 */
public class ModelWebServiceTask extends AsyncTask<String, Void, ModelObject[]> {
	//-----------------------------
	private Class<? extends ModelObject> mModelClass;
	
	public Class<? extends ModelObject> getModelClass() {
		return mModelClass;
	}

	public void setModelClass(Class<? extends ModelObject> pModelClass) {
		mModelClass = pModelClass;
	}

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
	/**
	 * Constructor
	 */
	public ModelWebServiceTask() {
	}
	
	/**
	 * Constructor
	 * 
	 * @param pModelClass the class object representing the type of object to construct
	 * @param pActivity the activity which created this task
	 * @param pResources a Resource object for strings
	 */
	public ModelWebServiceTask(Class<? extends ModelObject> pModelClass, Activity pActivity, Resources pResources) {
		setModelClass(pModelClass);
		setActivity(pActivity);
		setResources(pResources);
	}
	
	/**
	 * Initiates the request to the server after execute is called in the superclass.
	 * 
	 * @param urls the variable length parameter of urls. If multiple urls are supplied, then a single request per url will be
	 * 				executed serially
	 * @return an array of objects which were constructed. The number of urls supplied in urls should equal the size of this array.
	 */
	protected ModelObject[] doInBackground(String... urls) {
		try {
			ModelObject[] result = new ModelObject[urls.length];
			
			for (int i = 0; i < urls.length; i++) {
		    	String responseString = WebApi.getInstance().executeHttpGet(urls[i]);
		        result[i] = createModelObjectFromJSON(responseString);
			}
			
			return result;
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
	 * Initiates a request to load an image into a view
	 * 
	 * @param pURL the image url to request
	 * @param pView the view to put the image into
	 */
	protected void loadImage(String pURL, ImageView pView) {
		ImageLoaderTask task = new ImageLoaderTask(pView);
		task.execute(pURL);
	}

	/**
	 * Set the given text into a view with the given id
	 * 
	 * @param pId the id of the view
	 * @param pValue the string
	 * @return the view that had the text set into it
	 */
	protected TextView setText(int pId, String pValue) {
		View view = getActivity().findViewById(pId);
		if (view instanceof TextView) {
			((TextView) view).setText(pValue);
			return (TextView) view;
		}
		return null;
	}
	
	/**
	 * Puts an image into the view with the given id
	 * 
	 * @param pId the id of the view to put the image into
	 * @param pModel the object which contains the image url
	 * @return the image view that had the image set into it
	 */
	protected ImageView setImage(int pId, ModelObject pModel) {
		View view = getActivity().findViewById(pId);
		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			imageView.setImageDrawable(null);
			if (pModel.getImageUrl() != null)
				loadImage(pModel.getImageUrl(), imageView);
			return imageView;
		}
		return null;
	}

	/**
	 * Called after the response is received. This method will set the image, title, and label fields
	 * 
	 * @param objectArray the objects which contain the data to set
	 */
	protected void onPostExecute(ModelObject[] objectArray) {
		ModelObject object = objectArray[0];
		if (object == null)
			return;

		setImage(R.id.image, object);
		setText(R.id.title, object.getName());
		setText(R.id.label, object.getName());
	}

	/**
	 * Constructs the object based on the json string provided.
	 * 
	 * @param jsonString the json response string
	 * @return the constructed object
	 * @throws JSONException if a json exception occurs
	 * @throws IllegalAccessException if an error occurs creating the object
	 * @throws InstantiationException if an error occurs creating the object
	 */
    private ModelObject createModelObjectFromJSON(String jsonString)
    		throws JSONException, IllegalAccessException, InstantiationException
    {
    	JSONObject json = new JSONObject(jsonString);
		ModelObject object = getModelClass().newInstance();
		object.create(json);
		if (object.isEnabled())
			return object;
    	
    	return null;
    }    
}
