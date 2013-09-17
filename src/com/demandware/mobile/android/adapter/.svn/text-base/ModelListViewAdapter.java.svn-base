package com.demandware.mobile.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.demandware.mobile.android.R;
import com.demandware.mobile.android.model.ModelObject;
import com.demandware.mobile.android.task.ImageLoaderTask;

public abstract class ModelListViewAdapter implements ListAdapter {
	private List<DataSetObserver> mObservers = new ArrayList<DataSetObserver>(3);
	
	//---------------------------------
	private Activity mActivity;
	
	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity pActivity) {
		mActivity = pActivity;
	}

	//---------------------------------
	private ModelObject[] mObjects;

	public ModelObject[] getObjects() {
		return mObjects;
	}

	public void setObjects(ModelObject[] pObjects) {
		mObjects = pObjects;
	}

	//---------------------------------
	public ModelListViewAdapter() {
		
	}
	
	public int getCount() {
		ModelObject[] objects = getObjects();
		if (objects == null)
			return 0;
		return objects.length;
	}

	public ModelObject getItem(int pArg0) {
		ModelObject[] objects = getObjects();
		if (objects != null && pArg0 < objects.length && pArg0 >= 0)
			return objects[pArg0];
		return null;
	}

	public long getItemId(int pArg0) {
		return pArg0;
	}

	public int getItemViewType(int pArg0) {
		return 0;
	}

	protected abstract View inflateView(View pView);
	
	protected void setText(View pView, int pId, String pValue) {
		TextView view = (TextView) pView.findViewById(pId);
		view.setText(pValue);
		view.setTag(pView.getTag());
	}
	
	protected String convertToSmallImageUrl(String pUrl) {
		int index = pUrl.lastIndexOf("/large/");		
		if (index != -1) {
			StringBuffer buf = new StringBuffer(pUrl.substring(0, index));
			buf.append("/small/");
			buf.append(pUrl.substring(index + "/large/".length()));
			return buf.toString();
		}
		index = pUrl.lastIndexOf("/medium/");		
		if (index != -1) {
			StringBuffer buf = new StringBuffer(pUrl.substring(0, index));
			buf.append("/small/");
			buf.append(pUrl.substring(index + "/medium/".length()));
			return buf.toString();
		}
		return pUrl;
	}
	
	protected void setImage(View pView, int pId, ModelObject pModel) {
		ImageView view = (ImageView) pView.findViewById(pId);
		view.setImageDrawable(null);
		view.setTag(pView.getTag()); // set the model object on the view
		if (pModel.getImageUrl() != null) {
			ImageLoaderTask task = new ImageLoaderTask(view);
			task.execute(convertToSmallImageUrl(pModel.getImageUrl()));
		}
		/*else { // if there is no image, collapse the image view to 0 width
			LayoutParams params = view.getLayoutParams();
			params.width = 0;
			params.height = 0;
			view.setLayoutParams(params);
		}*/
	}

	public View getView(int pPos, View pView, ViewGroup pGroup) {
		if (pView == null) {
			pView = inflateView(pView);
			if (getActivity() instanceof OnClickListener)
				pView.setOnClickListener((OnClickListener) getActivity());
		}
		
		Log.d("loadImage Position", Integer.toString(pPos));
		ModelObject model = getItem(pPos);
		pView.setTag(model);

		setText(pView, R.id.label, model.getName());
		setImage(pView, R.id.icon, model);

		return pView;
	}
	
	public int getViewTypeCount() {
		return 1;
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isEmpty() {
		if (getObjects() == null || getObjects().length == 0)
			return true;
		return false;
	}

	public void registerDataSetObserver(DataSetObserver pArg0) {
		mObservers.add(pArg0);
	}

	public void unregisterDataSetObserver(DataSetObserver pArg0) {
		mObservers.remove(pArg0);
	}

	public boolean areAllItemsEnabled() {
		return true;
	}

	public boolean isEnabled(int pArg0) {
		return true;
	}
}
