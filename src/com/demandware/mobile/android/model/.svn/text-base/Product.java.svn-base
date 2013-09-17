package com.demandware.mobile.android.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product extends ModelObjectImpl {
	private static final long serialVersionUID = 7250069818997816300L;
	
	private String mCurrency;
	private boolean mOrderable = true;	
	private double mPrice = 0.0d;
	private String mShortDescription;
	private String mBrand;
	private String mSmallImageUrl;
	private String mMediumImageUrl;
	private String mLargeImageUrl;
	private List<Product> mVariants;
	private List<VariationAttribute> mVariationAttributes;
	private Map<String,String> mVariationValues;
	
	public static final String SELECT_VARIANTS = "variants.(product_id,price,orderable,variation_values)";
	public static final String SELECT_PARAMS = ModelObjectImpl.SELECT_PARAMS + ",currency,orderable,price,brand,short_description,image_groups.(view_type,images.(link)),variation_attributes," + SELECT_VARIANTS;
	public static final String SEARCH_SELECT_PARAMS = "count,hits.(product_id,product_name,image.(link),orderable,price,currency)";
	
	public static final String VIEWTYPE_SMALL = "small";
	public static final String VIEWTYPE_MEDIUM = "medium";
	public static final String VIEWTYPE_LARGE = "large";
	
	public String getCurrency() {
		return mCurrency;
	}

	public void setCurrency(String pCurrency) {
		mCurrency = pCurrency;
	}

	public boolean isOrderable() {
		return mOrderable;
	}

	public void setOrderable(boolean pOrderable) {
		mOrderable = pOrderable;
	}
	
	public double getPrice() {
		return mPrice;
	}

	public void setPrice(double pPrice) {
		mPrice = pPrice;
	}
	
	public boolean isEnabled() {
		return isOrderable();
	}

	public String getShortDescription() {
		return mShortDescription;
	}

	public void setShortDescription(String pShortDescription) {
		mShortDescription = pShortDescription;
	}

	public String getBrand() {
		return mBrand;
	}

	public void setBrand(String pBrand) {
		mBrand = pBrand;
	}

	public String getSmallImageUrl() {
		return mSmallImageUrl;
	}

	public void setSmallImageUrl(String pSmallImageUrl) {
		mSmallImageUrl = pSmallImageUrl;
	}

	public String getMediumImageUrl() {
		return mMediumImageUrl;
	}

	public void setMediumImageUrl(String pMediumImageUrl) {
		mMediumImageUrl = pMediumImageUrl;
	}

	public String getLargeImageUrl() {
		return mLargeImageUrl;
	}

	public void setLargeImageUrl(String pLargeImageUrl) {
		mLargeImageUrl = pLargeImageUrl;
	}

	public List<Product> getVariants() {
		return mVariants;
	}

	public void setVariants(List<Product> pVariants) {
		mVariants = pVariants;
	}
	
	public List<VariationAttribute> getVariationAttributes() {
		return mVariationAttributes;
	}

	public void setVariationAttributes(List<VariationAttribute> pVariationAttributes) {
		mVariationAttributes = pVariationAttributes;
	}

	public Map<String, String> getVariationValues() {
		return mVariationValues;
	}

	public void setVariationValues(Map<String, String> pVariationValues) {
		mVariationValues = pVariationValues;
	}

	//-----------------------------------
	public Product() {		
	}
	
	public Product(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public Product(JSONObject json) throws JSONException {
		create(json);
	}
	
	public void create(JSONObject json) throws JSONException {
		super.create(json);
		if (json.has("product_id"))
			setId(json.getString("product_id"));
		if (json.has("product_name"))
			setName(json.getString("product_name"));

		if (json.has("currency"))
			setCurrency(json.getString("currency"));
		if (json.has("price"))
			setPrice(json.getDouble("price"));			
		if (json.has("orderable"))
			setOrderable(json.getBoolean("orderable"));
		if (json.has("short_description"))
			setShortDescription(json.getString("short_description"));
		if (json.has("brand"))
			setBrand(json.getString("brand"));

		if (json.has("variants")) {
			JSONArray variants = json.getJSONArray("variants");
			List<Product> list = new ArrayList<Product>(variants.length());
			setVariants(list);
			for (int i = 0; i < variants.length(); i++) {
				Product productVariant = new Product((JSONObject) variants.get(i));
				if (productVariant.isEnabled())
					list.add(productVariant);
			}
		}
		
		if (json.has("variation_attributes")) {
			JSONArray variationAttributes = json.getJSONArray("variation_attributes");
			List<VariationAttribute> list = new ArrayList<VariationAttribute>(variationAttributes.length());
			setVariationAttributes(list);
			for (int i = 0; i < variationAttributes.length(); i++) {
				VariationAttribute attr = new VariationAttribute((JSONObject) variationAttributes.get(i));
				list.add(attr);
			}
		}

		// used for variants
		if (json.has("product_id"))
			setId(json.getString("product_id"));
		if (json.has("variation_values")) {
			Map<String,String> variationValues = new HashMap<String,String>(11);
			setVariationValues(variationValues);
			
			JSONObject jsonVariationValues = json.getJSONObject("variation_values");
			Iterator<?> iter = jsonVariationValues.keys();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				variationValues.put(key, jsonVariationValues.getString(key));
			}
		}
	}
	
	public String getVariantIds() {
		return getVariantIds(',');
	}

	public String getVariantIds(char separator) {
		StringBuilder sb = new StringBuilder();
		List<Product> variants = getVariants();
		for (int i = 0, size = variants.size(); i < size; i++) {
			Product variant = (Product) variants.get(i);
			sb.append(variant.getId());
			if (i < size - 1)
				sb.append(separator);
		}
		return sb.toString();
	}

	protected void readImageData(JSONObject json) throws JSONException {
		if (json.has("image")) {
			JSONObject image = json.getJSONObject("image");
			setImageUrl(image.getString("link"));
		}

		if (json.has("image_groups")) {
			JSONArray imageGroups = json.getJSONArray("image_groups");
			for (int i = 0; i < imageGroups.length(); i++) {
				JSONObject imageGroup = (JSONObject) imageGroups.get(i);
				
				String viewType = imageGroup.getString("view_type");
				JSONArray images = imageGroup.getJSONArray("images");
				if (images == null)
					continue;
				JSONObject image = (JSONObject) images.get(0);
				if (image == null)
					continue;
				String imageLink = image.getString("link");
				if (imageLink == null)
					continue;
				
				if (viewType.equalsIgnoreCase(VIEWTYPE_SMALL))
					setSmallImageUrl(imageLink);
				else if (viewType.equalsIgnoreCase(VIEWTYPE_MEDIUM))
					setMediumImageUrl(imageLink);
				else if (viewType.equalsIgnoreCase(VIEWTYPE_LARGE))
					setLargeImageUrl(imageLink);
			}
		}		
	}
}
