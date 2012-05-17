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