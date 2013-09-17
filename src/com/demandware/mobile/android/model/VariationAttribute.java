package com.demandware.mobile.android.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VariationAttribute extends ModelObjectImpl {
	private static final long serialVersionUID = -7519342817539357917L;

	private List<VariationAttributeValue> mValues;
	
	public List<VariationAttributeValue> getValues() {
		return mValues;
	}

	public void setValues(List<VariationAttributeValue> pValues) {
		mValues = pValues;
	}

	public VariationAttribute() {
		
	}
	
	public VariationAttribute(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public VariationAttribute(JSONObject json) throws JSONException {
		create(json);
	}
	
	public void create(JSONObject json) throws JSONException {
		super.create(json);

		if (json.has("values")) {
			JSONArray values = json.getJSONArray("values");
			List<VariationAttributeValue> list = new ArrayList<VariationAttributeValue>(values.length());
			setValues(list);
			for (int i = 0; i < values.length(); i++) {
				VariationAttributeValue attrValue = new VariationAttributeValue((JSONObject) values.get(i));
				attrValue.setId(getId()); // put the variation attribute's id into the VariationAttributeValue's id field. This simplifies processing later when adding to cart.
				if (attrValue.isEnabled())
					list.add(attrValue);
			}
		}
	}	
}