package com.demandware.mobile.android.activity.search;

import android.os.Bundle;

import com.demandware.mobile.android.activity.SiteGenesisActivityGroup;

/**
 * This class represents the activity group for the search tab.
 * 
 * @author mparasirakis
 */
public class ProductSearchBrowser extends SiteGenesisActivityGroup {
	/**
	 * Constructor
	 */
	public ProductSearchBrowser() {
	}
	
	/**
	 * Called when the activity is first created. This starts the ProductSearchActivity child activity.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        startChildActivity(ProductSearchActivity.class, null);
    }
}
