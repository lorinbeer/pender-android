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
import java.util.ArrayList;


import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        mGLView = new PenderView( mHandler );

        initView();
        
        ArrayList<String> scripts = new ArrayList<String>();

        scripts.add(readJS("penderandroidshim.js") );
        scripts.add(readJS("penderdemo.js") );
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
	  	    						al.open(jspath) ) ); //TODO: hardcoded magicks, needs to be spec'ed by argument
	  	    String buf = "";
	  	    while( ( buf = reader.readLine() ) != null ) {
	  	    	
	  	    	script.append("\n");
	  	    	script.append( buf );
	  	    	
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
    	                              top.setLayoutParams( new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.FILL_PARENT, 
    	                                                                                    RelativeLayout.LayoutParams.FILL_PARENT ) );
    	                 
    	                              top.addView( mGLView );
    	      
    	                              ctx.setContentView( top );
    	      
    	                          } //end run
    	}); // end runnable
    } //initView

    
    private void loadImage() {
    	
        InputStream instream = null;
        
        //cripcrap crippity path generation
        String basepath = "tex/pattysdaynyan/00";
        String path = "";
        for( int i = 0; i < 12; i++ ) {
        	
        	path = basepath;
        	
        	if( i < 10 ) {
        		path += "0";
        	}
        	
        	path += i + ".png";
        	
	        try {
				instream = this.getAssets().open( path );
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	
	        final Bitmap temp = BitmapFactory.decodeStream(instream);
	        
	        ((PenderView) mGLView).loadTexture( temp, 0 );
        }
    }

    private GLSurfaceView mGLView;

    private PenderMessageHandler mHandler;
    
}