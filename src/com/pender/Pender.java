/* Copyright 2012 Adobe Systems Incorporated
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


/*
 *
 *
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



public class Pender {

    /*
     *
     *
     */
    public Pender() {
        mGLView = new PenderView(mHandler);
        initView();

        ArrayList<String> scripts = new ArrayList<String>();
        scripts.add(readFileAsString("penderandroidshim.js"));
        scripts.add(readFileAsString("demos/client/penderdemo.js"));
        scripts.add("init();");

        ((PenderView)mGLView).execScripts(scripts);

    }

    // 
    public void initView() {
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

    // pender's gl surface
    private GLSurfaceView mGLView;
    // handles interthread communication for Pender
    private PenderHub mHub;
}
