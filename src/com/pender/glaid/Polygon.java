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
import java.nio.ShortBuffer;

/**
 *
 *
 */
public class Polygon {

    /**
     *
     * @param vertices the vertex data in a flat float array
     * @param indices vertex ordering data, size depends on draw mode 
     */
    public Polygon( float[] vertices, short[] indices ) {

        //declare and initilize the internal copy of the vertex and index arrays
        mVertices = new float[vertices.length];
        mVertices = vertices.clone();
        mIndices = new short[indices.length];
        mIndices = indices.clone();

	        //boilerplate setup of the vertex buffer
		ByteBuffer vbuf = ByteBuffer.allocateDirect( vertices.length * 4 );
		vbuf.order( ByteOrder.nativeOrder() );
	        mVertexBuffer = vbuf.asFloatBuffer();
		mVertexBuffer.put( mVertices );
		mVertexBuffer.position( 0 );
	        
		//boilerplate setup of the index buffer
		ByteBuffer ibuf = ByteBuffer.allocateDirect( indices.length * 2 );
		ibuf.order( ByteOrder.nativeOrder() );
		mIndexBuffer = ibuf.asShortBuffer();
	    mIndexBuffer.put( mIndices );
		mIndexBuffer.position(0);
    }
   
    public FloatBuffer getVertexBuffer() { return mVertexBuffer; }
    public ShortBuffer getIndexBuffer() { return mIndexBuffer; }
    public short[] getIndices() { return mIndices; }
    
    //==========================================================================
    
    private float[] mVertices; //vertex data defining the convex (your job) polygon
    
    private short[] mIndices; //defines the order of vertices that we want our polygon connected 

    private FloatBuffer mVertexBuffer; //what we feed opengl

    private ShortBuffer mIndexBuffer; //what we feed opengl

}
