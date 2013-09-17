package com.demandware.mobile.android.activity.account;

import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.task.HttpGetWebServiceTask;
import com.demandware.mobile.android.task.HttpPatchWebServiceTask;
import com.demandware.mobile.android.task.HttpRequestListener;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

public class ProfileActivity extends Activity implements HttpRequestListener {
	//-------------------------------
	protected static Resources sResources;
	
	private JSONObject mProfileData = null;

	//-------------------------------
	public ProfileActivity() {
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sResources = getResources();

        HttpGetWebServiceTask task = new HttpGetWebServiceTask("getProfile", this, sResources);
    	task.execute(WebApi.getInstance().getProfileUrl());
    }
    
    protected void closeActivity() {
    	//((ActivityGroup) getParent()).getLocalActivityManager().getClass().getName(), true);    	
    	finish();
    }
    
    protected void executeProfileUpdate() {
    	JSONObject json = new JSONObject();
    	
    	try {    	
			EditText email = (EditText) findViewById(R.id.profileEmail);
			json.put("email", email.getText());
			
			EditText firstName = (EditText) findViewById(R.id.profileFirstName);
			json.put("first_name", firstName.getText());
			
			EditText lastName = (EditText) findViewById(R.id.profileLastName);
			json.put("last_name", lastName.getText());
			
			EditText homePhone = (EditText) findViewById(R.id.profileHomePhone);
			json.put("phone_home", homePhone.getText());
			
			EditText mobilePhone = (EditText) findViewById(R.id.profileMobilePhone);
			json.put("phone_mobile", mobilePhone.getText());
			
			EditText workPhone = (EditText) findViewById(R.id.profileWorkPhone);
			json.put("phone_business", workPhone.getText());
			
			EditText fax = (EditText) findViewById(R.id.profileFax);
			json.put("fax", fax.getText());
			
			DatePicker birthday = (DatePicker) findViewById(R.id.profileBirthday);
			json.put("birthday", birthday.getYear() + "-" + (birthday.getMonth() + 1) + "-" + birthday.getDayOfMonth());
			
	        Spinner gender = (Spinner) findViewById(R.id.profileGender);
	        String genderString = (String) gender.getSelectedItem();
			json.put("gender", genderString.equals("Male") ? 1 : 2);
    	}
    	catch (JSONException e) {
    		Utility.displayError(e.getMessage(), this, sResources);
    		return;
    	}
    	
    	HttpPatchWebServiceTask task = new HttpPatchWebServiceTask("updateProfile", this, sResources);
    	task.execute(WebApi.getInstance().getProfileUrl(), json); // use profile url with patch request to update profile
    }
    
	public void httpRequestComplete(String pOperationName, String pResponse, Object pUserData) {
		if (pOperationName.equals("getProfile"))
			handleGetProfileResult(pResponse, pUserData);
		else if (pOperationName.equals("updateProfile"))
			handleUpdateProfileResult(pResponse, pUserData);
	}
	
	protected void handleGetProfileResult(String pResponse, Object pUserData) {
		JSONObject response = null;
		
		try {
			if (pResponse != null && pResponse.trim().length() > 0) {
				response = new JSONObject(pResponse);
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

		mProfileData = response;

        setContentView(R.layout.profile_layout);

        Button updateButton = (Button) findViewById(R.id.profileUpdateButton);
        updateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View pV) {
				executeProfileUpdate();
			}
		});
        Button cancelButton = (Button) findViewById(R.id.profileCancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View pV) {
				closeActivity();
			}
		});
        
		try {
			EditText email = (EditText) findViewById(R.id.profileEmail);
			email.setText((String) mProfileData.get("email"));
			
			EditText firstName = (EditText) findViewById(R.id.profileFirstName);
			firstName.setText((String) mProfileData.get("first_name"));
			
			EditText lastName = (EditText) findViewById(R.id.profileLastName);
			lastName.setText((String) mProfileData.get("last_name"));
			
			EditText homePhone = (EditText) findViewById(R.id.profileHomePhone);
			homePhone.setText((String) mProfileData.get("phone_home"));
			
			EditText mobilePhone = (EditText) findViewById(R.id.profileMobilePhone);
			mobilePhone.setText((String) mProfileData.get("phone_mobile"));
			
			EditText workPhone = (EditText) findViewById(R.id.profileWorkPhone);
			workPhone.setText((String) mProfileData.get("phone_business"));
			
			EditText fax = (EditText) findViewById(R.id.profileFax);
			fax.setText((String) mProfileData.get("fax"));
			
			DatePicker birthday = (DatePicker) findViewById(R.id.profileBirthday);
			String birthdayDate = mProfileData.getString("birthday");
			StringTokenizer st = new StringTokenizer(birthdayDate, "-", false);
			birthday.updateDate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
			
	        Spinner gender = (Spinner) findViewById(R.id.profileGender);
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	                this, R.array.gender_array, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        gender.setAdapter(adapter);
	        gender.setSelection(mProfileData.getInt("gender") == 1 ? 0 : 1);	        
		}
		catch (JSONException e) {
			Utility.displayError(e.getMessage(), this, sResources);
			return;
		}
	}
	
	protected void handleUpdateProfileResult(String pResponse, Object pUserData) {
		JSONObject response = null;
		
		try {
			if (pResponse != null && pResponse.trim().length() > 0) {
				response = new JSONObject(pResponse);
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
		
		Toast.makeText(getParent(), sResources.getString(R.string.profileUpdated), Toast.LENGTH_SHORT).show();
		closeActivity();
	}
}
