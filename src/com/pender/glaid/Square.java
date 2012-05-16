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

/**
 * lightweight square class for gl rendering/texture mapping
 * 
 * @author lorinbeer
 * 
 */
public class Square {
		
    public Square() {	
			ByteBuffer bb = ByteBuffer.allocateDirect( mVertices.length * 4 );
			bb.order( ByteOrder.nativeOrder() );
			mVertexBuffer = bb.asFloatBuffer( );
			mVertexBuffer.put( mVertices );
			mVertexBuffer.position( 0 );
		}
    
    public FloatBuffer getVertexBuffer() { return this.mVertexBuffer; }
    
    public FloatBuffer mVertexBuffer;
    public float mVertices[] = { -1.0f,  1.0f, 0.0f, 
    						     -1.0f, -1.0f, 0.0f, 	
    						      1.0f, -1.0f, 0.0f,
    							  1.0f,  1.0f, 0.0f };

}