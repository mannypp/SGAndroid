package com.demandware.mobile.android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Category extends ModelObjectImpl {
	private static final long serialVersionUID = -4663695271908017415L;
	
	public static final String SELECT_PARAMS = ModelObjectImpl.SELECT_PARAMS
			+ ",c_showInMenu,categories.(" + ModelObjectImpl.SELECT_PARAMS + ",c_showInMenu" + ")";
	
	public Category() {		
	}
	
	public Category(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public Category(JSONObject json) throws JSONException {
		create(json);
	}
	
	public void create(JSONObject json) throws JSONException {
		super.create(json);
		
		if (json.has("c_showInMenu"))
			setEnabled(json.getBoolean("c_showInMenu"));
	}

	protected void readImageData(JSONObject json) throws JSONException {
		if (json.has("image"))
			setImageUrl(json.getString("image"));
	}
}
