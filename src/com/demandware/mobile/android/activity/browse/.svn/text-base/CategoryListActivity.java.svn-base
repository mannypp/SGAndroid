package com.demandware.mobile.android.activity.browse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.demandware.mobile.android.adapter.CategoryListViewAdapter;
import com.demandware.mobile.android.model.Category;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.task.HttpGetWebServiceTask;
import com.demandware.mobile.android.task.HttpRequestListener;
import com.demandware.mobile.android.task.ListModelWebServiceTask;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

/**
 * This class is the main Activity used to display a list of categories in a ListView. This class mainly
 * handles initiating a request for a list of child categories for a given category, display them, and handling
 * clicks on them to display those child categories or products.
 * 
 * @author mparasirakis
 */
public class CategoryListActivity extends CatalogBrowseListActivity implements HttpRequestListener, OnClickListener {
	/**
	 * Constructor
	 */
	public CategoryListActivity() {
	}
	
    /**
     * Called when the activity is first created. If no id has been set, then get set the id to be the
     * root category.
     * 
     * @param savedInstanceState the state that was supplied
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new CategoryListViewAdapter());
        if (getId() == null)
        	setId("root");
        
        runActivity();
    }
    
    /**
     * Initiate a request back to the server for the child categories of the given category which is
     * set in the id property of this class.
     */
    public void runActivity() {
    	ListModelWebServiceTask task = new ListModelWebServiceTask(Category.class, "categories", this, sResources); // "categories" is the key in the json we want to process
		String url = WebApi.getInstance().getCategoriesUrl(getId(), "levels=1");
      	task.execute(url);
    }
    
    /**
     * Handle a click on a category in the list. Start a request to get the number of child categories.
     * This task is named "childCategoryCount" and the result will be handled in httpRequestComplete. We
     * get the count to determine if we should send a request for child categories or child products.
     * 
     * @param pView the view within the ListView that was click on
     */
    public void onClick(View pView) {
    	HttpGetWebServiceTask task = new HttpGetWebServiceTask("childCategoryCount", this, getResources());
    	task.setUserData(pView);
    	task.execute(WebApi.getInstance().getCategoriesUrl(((ModelObject) pView.getTag()).getId(), "levels=1"));
    }
    
    /**
     * Handles the responses for web service requests.
     * 
     * @param pOperationName the name of the operation which was supplied when the task was created
     * @param pResponse the raw json response string
     * @param pUserData the user data that was supplied in the task before it was executed
     */
    public void httpRequestComplete(String pOperationName, String pResponse, Object pUserData) {
    	if (pOperationName.equals("childCategoryCount")) {
	    	try {
	    		JSONObject json = new JSONObject(pResponse);
		    	if (json.has("fault")) {
		    		Utility.displayError(json.getJSONObject("fault").getString("message"), this, sResources);
		    		return;
		    	}
	    		JSONArray array = json.getJSONArray("categories");
	    		View view = (View) pUserData;
	    		
	    		// start another activity to display the child categories or products
    	    	startActivity((ModelObject) view.getTag(),
    	    			array.length() > 0 ? CategoryListActivity.class : ProductListActivity.class);
	    	}
	    	catch (JSONException e) {
	    		Utility.displayError(e.getMessage(), this, sResources);
	    		return;
	    	}
    	}
    }
}
