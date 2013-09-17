package com.demandware.mobile.android.activity.account;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.activity.SiteGenesisActivityGroup;
import com.demandware.mobile.android.task.HttpPostWebServiceTask;
import com.demandware.mobile.android.task.HttpRequestListener;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

public class AccountActivity extends SiteGenesisActivityGroup implements HttpRequestListener {
	//-------------------------------
	protected static Resources sResources;

	private boolean mLoggedIn = false;
	
	//-------------------------------
	public AccountActivity() {
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sResources = getResources();

        initializeView();
    }
    
    protected void initializeView() {
    	if (mLoggedIn)
        	setupLoggedInView();
    	else
    		createLoginDialog();
    }
    
    protected void createLoginDialog() {    	
		setContentView(R.layout.empty_layout);

		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.login_layout);
		dialog.setTitle(R.string.login);
		
		Button loginButton = (Button) dialog.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new LoginButtonListener(dialog));
		dialog.show();
    }
    
    protected void executeLogin(String pUserName, String pPassword) {
    	HttpPostWebServiceTask task = new HttpPostWebServiceTask("login", this, sResources);
    	JSONObject json = new JSONObject();
    	
    	try {
	    	json.put("login", pUserName);
	    	json.put("password", pPassword);
    	}
    	catch (JSONException e) {
    		Utility.displayError(e.getMessage(), this, sResources);
    		return;
    	}
    	
    	task.execute(WebApi.getInstance().getLoginUrl(), json);
    }
    
    protected void executeLogout() {
    	HttpPostWebServiceTask task = new HttpPostWebServiceTask("logout", this, sResources);
    	task.execute(WebApi.getInstance().getLogoutUrl(), null);
    }
    
    protected void executeAccountInfo() {
    	startChildActivity(ProfileActivity.class, null);
    }
    
    protected void setupLoggedInView() {
    	setContentView(R.layout.account_layout);
    	
    	Button button = (Button) findViewById(R.id.logoutButton);
    	button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				executeLogout();
			}
		});

    	button = (Button) findViewById(R.id.profileInfo);
    	button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				executeAccountInfo();
			}
		});

    	button = (Button) findViewById(R.id.shippingAddress);
    	button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});

    	button = (Button) findViewById(R.id.billingAddress);
    	button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
    }

	public void httpRequestComplete(String pOperationName, String pResponse, Object pUserData) {
		if (pOperationName.equals("login"))
			handleLoginResult(pResponse);
		else if (pOperationName.equals("logout"))
			handleLogoutResult(pResponse);
	}
	
	protected void handleLoginResult(String pResponse) {
		try {
			if (pResponse != null && pResponse.trim().length() > 0) {
				JSONObject response = new JSONObject(pResponse);
				if (response.has("fault")) {
		    		Utility.displayError(response.getJSONObject("fault").getString("message"), this, sResources);
					createLoginDialog();
		    		return;
				}
			}
		}
		catch (JSONException e) {
			Utility.displayError(e.getMessage(), this, sResources);
			return;
		}
		
		mLoggedIn = true;
    	setupLoggedInView();
    	Toast.makeText(this, R.string.loggedIn, Toast.LENGTH_SHORT).show();
	}
	
	protected void handleLogoutResult(String pResponse) {
		try {
			if (pResponse != null && pResponse.trim().length() > 0) {
				JSONObject response = new JSONObject(pResponse);
				if (response.has("fault")) {
		    		Utility.displayError(response.getJSONObject("fault").getString("message"), this, sResources);
		    		return;
				}
			}
		}
		catch (JSONException e) {
			Utility.displayError(e.getMessage(), this, sResources);
			return;
		}
		
		mLoggedIn = false;
		createLoginDialog();
    	Toast.makeText(this, R.string.loggedOut, Toast.LENGTH_SHORT).show();
	}
	
	class LoginButtonListener implements OnClickListener {
		private Dialog mDialog = null;
		
		public LoginButtonListener(Dialog pDialog) {
			mDialog = pDialog;
		}
		
		public void onClick(View pView) {
    		EditText username = (EditText) mDialog.findViewById(R.id.username);
    		EditText password = (EditText) mDialog.findViewById(R.id.password);
    		String name = username.getText().toString();
    		String pass = password.getText().toString();
    		
    		mDialog.dismiss();
    		
			executeLogin(name, pass);
		}
	}	
}
