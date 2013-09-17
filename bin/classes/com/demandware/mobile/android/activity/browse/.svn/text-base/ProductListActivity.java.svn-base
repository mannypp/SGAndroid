package com.demandware.mobile.android.activity.browse;

import android.os.Bundle;
import android.view.View;

import com.demandware.mobile.android.adapter.ProductListViewAdapter;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.model.Product;
import com.demandware.mobile.android.task.ListModelWebServiceTask;
import com.demandware.mobile.android.util.WebApi;

/**
 * This class represents a list of products within a category.
 * 
 * @author mparasirakis
 */
public class ProductListActivity extends CatalogBrowseListActivity {
	public ProductListActivity() {
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ProductListViewAdapter());
        runActivity();
    }
    
    /**
     * Initiate a request back to the server for the child products of the given category which is
     * set in the id property of this class.
     */
    public void runActivity() {
    	ListModelWebServiceTask task = new ListModelWebServiceTask(Product.class, "hits", this, sResources); // "hits" is the key in the json we want to process
		String url = WebApi.getInstance().getProductSearchUrl(getId());
      	task.execute(url);   	
    }
    
    /**
     * Handle a click on a product in the list. Start an activity to display the product details.
     * 
     * @param pView the view within the ListView that was clicked on
     */
    public void onClick(View pView) {
		startActivity((ModelObject) pView.getTag(), ProductActivity.class);
    }    
}
