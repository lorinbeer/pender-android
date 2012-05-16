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

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import com.pender.PenderMessageHandler;

public class PenderView extends GLSurfaceView {

     public PenderView( PenderMessageHandler handler ) {
         super( handler.getActivity() );
         mRenderer = new PenderRenderer(handler);
         this.setRenderer( mRenderer );
     }

     public void execScripts( ArrayList<String> scripts ) {
    	 final ArrayList<String> fscripts = scripts;
    	 this.queueEvent( new Runnable() {
    		 public void run() {
    			 for( int i = 0; i < fscripts.size(); i++) {
    				 ((PenderRenderer)mRenderer).execScript(fscripts.get(i));
    			 }
    		 }
    	 });
     }
     
     public void loadTexture( final Bitmap bmp, final int id ) {
         this.queueEvent( new Runnable() {
             public void run() {
            	 ((PenderRenderer) mRenderer).requestLoad(id,bmp);
             }
         });
     }

    GLSurfaceView.Renderer mRenderer;

} 