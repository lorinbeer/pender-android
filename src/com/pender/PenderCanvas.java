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

import java.nio.FloatBuffer;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES10;

import com.pender.glaid.HashableFloatArray;
import com.pender.glaid.Image;
import com.pender.glaid.Polygon;
import com.pender.renderer.PenderRenderer;


public class PenderCanvas {
	//========================================================================== 
	public PenderCanvas( PenderRenderer renderer ) {
		mRenderer = renderer;
		mPolys = new HashMap<HashableFloatArray,Polygon>();
		mTexCoords = new HashMap<HashableFloatArray,FloatBuffer>();
	}
	//==========================================================================
	//==========================================================================
	/**
	 * 
	 * @param img
	 * @param dx
	 * @param dy
	 */
    public void drawImage( Image img, float dx, float dy) {
    	GLES10.glMatrixMode (GLES10.GL_MODELVIEW);
    	GLES10.glPushMatrix ();
    	GLES10.glTranslatef (dx, dy, 0.0f);
    	this.glDrawImage (img);
    	GLES10.glPopMatrix();
   } 
   //========================================================================== 
    /**
     * 
     * @param image integer reference to the image
     * @param dx x coordinate of top right corner 
     * @param dy y coordinate of top right corner
     * @param dw image width
     * @param dh image height
     */
    public void drawImage( Image img, float dx, float dy, float dw, float dh) {
		float vert[] = { 
	  		      dw, 0.0f, 0.0f,
	  		      dw,   dh, 0.0f,
	  		      0.0f, dh, 0.0f,
	  		      0.0f, 0.0f, 0.0f,
	  	};
	  	short[] ind = { 0, 1, 2, 0, 2, 3 };
	  	
	  	Polygon poly = new Polygon (vert,ind);
	  	img.setPoly(poly);
	  	
    	GLES10.glMatrixMode (GLES10.GL_MODELVIEW);
    	GLES10.glPushMatrix ();
    	GLES10.glTranslatef (dx, dy, 0.0f);
    	this.glDrawImage (img);
    	GLES10.glPopMatrix ();
    }
    //========================================================================== 
    /**
     * 
     * @param image integer reference to the image
     * @param sx
     * @param sy
     * @param sw
     * @param sh
     * @param dx x coordinate of top right corner 
     * @param dy y coordinate of top right corner
     * @param dw image width
     * @param dh image height
     */
    public void drawImage (Image img, float sx, float sy, float sw, float sh, 
                                      float dx, float dy, float dw, float dh) {
    	HashableFloatArray k = new HashableFloatArray(new float[]{dw,dh});
    	HashableFloatArray t = new HashableFloatArray(new float[]{sx,sy,sw,sh});
    	Polygon poly = null;
    	FloatBuffer texbuf = null;
    	if (img==null) {
    		throw new NullPointerException("Image cannot be null");
    	}
    	
    	//
    	if (mPolys.containsKey(k)) {
    		poly = mPolys.get(k);
    	} else {
    		poly = this.createPoly(dw, dh);
    		mPolys.put(k, poly);
    	}
    	
    	//
    	if(mTexCoords.containsKey(t)) {
    		texbuf = mTexCoords.get(t);
    	} else {
    		texbuf = img.calcTexCoords(sx, sy, sw, sh);
    		mTexCoords.put(t, texbuf);
    	}
	  	
	  	img.setPoly(poly);
	  	img.setTexCoords(texbuf);
    	
    	GLES10.glMatrixMode (GLES10.GL_MODELVIEW);
    	GLES10.glPushMatrix ();
    	GLES10.glTranslatef (dx, dy, 0.0f);
    	this.glDrawImage(img);
    	GLES10.glPopMatrix ();
    }
    //==========================================================================
    //==========================================================================
    public void scale( float x, float y ) {
    	//awesome
    	GLES10.glScalef(x, y, 0.0f);
    }
    //==========================================================================
    public void rotate( float angle ) {
    	//this is problematic, and will require some creativity
    	//for now the center of rotation will always be about the origin
    	GLES10.glRotatef( angle , 0.0f, 0.0f, 0.0f);
    }
    //==========================================================================
    public void translate( float x, float y ) {
        //awesome
        GLES10.glTranslatef( x, y, 0.0f );
    } 
    //========================================================================== 
    /**
     * draw a mesh
     * this can be removed and placed in PenderCanvas
     * currently there is an incesteous closed loop
     */
    public void drawPolygon( Polygon convexpoly ) {
        GLES10.glFrontFace (GLES10.GL_CW);
        GLES10.glEnableClientState (GLES10.GL_VERTEX_ARRAY);
        GLES10.glVertexPointer (3, GLES10.GL_FLOAT, 0, 
        						convexpoly.getVertexBuffer() );
        GLES10.glDrawElements (GLES10.GL_TRIANGLE_STRIP,
        		               convexpoly.getIndices().length,  
        					   GLES10.GL_UNSIGNED_SHORT, 
        					   convexpoly.getIndexBuffer());
        GLES10.glDisableClientState (GLES10.GL_VERTEX_ARRAY);
    }
    //==========================================================================
    /**
     * 
     * @param image
     */
    public void glDrawImage( Image image ) {  
     	GLES10.glBindTexture (GL10.GL_TEXTURE_2D, image.getGLId() );
    	GLES10.glEnableClientState (GL10.GL_TEXTURE_COORD_ARRAY);
    	GLES10.glTexCoordPointer (2,
    							  GL10.GL_FLOAT,
    							  0,
    							  image.getTextureBuffer() );
    	drawPolygon( image.getPoly() );
    	GLES10.glDisableClientState (GL10.GL_TEXTURE_COORD_ARRAY);
    }
    //==========================================================================
    public void clearRect(int dx,int dy,int dwidth,int dheight) {
    	GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT);
    }
    //==========================================================================
    /**
     * creates a polygon of the specified dimensions
     */
    private Polygon createPoly(float width, float height) {
    	short[] ind = { 0, 1, 2, 0, 2, 3 }; //we always use this wrapping
    	float vert[] = createVertArray(width,height);
    	return new Polygon(vert,ind);
	  	
    }
    //==========================================================================
    /**
     * creates a vertex array representing a rectangle of specified dimensions
     */
    private float [] createVertArray(float width, float height) {
		float vert[] = { 
	  		      width, 0.0f, 0.0f,
	  		      width, height, 0.0f,
	  		      0.0f,  height, 0.0f,
	  		      0.0f, 0.0f, 0.0f,
	  	};
		return vert;
    }
    //==========================================================================
    //==========================================================================
    private PenderRenderer mRenderer;
    private HashMap<HashableFloatArray,Polygon> mPolys;
    private HashMap<HashableFloatArray,FloatBuffer> mTexCoords;
    //==========================================================================
    //==========================================================================
}