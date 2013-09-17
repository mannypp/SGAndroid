package com.demandware.mobile.android.activity.search;

import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.activity.browse.ProductActivity;
import com.demandware.mobile.android.adapter.ProductListViewAdapter;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.model.Product;
import com.demandware.mobile.android.task.ListModelWebServiceTask;
import com.demandware.mobile.android.util.WebApi;

/**
 * This class is the main activity for displaying search results.
 * 
 * @author mparasirakis
 */
public class ProductSearchActivity extends ListActivity {
	//-------------------------------
	protected static Resources sResources;
	
	private TextView mTitle;

	//-------------------------------
	/**
	 * Constructor
	 */
	public ProductSearchActivity() {
	}
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        	
        sResources = getResources();
        setContentView(R.layout.product_search_layout);
        setListAdapter(new ProductListViewAdapter());

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(sResources.getString(R.string.search));

        setUpListeners();
    }
    
    /**
     * Setup the listeners for the search button and enter key click within the search criteria field.
     */
    protected void setUpListeners() {
        Button searchButton = (Button) findViewById(R.id.searchButton);        
        searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View pView) {
				executeSearch();
			}
		});

    	EditText searchCriteria = (EditText) findViewById(R.id.searchCriteria);
    	searchCriteria.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View pV, int pKeyCode, KeyEvent pEvent) {
				if (pKeyCode == KeyEvent.KEYCODE_ENTER || pKeyCode == KeyEvent.KEYCODE_SEARCH) {
					executeSearch();
					return true;
				}
				return false;
			}
		});
    }
    
    /**
     * Execute the search by creating a task and issuing a search request.
     */
	protected void executeSearch() {
    	EditText searchCriteria = (EditText) findViewById(R.id.searchCriteria);
    	String searchString = searchCriteria.getText().toString();
    	
    	ListModelWebServiceTask task = new ListModelWebServiceTask(Product.class, "hits", this, sResources); // "hits" is the key in the json we want to process
		String url = WebApi.getInstance().getProductSearchUrl("*" + searchString + "*");
      	task.execute(url);   	    	
    }    

	/**
	 * Starts an activity using the given activity class.
	 * 
	 * @param pModel the model object that was in the view, in this case a product
	 * @param pActivityClass the class representing the activity to start
	 */
    protected void startActivity(ModelObject pModel, Class<? extends Activity> pActivityClass) {
    	HashMap<String,Object> extra = new HashMap<String,Object>();
    	extra.put("model", pModel);
    	((ProductSearchBrowser) getParent()).startChildActivity(pActivityClass, extra);
    }

    /**
     * Handle a click on a search result in the ListView.
     * 
     * @param pView the view in the ListView that was clicked
     */
    public void onClick(View pView) {
    	startActivity((ModelObject) pView.getTag(), ProductActivity.class);
    }    
}
