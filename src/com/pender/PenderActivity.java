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
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.widget.RelativeLayout;

public class PenderActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new PenderMessageHandler(this);

        mGLView = new PenderView(mHandler);
        initView();
        /*
        ArrayList<String> scripts = new ArrayList<String>();
        // specify location of the shim relative to Assets directory
        scripts.add(readJS("penderandroidshim.js"));
        // specify location of js implementation of client app
        scripts.add(readJS("demos/client/penderdemo.js") );
        // pin an init call to the end, calls default init(), unless redefined
        scripts.add("init();");
        // execute the scripts in order specified
        ((PenderView)mGLView).execScripts(scripts);
        */
        ArrayList<String> scripts = new ArrayList<String>();
        scripts.add(readJS("penderandroidshim.js"));
      /*scripts.add(readJS("www/sunspider/sunspider-test-contents.js"));
        scripts.add(readJS("www/sunspider/sunspider-test-prefix.js"));
        scripts.add(readJS("www/sunspider/sunspider.js"));
        scripts.add("start();");*/

        scripts.add(readJS("demos/client/penderdemo.js"));
        scripts.add("init();");
        
        ((PenderView)mGLView).execScripts(scripts);
    }

    public PenderView getView() { return (PenderView)this.mGLView; }

    private String readJS( String jspath) {		
    	BufferedReader reader = null;
    	StringBuffer script = new StringBuffer();
    	try {
	  	    AssetManager al = this.getAssets(); //sharpshooting hulk

	  	    reader = new BufferedReader( 
	  	    					new InputStreamReader( 
	  	    						al.open(jspath)));
	  	    String buf = "";
	  	    while(( buf = reader.readLine()) != null) {
	  	    	script.append("\n");
	  	    	script.append(buf);
	  	    }
  	    } catch (IOException e ) {
  	    	Log.d("exception", e.toString() );
  	    	Log.d("handle this gracefully","wouldn't you?");
  	    }
    	return script.toString();
    }

    private void initView() {
        final Activity ctx = this;
    	this.runOnUiThread( new Runnable() {
    	                        public void run() { 
    	                              RelativeLayout top = new RelativeLayout( ctx );
    	                              top.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 
    	                                                                                  RelativeLayout.LayoutParams.FILL_PARENT));
    	                              top.addView( mGLView );
    	                              ctx.setContentView( top );
    	                          } //end run
    	}); // end runnable
    } //initView

    private GLSurfaceView mGLView;
    private PenderMessageHandler mHandler;
}