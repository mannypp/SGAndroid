package com.demandware.mobile.android.activity.cart;

import java.text.NumberFormat;
import java.util.Currency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.model.Cart;
import com.demandware.mobile.android.model.CartItem;
import com.demandware.mobile.android.task.HttpGetWebServiceTask;
import com.demandware.mobile.android.task.HttpPatchWebServiceTask;
import com.demandware.mobile.android.task.HttpRequestListener;
import com.demandware.mobile.android.util.Utility;
import com.demandware.mobile.android.util.WebApi;

public class CartActivity extends Activity implements HttpRequestListener {
	//-------------------------------
	protected static Resources sResources;

	private static final String REMOVE_MARKER = "removeItem";
	
	// operation names
	protected static final String GET_CART_CONTENTS = "getCartContents";
	protected static final String CART_UPDATE = "cartUpdate";
	
	// action names
	public static final String DISPLAY_CART_TAB = "com.demandware.mobile.android.activity.cart.DISPLAY_CART_TAB";
	
	//-------------------------------
	private Cart mCurrentCart;
	
	public Cart getCurrentCart() {
		return mCurrentCart;
	}

	public void setCurrentCart(Cart pCurrentCart) {
		mCurrentCart = pCurrentCart;
	}

	//-------------------------------
	public CartActivity() {
		
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sResources = getResources();
        setContentView(R.layout.cart_layout);
        
        IntentFilter filter = new IntentFilter(DISPLAY_CART_TAB);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            	String action = intent.getAction();
            	if (action.equals(DISPLAY_CART_TAB))
            		refreshActivityContent(intent);
            }
        }, filter);        

        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View pView) {
				try {
					updateCartQuantities();
				}
				catch (JSONException e) {
					Log.e("Error", "Error occurred which creating JSON to update cart", e);
				}
			}
		});
        
        refreshActivityContent(null);
    }
    
    protected void refreshActivityContent(Intent pIntent) {
    	Log.i("Refresh Cart", "Refreshing cart view");
    	HttpGetWebServiceTask task = new HttpGetWebServiceTask(GET_CART_CONTENTS, this, sResources);
      	task.execute(WebApi.getInstance().getCartContentsUrl());
    }
    
    public void httpRequestComplete(String pOperationName, String pResponse, Object pUserData) {
    	try {
	    	JSONObject json = new JSONObject(pResponse);
	    	if (json.has("fault")) {
	    		Utility.displayError(json.getJSONObject("fault").getString("message"), this, sResources);
	    		return;
	    	}
    	}
    	catch (JSONException e) {
    		Utility.displayError(e.getMessage(), this, sResources);
    		return;
    	}
    	
    	if (pOperationName.equals(GET_CART_CONTENTS)
    			|| pOperationName.equals(CART_UPDATE))
    		updateCartView(pResponse, pUserData);
    }
    
    protected void updateCartView(String jsonString, Object pUserData) {
    	Log.i("updateCartView response", jsonString);

    	try {
    		setCurrentCart(new Cart(jsonString));
    	}
    	catch (JSONException e) {
    		Log.e(getResources().getString(R.string.error), e.getMessage(), e);
    		Utility.displayError(e.getMessage(), this, sResources);
    		return;
    	}
    	
    	TableLayout cartTable = (TableLayout) findViewById(R.id.cartTable);
    	LayoutInflater inflater = getLayoutInflater();
    	
    	cartTable.removeAllViews();

    	Cart cart = getCurrentCart();
    	NumberFormat currFormat = NumberFormat.getCurrencyInstance();
    	currFormat.setCurrency(Currency.getInstance(cart.getCurrency()));

    	// setup the headers
    	View row = inflater.inflate(R.layout.cart_header_row_layout, cartTable, false);
		cartTable.addView(row);
    	
		// fill in the table with the products
    	for (CartItem item : cart.getItems()) {
    		row = inflater.inflate(R.layout.cart_row_layout, cartTable, false);
    		
    		EditText quantity = (EditText) row.findViewById(R.id.cartQuantity);
    		String quant = Double.toString(item.getQuantity());
    		if (quant.endsWith(".0"))
    			quant = quant.substring(0, quant.indexOf('.'));
    		quantity.setText(quant);
    		
    		TextView product = (TextView) row.findViewById(R.id.cartProduct);
    		product.setText(item.getProductName());
    		
    		TextView price = (TextView) row.findViewById(R.id.cartPrice);
    		price.setText(currFormat.format(item.getPrice()));
    		
    		ImageView removeButton = (ImageView) row.findViewById(R.id.removeButton);
    		removeButton.setTag(quantity);
    		removeButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					try {
						EditText quantity = (EditText) v.getTag();
						quantity.setTag(REMOVE_MARKER);
						updateCartQuantities();
					}
					catch (JSONException e) {
						Log.e("Error", "Error occurred which creating JSON to update cart", e);
					}
				}
			});

    		cartTable.addView(row);
    	}
    	
    	TextView subtotal = (TextView) findViewById(R.id.subtotal);
    	subtotal.setText(currFormat.format(cart.getSubtotal()));
    	
		Button updateButton = (Button) findViewById(R.id.updateButton);
		Button checkoutButton = (Button) findViewById(R.id.checkoutButton);
		boolean enabled = cart.getItems().size() != 0;
		updateButton.setEnabled(enabled);
		checkoutButton.setEnabled(enabled);
    }
    
    protected void updateCartQuantities() throws JSONException {
    	Cart cart = getCurrentCart();
    	TableLayout cartTable = (TableLayout) findViewById(R.id.cartTable);
    	int i = 0;
    	
		JSONObject json = new JSONObject();
		JSONArray itemsJSON = new JSONArray();
		json.put("product_items", itemsJSON);

		for (CartItem item : cart.getItems()) {
    		i++;
    		View row = cartTable.getChildAt(i);
    		EditText quantity = (EditText) row.findViewById(R.id.cartQuantity);

    		double rowQuantity = Double.valueOf(quantity.getText().toString());
    		String quantityTag = (String) quantity.getTag();
    		if (quantityTag != null && quantityTag.equals(REMOVE_MARKER))
    			rowQuantity = 0;
    		
    		JSONObject quantityUpdate = new JSONObject();
    		if (item.getQuantity() != rowQuantity)    		
    			quantityUpdate.put("quantity", rowQuantity);
    		itemsJSON.put(quantityUpdate);
    	}
		
		HttpPatchWebServiceTask task = new HttpPatchWebServiceTask(CART_UPDATE, this, sResources);
		task.execute(WebApi.getInstance().getCartUpdateUrl(), json);
    }    
}
