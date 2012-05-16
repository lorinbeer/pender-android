

package com.pender;

import java.util.UUID;

import android.os.Message;

import com.pender.PenderMessageHandler;


public class PenderJS {
	
	public PenderJS(PenderMessageHandler handler) {
		mHandler = handler;
		
	}

	public void loadImage( String path  ) {
		Message msg = new Message();
		final String fpath = path;
		int id = UUID.randomUUID().hashCode();
		msg.obj = fpath;
		msg.arg1 = 0;
		msg.arg2 = id;
		mHandler.dispatchMessage(msg);	
	}
	
	private PenderMessageHandler mHandler;
	
}