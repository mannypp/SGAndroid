package com.demandware.mobile.android.activity.browse;

import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.model.ModelObject;

/**
 * This is a base class used by both CatalogBrowseListActivity and ProductListActivity. It contains common functionality
 * which is used by both classes to display categories and/or products.
 * 
 * @author mparasirakis
 */
public class CatalogBrowseListActivity extends ListActivity implements OnClickListener, ModelActivity {
	//-------------------------------
	protected static Resources sResources;
	
	//-------------------------------
	private TextView mTitle;

	//-------------------------------
	private String mId;
	
	public String getId() {
		return mId;
	}

	public void setId(String pId) {
		mId = pId;
	}

	//-------------------------------
	private ModelObject mModel;
	
	public ModelObject getModel() {
		return mModel;
	}

	public void setModel(ModelObject pModel) {
		mModel = pModel;
	}

	public CatalogBrowseListActivity() {
	}
	
    /**
     * Called when the activity is first created. Sets the model and id properties if they were supplied
     * as an extra when the activity was created. Also sets the title view at the top of the list.
     * 
     * @param savedInstanceState the state that was supplied
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        	
        sResources = getResources();
        setContentView(R.layout.list_layout);

        String urlId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	setModel((ModelObject) extras.get("model"));
        	urlId = getModel().getId();
            if (urlId != null)
            	setId(urlId);
        }

        mTitle = (TextView) findViewById(R.id.title);
        if (getModel() == null)
        	mTitle.setText(R.string.rootTitle);
        else
        	mTitle.setText(getModel().getName());
    }
    
    /**
     * Convenience method for starting a new activity.
     * 
     * @param pModel the model object for this list activity 
     * @param pActivityClass the activity class to start
     */
    protected void startActivity(ModelObject pModel, Class<? extends Activity> pActivityClass) {
    	HashMap<String,Object> extra = new HashMap<String,Object>();
    	extra.put("model", pModel);
    	((CatalogBrowser) getParent()).startChildActivity(pActivityClass, extra);
    }
    
    /**
     * Empty method which is overridden by subclasses.
     */
    public void runActivity() {
    	// override in subclasses
    }
    
    /**
     * Empty method which is overridden by subclasses.
     */
    public void onClick(View pView) {
    	// override in subclasses
    }    
}
