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


package com.pender.glaid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;





public class Image {
	
    public Image() {
    	initBuffers();
         mGLId = 0;
    }
    //==========================================================================
    public Image( int glid, Polygon poly ) {
    	initBuffers();
    	mGLId = glid;
    	mPoly = poly;
    }
    //==========================================================================
    public void initBuffers() {
    	ByteBuffer byteBuffer = ByteBuffer.allocateDirect( mTexMapCoords.length * 4);
    	byteBuffer.order(ByteOrder.nativeOrder());
    	mTexMapCoordBuffer = byteBuffer.asFloatBuffer();
    	mTexMapCoordBuffer.put(mTexMapCoords);
    	mTexMapCoordBuffer.position(0);
    }
    //==========================================================================
    private float mTexMapCoords[] = {
    								0.0f, 0.0f,
    								0.0f, 1.0f,
    								1.0f, 1.0f,
    								1.0f, 0.0f
    						   };
    //==========================================================================
    public int getGLId() { return mGLId; }
    public void setGLId( int glid ) { mGLId = glid; }
    public Polygon getPoly() { return mPoly; }
    public void setPoly( Polygon poly ) { mPoly = poly; }
    public FloatBuffer getTextureBuffer() { return mTexMapCoordBuffer; }
    //==========================================================================
    private Polygon mPoly;
    private int mGLId;
    private FloatBuffer mTexMapCoordBuffer;
    //==========================================================================
    //==========================================================================
}