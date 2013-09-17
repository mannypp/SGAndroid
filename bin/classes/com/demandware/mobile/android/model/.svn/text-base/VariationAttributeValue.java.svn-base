package com.demandware.mobile.android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class VariationAttributeValue extends ModelObjectImpl {
	private static final long serialVersionUID = 4889423089151245206L;
	
	private boolean mOrderable = true;
	private String mValue;
	
	public boolean isOrderable() {
		return mOrderable;
	}

	public void setOrderable(boolean pOrderable) {
		mOrderable = pOrderable;
	}

	public boolean isEnabled() {
		return isOrderable();
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(String pValue) {
		mValue = pValue;
	}

	//--------------------------------
	public VariationAttributeValue() {
	}
	
	public VariationAttributeValue(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public VariationAttributeValue(JSONObject json) throws JSONException {
		create(json);
	}
	
	public void create(JSONObject json) throws JSONException {
		super.create(json);
		
		if (json.has("orderable"))
			setOrderable(json.getBoolean("orderable"));
		if (json.has("value"))
			setValue(json.getString("value"));
	}
}