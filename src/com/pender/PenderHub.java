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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;  
import android.util.Log;



public class PenderHub extends Handler {
	
	public PenderHub( PenderActivity activity ) {
		
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
	
	/**
	 * 
	 * @param msg
	 */
	public void runScript(Message msg) {
    	BufferedReader reader = null;
    	StringBuffer script = new StringBuffer();
    	String path = null;
		try { 
			path = (String)msg.obj;
	  	    AssetManager al = this.mActivity.getAssets(); //sharpshooting hulk
	  	    reader = new BufferedReader( 
  					new InputStreamReader( 
  						al.open(path)));
	  	    String buf = "";
	  	    while(( buf = reader.readLine()) != null) {
	  	    	script.append("\n");
	  	    	script.append(buf);
	  	    }
		} catch (IOException e ) {
  	    	Log.d("exception", e.toString() );
  	    	Log.d("PENDER","STRING PATH: "+path+" DOES NOT COMPUTE. Please ensure that the file exists in a subdirectory of assets eg \"demos/client/penderdemo.js\"");
  	    }
		String str = script.toString();
		mActivity.getView().execScripts(msg.obj);
	}

	/**
	 * 
	 */
	public void interpretFile() {
		
	}
	
	public PenderActivity getActivity() { return mActivity; }

	private PenderActivity mActivity;
	
}