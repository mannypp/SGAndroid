package com.demandware.mobile.android.activity.browse;

import android.os.Bundle;

import com.demandware.mobile.android.activity.SiteGenesisActivityGroup;

/**
 * This class is a container for the browse tab. It is an activity group which holds all the activities
 * which are started and related to browse.
 * 
 * @author mparasirakis
 */
public class CatalogBrowser extends SiteGenesisActivityGroup {
	/**
	 * Constructor
	 */
	public CatalogBrowser() {
	}
	
	/**
	 * Called when the activity is first created. This method will start the first activity in the group
	 * which is the CategoryListActivity.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        startChildActivity(CategoryListActivity.class, null);
    }
}
