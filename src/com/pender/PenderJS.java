

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

		mJSScope = scope;
	}
    //==========================================================================
	public int loadImage( String path  ) {
		Message msg = new Message();
		final String fpath = path;
		int id = UUID.randomUUID().hashCode();
		msg.obj = fpath;
		msg.arg1 = 0;
		msg.arg2 = id;
		mHandler.dispatchMessage(msg);
		
    	mJSContext = Context.enter();
    	mJSContext.setOptimizationLevel(-1);
    	String script = "";
    	mJSContext.evaluateString (mJSScope, 
        						   script, 
        						   "load script", 
        						   0, 
        						   null);
	    Context.exit();
	    return id;
	}
	//==========================================================================
	public void _addImage( int id, Image img ) {
		mImageMap.put(id,img);
	}
	//==========================================================================
	//==========================================================================
	private PenderMessageHandler mHandler;
	HashMap<Integer,Image> mImageMap;
	Context mJSContext;
	Scriptable mJSScope;
	//==========================================================================
}