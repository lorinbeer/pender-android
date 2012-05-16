

package com.pender;

import java.util.HashMap;
import java.util.UUID;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import android.os.Message;

import com.pender.PenderMessageHandler;
import com.pender.glaid.Image;


public class PenderJS {
    
	public PenderJS(PenderMessageHandler handler, Context ctx, Scriptable scope) {
		mHandler = handler;
		mImageMap = new HashMap<Integer,Image>();
		mReady = true;
		mJSScope = scope;
	}
    //==========================================================================
	public int loadImage (String path) {
		mReady = false;
		Message msg = new Message();
		final String fpath = path;
		int id = UUID.randomUUID().hashCode();
		msg.obj = fpath;
		msg.arg1 = 0;
		msg.arg2 = id;
		mHandler.dispatchMessage(msg);
		mImageMap.put(id, null);
	    return id;
	}
	//==========================================================================
	public Image getImage (int id) {
		return mImageMap.get(id);
	}
    //==========================================================================
	public boolean getState() {
		return mReady;
	}
	//==========================================================================
	private PenderMessageHandler mHandler;
	private HashMap<Integer,Image> mImageMap;
	
	private boolean mReady;
	
	private Context mJSContext;
	private Scriptable mJSScope;
	//==========================================================================
}