package com.pender;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;  



public class PenderMessageHandler extends Handler {
	
	public PenderMessageHandler( PenderActivity activity ) {
		
		mActivity = activity;
		
	}
	public void handleMessage(Message msg) {
		InputStream instream = null;
		try { 
			String path = (String)msg.obj;
			instream = mActivity.getAssets().open( path );
		    final Bitmap temp = BitmapFactory.decodeStream(instream);
		    mActivity.getView().loadTexture( temp, msg.arg2 );		
		} catch (IOException e) {
			e.printStackTrace();
		}
}
		
	
	
	public PenderActivity getActivity() { return mActivity; }
	
	private PenderActivity mActivity;
	
}