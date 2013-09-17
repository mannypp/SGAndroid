package com.demandware.mobile.android.task;

import java.util.Currency;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.activity.browse.ModelActivity;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.model.Product;
import com.demandware.mobile.android.model.VariationAttribute;
import com.demandware.mobile.android.model.VariationAttributeValue;

/**
 * A specialized AsyncTask for loading a single product from the server.
 * 
 * @author mannyp
 */
public class ProductWebServiceTask extends ModelWebServiceTask {
	/**
	 * Constructor
	 * 
	 * @param pModelClass the class object to construct
	 * @param pActivity the activity which created this task
	 * @param pResources a Resources object for strings
	 */
	public ProductWebServiceTask(Class<? extends ModelObject> pModelClass, Activity pActivity, Resources pResources) {
		setModelClass(pModelClass);
		setActivity(pActivity);
		setResources(pResources);
	}
	
	/**
	 * Overrides the base class method. Loads the image from the given object into the view with the given id.
	 * 
	 * @param pId the id of the view to put the image into
	 * @param pModel the object containing the url of the image
	 * @return the view the image was set into
	 */
	protected ImageView setImage(int pId, ModelObject pModel) {
		ImageView view = super.setImage(pId, pModel);
		if (view != null && pModel instanceof Product) {
			Product prod = (Product) pModel;
			if (prod.getLargeImageUrl() != null)
				loadImage(prod.getLargeImageUrl(), view);
			return view;
		}
		return null;
	}
	
	/**
	 * Called after the response is retrieve from the server to put all the data into the product view
	 * 
	 * @param objectArray an array containing the product whose data needs to be displayed
	 */
	protected void onPostExecute(ModelObject[] objectArray) {
		super.onPostExecute(objectArray);
		ModelObject object = objectArray[0];
		if (object == null)
			return;

		Product prod = (Product) object;
		Activity activity = getActivity();
		((ModelActivity) activity).setModel(prod); // replace the model in the activity with this one which contains far more details
		
		// hide the contents in the view until everything is set into it
		ScrollView scrollView = (ScrollView) activity.findViewById(R.id.productScrollView);
		scrollView.setVisibility(View.INVISIBLE);

		View addButton = activity.findViewById(R.id.addToCartButton);
		addButton.setTag(prod);

		// set the price, brand, and description fields
		setText(R.id.price, Currency.getInstance(prod.getCurrency()).getSymbol() + Double.toString(prod.getPrice()));		
		setText(R.id.brand, prod.getBrand());
		setText(R.id.description, prod.getShortDescription());
		
		List<Product> variantProducts = prod.getVariants();
		if (variantProducts == null)
			return;
		
		// layout all the variant information by constructing spinners for each variant and putting the variant attributes
		// into each spinner
		List<VariationAttribute> attr = prod.getVariationAttributes();
		if (attr != null) {
			RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.productLayout);			
			View layoutBelow = activity.findViewById(R.id.description);
			RelativeLayout.LayoutParams params;
			
			int i = 1001;
			for (VariationAttribute va : attr) {
				Spinner spinner = new Spinner(activity.getParent()); //, Spinner.MODE_DROPDOWN);
				spinner.setId(i);
				spinner.setTag(attr);
				spinner.setPrompt(va.getName());

				ArrayAdapter<VariationAttributeValue> spinnerArrayAdapter = new ArrayAdapter<VariationAttributeValue>(
								activity.getParent(), android.R.layout.simple_spinner_item, va.getValues());
				spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);				
				spinner.setAdapter(spinnerArrayAdapter);
				
				params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.BELOW, layoutBelow.getId());
				spinner.setLayoutParams(params);

				layout.addView(spinner);
				
				layoutBelow = spinner;
				i++;
			}
			
			View quantity = activity.findViewById(R.id.quantityTextInput);
			
			params = new RelativeLayout.LayoutParams(75, 50);
			params.addRule(RelativeLayout.BELOW, layoutBelow.getId());
			quantity.setLayoutParams(params);
			
			if (quantity.hasFocus())
				quantity.clearFocus();
			
			params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, 65);
			params.addRule(RelativeLayout.BELOW, layoutBelow.getId());
			params.addRule(RelativeLayout.RIGHT_OF, quantity.getId());
			addButton.setLayoutParams(params);
		}
		
		// post a message to the scrollview to scroll to the top and display itself
		scrollView.post(new Runnable() {
			public void run() {
				ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.productScrollView);
				scrollView.scrollTo(0, 0);
				scrollView.setVisibility(View.VISIBLE);
			}
		});
	}
}
