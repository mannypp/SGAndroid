package com.demandware.mobile.android.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.activity.account.AccountActivity;
import com.demandware.mobile.android.activity.browse.CatalogBrowser;
import com.demandware.mobile.android.activity.cart.CartActivity;
import com.demandware.mobile.android.activity.search.ProductSearchBrowser;

/**
 * This class is the main activity for the application. It creates and displays the tabs for the application.
 * 
 * @author mparasirakis
 */
public class SiteGenesisActivity extends TabActivity implements OnTabChangeListener {
	//-------------------------------
	protected static Resources sResources;	
	
	public static final String BROWSE = "browse";
	public static final String SEARCH = "search";
	public static final String CART = "cart";
	public static final String ACCOUNT = "account";
	
	/**
	 * Constructor
	 */
	public SiteGenesisActivity() {
	}
	
	/**
	 * Create the TabHost and populate it with the Activity for each tab. Also sets the tab change listener
	 * to be this class so that we are notified when the tab is changed in case we want to do something like
	 * update the cart tab when it is displayed. At the end it sets the current tab to the browse tab.
	 * 
	 * @param savedInstanceState the state that is passed to this activity
	 */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_layout);
	    sResources = getResources(); // Resource object to get Drawables

	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    intent = new Intent(this, CatalogBrowser.class);
	    spec = tabHost.newTabSpec(BROWSE).setIndicator("", //sResources.getString(R.string.browse),
	                      sResources.getDrawable(R.drawable.ic_tab_browse))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent(this, ProductSearchBrowser.class);
	    spec = tabHost.newTabSpec(SEARCH).setIndicator("", //sResources.getString(R.string.search),
	                      sResources.getDrawable(R.drawable.ic_tab_search))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent(this, CartActivity.class);
	    spec = tabHost.newTabSpec(CART).setIndicator("", //sResources.getString(R.string.cart),
	    				  sResources.getDrawable(R.drawable.ic_tab_cart))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent(this, AccountActivity.class);
	    spec = tabHost.newTabSpec(ACCOUNT).setIndicator("", //sResources.getString(R.string.account),
	    				  sResources.getDrawable(R.drawable.ic_tab_account))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setOnTabChangedListener(this);
	    tabHost.setCurrentTab(0);
	}
		
	/**
	 * Called whenever a new tab is clicked on to be displayed. In our case, we just want to know when the cart
	 * tab is clicked so that we can broadcast a new intent that the cart tab is about to be displayed. This will
	 * ultimately update the cart in case any changes have occurred since it was last displayed.
	 * 
	 * @param tabId the id of the tab when was selected
	 */
	public void onTabChanged(String tabId) {
		if (tabId.equals(CART)) {
			Intent intent = new Intent(CartActivity.DISPLAY_CART_TAB);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			sendBroadcast(intent);
		}
	}
}
