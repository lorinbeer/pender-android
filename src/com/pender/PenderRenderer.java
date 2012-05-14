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
import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import android.graphics.Bitmap;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

import com.pender.glaid.Image;
import com.pender.glaid.Polygon;


public class PenderRenderer implements GLSurfaceView.Renderer {

    public PenderRenderer() {

        super();

        preloadlist = new ArrayList<Bitmap>();
        
        textureList = new ArrayList<Image>();
        
        mLastFrame = 0;
        mfps = 0;
        mSetfps = 60;
        
        mCanvas = new PenderCanvas(this);
        
        initJSEngine();
 
    }

    @Override
    public void onDrawFrame( GL10 gl ) {
    	long nowframe = new Date().getTime();
  
    	if( (nowframe - mLastFrame) > 1.0/mSetfps ) {

    		long thisfps = Math.round( 1.0 / (nowframe - mLastFrame)*1000f ); //calculate time 
    	    mLastFrame = nowframe;
    	    	mfps = Math.round( mfps*0.9 + 0.1*thisfps ); //weighted averaging

    	    	Log.d("fps",String.valueOf(mfps));
    		
    		GLES10.glClear( GLES10.GL_COLOR_BUFFER_BIT | 
    						GLES10.GL_DEPTH_BUFFER_BIT );
    	
    		GLES10.glLoadIdentity();  
    	  
    	//    GLES10.glTranslatef( 0.0f, 0.0f, -10.0f ); 
        
        	execScript( "draw();" );  
    	/*    
    		float vert[] = { 
   	  		      10.0f, 100.0f, 0.0f,
   	  		      10.0f, 40.0f, 0.0f,
   	  		      40.0f, 40.0f, 0.0f,
   	  		      40.0f, 10.0f, 0.0f,  
   	  		};

    		short[] ind = { 0, 1, 2, 0, 2, 3 };

    	    Polygon poly = new Polygon( vert, ind );
    	    
    	   this.drawPolygon( poly );
    	    
    	    this.drawTexturedPolygon(poly, textureList.get(0) );
    	  */  
    	}

    }

    @Override
    public void onSurfaceCreated( GL10 gl, EGLConfig config ) {
    	
    	GLES10.glClearColor( 1.0f, 0.0f, 0.0f, 0.5f );
    	
    	GLES10.glShadeModel( GLES10.GL_SMOOTH );
    	
    	GLES10.glClearDepthf( 1.0f );
    	
    	GLES10.glEnable( GLES10.GL_DEPTH_TEST );
    	
		GLES10.glEnable(GLES10.GL_TEXTURE_2D); 
		
		GLES10.glEnable( GL10.GL_BLEND );
    	
    	GLES10.glBlendFunc( GLES10.GL_SRC_ALPHA, GLES10.GL_ONE_MINUS_SRC_ALPHA );	
	
    	GLES10.glDepthFunc( GLES10.GL_LEQUAL );
    	
    	GLES10.glHint( GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES10.GL_NICEST );

    	while( ! preloadlist.isEmpty() ) {
    		loadTexture( preloadlist.remove( 0 ) );
    	}

        this.setupJS();
        
    }

    @Override
    public void onSurfaceChanged( GL10 gl, int width, int height ) {

    	// Sets the current view port to the new size.
    	GLES10.glViewport(0, 0, width, height);
		// Select the projection matrix
		GLES10.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		GLES10.glLoadIdentity();
		
		// Calculate the aspect ratio of the window
		
	/*	GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);*/
		
		GLU.gluOrtho2D(gl, 0.0f, (float) width, (float) height, (float)0.0);
			
		// Select the modelview matrix
		GLES10.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		GLES10.glLoadIdentity();

    }

