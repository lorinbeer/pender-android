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

import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES10;

import com.pender.glaid.Image;
import com.pender.glaid.Polygon;


public class PenderCanvas {
	
	public PenderCanvas( PenderRenderer renderer ) {
		float vert[] = { 
	  		      100.0f, 300f, 0.0f,
	  		      100.0f, 428.0f, 0.0f,
	  		      228.0f, 428.0f, 0.0f,
	  		      228.0f, 300.0f, 0.0f,
	  	};

	  	short[] ind = { 0, 1, 2, 0, 2, 3 };

		mPoly = new Polygon( vert, ind );
		mRenderer = renderer;
	}

    public void drawImage( Image img, float dx, float dy) {
    	this.glDrawImage(img);
   } 

    /**
     * 
     * @param image integer reference to the image
     * @param dx x coordinate of top right corner 
     * @param dy y coordinate of top right corner
     * @param dw image width
     * @param dh image height
     */
    public void drawImage( Image img, float dx, float dy, float dw, float dh) {
    	this.glDrawImage(img);
    }

    public void drawImage( Image  img, float sx, float sy, float sw, float sh, 
                                       float dx, float dy, float dw, float dh) {
    	this.glDrawImage(img);
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
    //==========================================================================
    Polygon mPoly;
    PenderRenderer mRenderer;
    //==========================================================================
}