package com.demandware.mobile.android.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * An AsyncTask which specifically is used to load images from the server.
 * 
 * @author mannyp
 */
public class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
	//-----------------------------------
	private ImageView mImageView;
	
	public ImageView getImageView() {
		return mImageView;
	}

	public void setImageView(ImageView pImageView) {
		mImageView = pImageView;
	}

	//-----------------------------------
	/**
	 * Constructor
	 * 
	 * @param pView the View which the image will be displayed in
	 */
	public ImageLoaderTask(ImageView pView) {
		setImageView(pView);
	}
	
	/**
	 * Issues a request to the server to retrieve an image.
	 * 
	 * @param pUrl the url for the request
	 * @return a Bitmap object
	 */
	public Bitmap doInBackground(String... pUrl) {
		InputStream is = null;
		try {
			URL url = new URL(pUrl[0]);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			return BitmapFactory.decodeStream(is);
		}
		catch(MalformedURLException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
		}
		catch (IOException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
		}
		finally {
			try {
				if (is != null)
					is.close();
			}
			catch (IOException e) {
				Log.e(this.getClass().getName(), e.getMessage(), e);
			}
		}
		return null;
	}
	
	/**
	 * Called after the response is received. Puts the image into the image view.
	 */
	protected void onPostExecute(Bitmap pBitmap) {
		if (pBitmap != null)
			getImageView().setImageBitmap(pBitmap);
	}
}
