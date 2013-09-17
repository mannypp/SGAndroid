package com.demandware.mobile.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.demandware.mobile.android.R;

/**
 * This class represents the splash screen which is displayed at startup time. It is the class
 * which is configured in the AndroidManifest.xml file to respond to the android.intent.action.MAIN
 * action. 
 * 
 * @author mparasirakis
 */
public class SplashScreenActivity extends Activity {
	private boolean mSplashActive = true;
	private int mSplashTime = 3000;

	/**
	 * Configure the content view for the splash screen and then display it.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		showSplashScreen();
	}
	
	/**
	 * Starts a thread which waits 3 seconds and then launches the SiteGenesisActivity. Finishes this
	 * activity first.
	 */
	private void showSplashScreen() {
	    Thread splashThread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                int waited = 0;
	                while(mSplashActive && (waited < mSplashTime)) {
	                    sleep(100);
	                    if(mSplashActive)
	                        waited += 100;
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            } finally {
	                finish();
	                startActivity(new Intent("com.demandware.mobile.android.activity.SiteGenesisActivity"));
	            }
	        }
	    };
	    splashThread.start();
	}
	
	/**
	 * If a user touches the screen, it will finish this activity and launch the SiteGenesisActivity.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN)
	        mSplashActive = false;
	    return true;
	}
}
