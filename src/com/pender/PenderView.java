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

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;


class PenderView extends GLSurfaceView {

     public PenderView( Context ctx ) {

         super( ctx );

         mRenderer = new PenderRenderer();

         this.setRenderer( mRenderer );

     }


     public void execScript( String script ) {
    
         ((PenderRenderer) mRenderer).execScript(script);

     }
     
     public void loadTexture( final Bitmap bmp ) {

         this.queueEvent( new Runnable() {
             public void run() { 	

             		((PenderRenderer) mRenderer).addToPreload( bmp );
             	
             }
         });
    	 
     }

    GLSurfaceView.Renderer mRenderer;

}
 