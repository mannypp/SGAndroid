package com.demandware.mobile.android.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cart {
	private String mCurrency;
	private double mSubtotal;
	private double mTotal;
	private List<CartItem> mItems;
	
	public String getCurrency() {
		return mCurrency;
	}

	public void setCurrency(String pCurrency) {
		mCurrency = pCurrency;
	}

	public double getSubtotal() {
		return mSubtotal;
	}

	public void setSubtotal(double pSubtotal) {
		mSubtotal = pSubtotal;
	}

	public double getTotal() {
		return mTotal;
	}

	public void setTotal(double pTotal) {
		mTotal = pTotal;
	}

	public List<CartItem> getItems() {
		return mItems;
	}

	public void setItems(List<CartItem> pItems) {
		mItems = pItems;
	}

	public Cart() {		
	}
	
	public Cart(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public Cart(JSONObject json) throws JSONException {
		create(json);
	}
	
	public CartItem findProduct(String productId) {
		for (CartItem item : getItems()) {
			if (productId.equals(item.getProductId()))
				return item;
		}
		return null;
	}
	
	public boolean productInCart(String productId) {
		return (findProduct(productId) != null);
	}
	
	public void create(JSONObject json) throws JSONException {
		if (json.has("currency"))
			setCurrency(json.getString("currency"));
		if (json.has("product_sub_total"))
			setSubtotal(json.getDouble("product_sub_total"));
		if (json.has("product_total"))
			setTotal(json.getDouble("product_total"));
		
		if (json.has("product_items")) {
			JSONArray items = json.getJSONArray("product_items");
			List<CartItem> list = new ArrayList<CartItem>(items.length());
			setItems(list);
			for (int i = 0; i < items.length(); i++) {
				CartItem cartItem = new CartItem((JSONObject) items.get(i));
				list.add(cartItem);
			}
		}
	}
}