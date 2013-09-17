package com.demandware.mobile.android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CartItem {
	private double mBasePrice;
	private double mPrice;
	private String mItemText;
	private String mProductName;
	private String mProductId;
	private double mQuantity;
	
	public double getBasePrice() {
		return mBasePrice;
	}

	public void setBasePrice(double pBasePrice) {
		mBasePrice = pBasePrice;
	}

	public double getPrice() {
		return mPrice;
	}

	public void setPrice(double pPrice) {
		mPrice = pPrice;
	}

	public String getItemText() {
		return mItemText;
	}

	public void setItemText(String pItemText) {
		mItemText = pItemText;
	}

	public String getProductName() {
		return mProductName;
	}

	public void setProductName(String pProductName) {
		mProductName = pProductName;
	}

	public String getProductId() {
		return mProductId;
	}

	public void setProductId(String pProductId) {
		mProductId = pProductId;
	}

	public double getQuantity() {
		return mQuantity;
	}

	public void setQuantity(double pQuantity) {
		mQuantity = pQuantity;
	}

	public CartItem() {		
	}

	public CartItem(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}

	public CartItem(JSONObject json) throws JSONException {
		create(json);
	}

	public void create(JSONObject json) throws JSONException {
		if (json.has("base_price"))
			setBasePrice(json.getDouble("base_price"));
		if (json.has("price"))
			setPrice(json.getDouble("price"));
		if (json.has("item_text"))
			setItemText(json.getString("item_text"));
		if (json.has("product_name"))
			setProductName(json.getString("product_name"));
		if (json.has("product_id"))
			setProductId(json.getString("product_id"));
		if (json.has("quantity"))
			setQuantity(json.getDouble("quantity"));
	}
}