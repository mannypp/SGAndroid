package com.demandware.mobile.android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
 * This class is a base activity group which is used by other activities within the TabHost. It contains some useful
 * methods for each of the ActivityGroups.
 * 
 * @author mparasirakis
 */
public abstract class SiteGenesisActivityGroup extends ActivityGroup {
	protected static Resources sResources;
	
	private List<String> mActivityIdList = new ArrayList<String>();
	private static int sActivityCount = 0;
	
	/**
	 * Constructor
	 */
	public SiteGenesisActivityGroup() {
	}
	
	/**
	 * Called when the activity is first created. Adds an id to the list of activities in this group.
	 * 
	 * @param savedInstanceState the state that is passed to this activity upon creation
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        sResources = getResources();
        
        mActivityIdList.add(getClass().getName());
    }
    
    /**
     * A convenience method for starting a child activity within the group.
     * 
     * @param pActivityClass the class object of the activity to start
     * @param pExtra extra data to attach to the intent
     */
    public void startChildActivity(Class<?> pActivityClass, Map<String,Object> pExtra) {
        Intent intent = new Intent(this, pActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (pExtra != null) {
        	for (String key : pExtra.keySet())
        		intent.putExtra(key, (Serializable) pExtra.get(key));
        }
        String activityName = pActivityClass.getName() + "-" + (++sActivityCount);
        Window window = getLocalActivityManager().startActivity(activityName, intent);
	    mActivityIdList.add(activityName);
        setContentView(window.getDecorView());
    }
    
    /**
     * This is called when a child activity of this one calls its finish method.
     * This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
     * and starts the previous activity.
     * If the last child activity just called finish(),this activity (the parent),
     * calls finish to finish the entire group.
     */
    @Override
    public void finishFromChild(Activity child) {
	    LocalActivityManager manager = getLocalActivityManager();
	    int index = mActivityIdList.size()-1;
	
	    if (index < 1) {
		    finish();
		    return;
	    }
	
	    manager.destroyActivity(mActivityIdList.get(index), true);
	    mActivityIdList.remove(index);
	    index--;
	    String lastId = mActivityIdList.get(index);
	    Activity lastActivity = manager.getActivity(lastId);
	    if (lastActivity != null) {
		    Intent lastIntent = lastActivity.getIntent();
		    Window newWindow = manager.startActivity(lastId, lastIntent);
		    setContentView(newWindow.getDecorView());
	    }
    }

    /**
     * The primary purpose is to prevent systems before android.os.Build.VERSION_CODES.ECLAIR
     * from calling their default KeyEvent.KEYCODE_BACK during onKeyDown.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
		    //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
		    return true;
	    }
	    return super.onKeyDown(keyCode, event);
    }

    /**
     * Overrides the default implementation for KeyEvent.KEYCODE_BACK
     * so that all systems call onBackPressed().
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
		    onBackPressed();
		    return true;
	    }
	    return super.onKeyUp(keyCode, event);
    }

    /**
     * If a Child Activity handles KeyEvent.KEYCODE_BACK.
     * Simply override and add this method.
     */
    @Override
    public void onBackPressed () {
	    int length = mActivityIdList.size();
	    if ( length > 1) {
		    Activity current = getLocalActivityManager().getActivity(mActivityIdList.get(length-1));
		    current.finish();
	    }
    }
}

