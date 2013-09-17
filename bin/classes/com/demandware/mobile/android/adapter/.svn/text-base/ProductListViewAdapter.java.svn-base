package com.demandware.mobile.android.adapter;

import java.util.Currency;

import android.view.View;
import android.view.ViewGroup;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.model.Product;

public class ProductListViewAdapter extends ModelListViewAdapter {
	public ProductListViewAdapter() {		
	}
	
	protected View inflateView(View pView) {
		return getActivity().getLayoutInflater().inflate(R.layout.product_row_layout, null);
	}
	
	public View getView(int pPos, View pView, ViewGroup pGroup) {
		View view = super.getView(pPos, pView, pGroup);

		Product prod = (Product) getItem(pPos);
		setText(view, R.id.price, Currency.getInstance(prod.getCurrency()).getSymbol() + Double.toString(prod.getPrice()));
		
		return view;
	}
}
