/**
 * Copyright 2012 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.pender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import android.os.Message;
import android.util.Log;


//import org.mozilla.javascript.
//import org.mozilla.javascript.Function;
//import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.*;
import com.pender.PenderHub;
import com.pender.glaid.Image;

/**
 * PenderJS
 *   js utility class, provides the 'Pender' object expected by demo apps
 */
public class PenderJS {
	//==========================================================================
	// Public members, to be accessed in a js like way
	//==========================================================================

	public PenderCanvas canvas;
	public int width;
	public int height;

	//==========================================================================
	//==========================================================================    

	/**
	 * 
	 * @param handler
	 */
	public PenderJS (PenderHub handler,
					 PenderCanvas pendercanvas) {
		mHandler = handler;
		canvas = pendercanvas;
		width = 720;//magic number
		height = 1084;//magic number
		mDelayed = new ArrayList<FuncDelayPair>();
		mImageMap = new HashMap<Integer,Image>();
		mPendingAssets = 0;
		mReady = true;
	}
	
	//==========================================================================
    //==========================================================================
	
	/**
	 * 
	 * @param path
	 * @return
	 */
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
		mPendingAssets++;
	    return id;
	}

	//==========================================================================
	//==========================================================================

	/**
	 * attemtp to retrieve an image with the given id
	 * @param id of the image to retrieve
	 * @return image if found, null if not found or if not yet loaded
	 */
	public Image getImage (int id) {
		if (mImageMap.containsKey(id) ) {
			return mImageMap.get(id);
		}
		return null;
	}

    //==========================================================================
	//==========================================================================

	/**
	 * 
	 */
	public void setImage (int id, Image img) {
		if (--mPendingAssets<=0) {
			mReady = true;
		}
        mImageMap.put (id,img);
	}

	//==========================================================================
	//==========================================================================

	/**
	 * 
	 * @param func
	 * @param period
	 */
	public void setInterval (Function func, float period) {
		FuncDelayPair delayed = new FuncDelayPair(func, period, true);
		mDelayed.add(delayed);
	}

	//==========================================================================
	//==========================================================================

	/**
	 * 
	 * @param func
	 * @param period
	 */
	public void setTimeout (ScriptableObject func, float period) {
		//FuncDelayPair delayed = new FuncDelayPair(func, period, false);
	//	mDelayed.add(delayed);
	}

	//==========================================================================
	//==========================================================================

	/**
	 * query the current state of Pender
	 * 	true is Ready
	 * 	false is pending
	 * @return the current state of Pender, true if Ready
	 */
	public boolean ready () {
		return mReady;
	}

	//==========================================================================
	//==========================================================================
	/*
	 * replace with circular array
	 */
	public ArrayList<FuncDelayPair> getDelayed() {
		return mDelayed;
	}

	//==========================================================================
	// Private Members
	//==========================================================================

	// list of functions to execute after a specified period/delay
	private ArrayList<FuncDelayPair> mDelayed;
	
	// custom message handler
	private PenderHub mHandler;
	
	// blarg
	private HashMap<Integer,Image> mImageMap;

	// number of assets 
	private int mPendingAssets;

	// ready flag, off when waiting on asynchronous processes
	private boolean mReady;
	//==========================================================================
}