    //==========================================================================
    //==========================================================================
    /**
     * 
     */
    public void setupJS() {

    	mCanvas.setImageList( textureList );
    	
    	mJSContext = Context.enter();
    	mJSContext.setOptimizationLevel(-1);

        Object jscanvas = Context.javaToJS(mCanvas,mJSScope);
	    ScriptableObject.putProperty(mJSScope, "Canvas", jscanvas);
	    
	    Object setting_fps = Context.javaToJS( mSetfps, mJSScope );
	    ScriptableObject.putProperty( mJSScope, "setting_fps", setting_fps );
	    
	    Context.exit();

    }
    //==========================================================================
    //==========================================================================
<<<<<<< HEAD

/*
=======
    
    /**
     * draw a mesh
     * this can be removed and placed in PenderCanvas
     * currently there is an incesteous closed loop
     */
>>>>>>> 04636d3cb81a245705948df3a2012a7af567a6a8
    public void drawPolygon( Polygon convexpoly ) {
    	
        GLES10.glFrontFace( GLES10.GL_CW );

        GLES10.glEnableClientState( GLES10.GL_VERTEX_ARRAY );
        
        GLES10.glVertexPointer( 3, GLES10.GL_FLOAT, 0, convexpoly.getVertexBuffer() );

        GLES10.glDrawElements( GLES10.GL_TRIANGLE_STRIP,
        		               convexpoly.getIndices().length,  
        					   GLES10.GL_UNSIGNED_SHORT, 
        					   convexpoly.getIndexBuffer() );

        GLES10.glDisableClientState( GLES10.GL_VERTEX_ARRAY );
    
    }
<<<<<<< HEAD
*/
/*
    public void drawImage( Image img ) {

    	GLES10.glBindTexture(GL10.GL_TEXTURE_2D, img.getGLId() );
    	GLES10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    	
    	GLES10.glTexCoordPointer(2, GL10.GL_FLOAT, 0, img.getTextureBuffer() );
    	
    	drawPolygon( img.getPoly() );
=======

    
    public void drawTexturedPolygon( Polygon convexpoly, Image texture ) {

    	GLES10.glBindTexture(GL10.GL_TEXTURE_2D, texture.getGLId() );
    	GLES10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    	
    	GLES10.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture.getTextureBuffer() );
    	
    	drawPolygon( convexpoly );
>>>>>>> 04636d3cb81a245705948df3a2012a7af567a6a8
        
    	GLES10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    	
    }
<<<<<<< HEAD
*/
=======
>>>>>>> 04636d3cb81a245705948df3a2012a7af567a6a8

    public void loadTexture( Bitmap bmp ) {
    	
       int[] texid = new int[1];
 
       GLES10.glGenTextures(1, texid, 0); //generate a single new texture id
       GLES10.glBindTexture(GL10.GL_TEXTURE_2D, texid[0] );

       //set texture parameters
       GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_NEAREST ); 
       GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR  ); 

       GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, 0, bmp, 0 );
     
      	mJSContext = Context.enter();
    	mJSContext.setOptimizationLevel(-1); 

     //  try {
      // 	String script = "Textures[\""+tex.name+"\"] = "+texid[0]+";";
    	String script = "var image = "+texid[0]+";";
       	mJSContext.evaluateString(mJSScope, script, "Insert Texture", 0, null);

  //     } 
   //    catch (Exception e) {

   	      //  Log.d("Renderer.onSurfaceCreated",e.toString());

  //     }		
       	float vert[] = { 
	      100.0f, 300f, 0.0f,
	      100.0f, 428.0f, 0.0f,
	      228.0f, 428.0f, 0.0f,
	      228.0f, 300.0f, 0.0f,
};

short[] ind = { 0, 1, 2, 0, 2, 3 };

       	Polygon poly = new Polygon( vert, ind );
       	Image im = new Image( texid[0], poly );

       	textureList.add( im );

    }
    
    
    private void initJSEngine() {
<<<<<<< HEAD
    	  
=======
    	
>>>>>>> 04636d3cb81a245705948df3a2012a7af567a6a8
        //Rhino Setup
        mJSContext = Context.enter();
  	    mJSScope = mJSContext.initStandardObjects();
  	    mJSContext.setOptimizationLevel(-1);
<<<<<<< HEAD
  	    
  	    
  	    
=======
>>>>>>> 04636d3cb81a245705948df3a2012a7af567a6a8
        this.setupJS();
  	    Context.exit(); 

    } 
    
    //==========================================================================
    //==========================================================================
    
    public void execScript( String script ) {
    	
        mJSContext = Context.enter();
        mJSContext.setOptimizationLevel(-1);
        //if there is a syntax error in the script, the world explodes
        Object result = mJSContext.evaluateString( mJSScope, 
        										   script, 
        										   "load script", 
        										   0, 
        										   null);
        Context.exit();
    }
    
    public void addToPreload( Bitmap bmp ) { preloadlist.add( bmp ); }
    
    private long mLastFrame;
    
    private long mfps;
    
    private int mSetfps;
    
    private ArrayList<Bitmap> preloadlist;
    
    private ArrayList<Image> textureList;
    
    private Context mJSContext;
    private Scriptable mJSScope;
    
    private PenderCanvas mCanvas;

}