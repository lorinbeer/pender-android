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

import java.util.HashMap;
import java.util.UUID;

import android.os.Message;

import com.pender.PenderMessageHandler;
import com.pender.glaid.Image;


public class PenderJS {
    //==========================================================================    
	/**
	 * 
	 * @param handler
	 */
	public PenderJS (PenderMessageHandler handler) {
		mHandler = handler;
		mImageMap = new HashMap<Integer,Image>();
		mPendingAssets = 0;
		mReady = true;
	}
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
	/**
	 * query the current state of Pender
	 * 	true is Ready
	 * 	false is pending
	 * @return the current state of Pender, true if Ready
	 */
	public boolean getState () {
		return mReady;
	}
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
	private PenderMessageHandler mHandler;
	private HashMap<Integer,Image> mImageMap;
	private int mPendingAssets;
	private boolean mReady;
	//==========================================================================
}