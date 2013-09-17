package com.demandware.mobile.android.activity.browse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.model.Product;
import com.demandware.mobile.android.model.VariationAttributeValue;
import com.demandware.mobile.android.task.HttpPostWebServiceTask;
import com.demandware.mobile.android.task.HttpRequestListener;
import com.demandware.mobile.android.task.ProductWebServiceTask;
import com.demandware.mobile.android.util.WebApi;

/**
 * This class represents the details of a product.
 * 
 * @author mparasirakis
 */
public class ProductActivity extends CatalogBrowseActivity implements ModelActivity, HttpRequestListener {
	protected static final String ADD_TO_CART = "addToCart";
	
	private TextView mTitle;
	
	/**
	 * Constructor
	 */
	public ProductActivity() {
		super();
	}
	
	/**
	 * Called when the activity is first created. It sets up the view and initiates a request to get the product details.
	 * 
	 * @param savedInstanceState the state that was supplied
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_layout);

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View pView) {
        		finish();
        	}
        });
        if (getModel() != null)
        	mTitle.setText(getModel().getName());

        runActivity();
    }
    
    /**
     * Initiates a request to get the product details.
     */
    public void runActivity() {
    	ProductWebServiceTask task = new ProductWebServiceTask(Product.class, this, sResources);
      	task.execute(WebApi.getInstance().getProductUrl(getId()));
    }
    
    /**
     * Handles the add to cart button clicks. This method first iterates through each variation and then
     * issues a request to add the product to the cart.
     * 
     * @param pView the view that was clicked on, in this case the add to cart button
     */
    public void addToCart(View pView) {
		Product prd = (Product) getModel();
		TextView quantityView = (TextView) findViewById(R.id.quantityTextInput);
		Spinner variationSpinner;
		List<VariationAttributeValue> selectedAttributes = new ArrayList<VariationAttributeValue>(3);

		for (int i = 1001; ; i++) { // 1001 is the id of the first spinner on the activity view
			variationSpinner = (Spinner) findViewById(i);
			if (variationSpinner == null)
				break;
			VariationAttributeValue value = (VariationAttributeValue) variationSpinner.getSelectedItem();
			selectedAttributes.add(value);
		}
		
		// try to find a match for the variants which the user selected
		Product variantToAdd = null;
		for (Product variant : prd.getVariants()) {
			Map<String,String> variantValues = variant.getVariationValues();
			boolean found = true;
			for (VariationAttributeValue selectedValue : selectedAttributes) {
				String selectedId = selectedValue.getId();
				String variantValue = variantValues.get(selectedId);
				if (variantValue == null || !variantValue.equals(selectedValue.getValue())) {
					found = false;
					break;
				}
			}
			if (found) {
				variantToAdd = variant;
				break;
			}
		}
		
		// if no variant was found, inform the user
		if (variantToAdd == null) {
			Toast toast = Toast.makeText(this,
					getResources().getString(R.string.productCannotBeAddedToCart, getModel().getName()),
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		
		// create the json and task to add the variant to the cart
		JSONObject json = new JSONObject();
		try {
			json.put("product_id", variantToAdd.getId());
			String valString = quantityView.getText().toString();

			int index;
			if ((index = valString.indexOf('.')) != -1)
				valString = valString.substring(0, index);
			json.put("quantity", Integer.valueOf(valString));
			
	    	Log.i("addToCart request", WebApi.getInstance().getAddToCartUrl() + " ***JSON BODY*** " + json);
			HttpPostWebServiceTask task = new HttpPostWebServiceTask(ADD_TO_CART, this, getResources());
			task.execute(WebApi.getInstance().getAddToCartUrl(), json);
		}
		catch (JSONException e) {
			Log.e(getResources().getString(R.string.error), e.getLocalizedMessage(), e);
		}		
    }
    
    /**
     * This method handles the response from the server after adding the product to the cart.
     * It displays a Toast message informing the user of success or failure.
     * 
     * @param pResponse the raw json response
     * @param pUserData optional user data which may be supplied in the web service task
     */
    protected void addToCartResponse(String pResponse, Object pUserData) {
    	String errorMsg = null;
    	Log.i("addToCart response", pResponse);
    	
    	try {
    		JSONObject resp = new JSONObject(pResponse);
    		if (resp.has("fault"))
    			errorMsg = resp.getJSONObject("fault").getString("message");
    	}
    	catch (JSONException e) {
    		Log.e(getResources().getString(R.string.error), e.getMessage(), e);
    	}
    	
		Toast toast;
		if (errorMsg != null)
			toast = Toast.makeText(this,
					getResources().getString(R.string.productCannotBeAddedToCart, getModel().getName()) + "(" + errorMsg + ")",
					Toast.LENGTH_LONG);
		else
			toast = Toast.makeText(this, R.string.itemAddedToCart, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
    }
    
    /**
     * This method handles the response for any tasks that were initiated by this class. This is called by
     * the web service task.
     * 
     * @param pOperationName the name of the operation
     * @param pResponse the raw json response string
     * @param pUserData user data which may have been supplied in the task before it was executed
     */
    public void httpRequestComplete(String pOperationName, String pResponse, Object pUserData) {
    	if (pOperationName.equals(ADD_TO_CART))
    		addToCartResponse(pResponse, pUserData);
    }
}
