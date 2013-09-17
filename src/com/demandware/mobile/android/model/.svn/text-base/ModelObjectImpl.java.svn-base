package com.demandware.mobile.android.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ModelObjectImpl implements ModelObject, Serializable {
	private static final long serialVersionUID = 1569650743585798499L;
	
	private String mId;
	private String mName;
	private String mImageUrl;
	private boolean mEnabled;
	
	public static final String SELECT_PARAMS = "id,name,image";
	
	public String getId() {
		return mId;
	}

	public void setId(String pId) {
		mId = pId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getImageUrl() {
		return mImageUrl;
	}

	public void setImageUrl(String pImageUrl) {
		mImageUrl = pImageUrl;
	}

	public boolean isEnabled() {
		return mEnabled;
	}

	public void setEnabled(boolean pEnabled) {
		mEnabled = pEnabled;
	}

	public ModelObjectImpl() {		
	}
	
	public ModelObjectImpl(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public ModelObjectImpl(JSONObject json) throws JSONException {
		create(json);
	}
	
	public void create(JSONObject json) throws JSONException {
		if (json.has("id"))
			setId(json.getString("id"));
		if (json.has("name"))
			setName(json.getString("name"));
		readImageData(json);
	}
	
	protected void readImageData(JSONObject json) throws JSONException {
	}
	
	public String toString() {
		return getName();
	}
}
