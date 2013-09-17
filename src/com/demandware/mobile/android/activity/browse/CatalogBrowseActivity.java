package com.demandware.mobile.android.activity.browse;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import com.demandware.mobile.android.model.ModelObject;

/**
 * This class serves as a base class mainly for the ProductActivity class. It exposes common properties and methods.
 * 
 * @author mparasirakis
 */
public class CatalogBrowseActivity extends Activity implements ModelActivity {
	//-------------------------------
	protected static Resources sResources;
	
	//-------------------------------
	private String mId = "root";
	
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

	//-------------------------------
	/**
	 * Constructor
	 */
	public CatalogBrowseActivity() {
	}
	
    /**
     * Called when the activity is first created. Sets the model and id properties.
     * 
     * @param savedInstanceState the state that was supplied
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sResources = getResources();

        String urlId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	setModel((ModelObject) extras.get("model"));
        	urlId = getModel().getId();
            if (urlId != null)
            	setId(urlId);
        }
    }        
    
    /**
     * Meant to be overridden in subclasses
     */
    public void runActivity() {
    	// override in subclasses
    }
}
