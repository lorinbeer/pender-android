
package com.pender;

import android.text.format.Time;

import org.mozilla.javascript.Function;

/**
 * 
 */
class FuncDelayPair {
	//==========================================================================
	//==========================================================================
	/**
	 * 
	 * @param f
	 * @param d
	 * @param interval
	 */
	public FuncDelayPair(Function f, float d, boolean interval) {
		func = f;
		delay = d;
		lastdraw = 0;
	}
	//==========================================================================
	//==========================================================================
	/**
	 * 
	 * @return
	 */
	 public boolean ready() {
		 Time t = new Time();
		 return this.ready(t.toMillis(false));
	 }
	//==========================================================================
	//==========================================================================
	/**
	 * returns true if delay has elapsed since last draw based on time
	 * 
	 * @return true if delay has elapsed, false otherwise
	 */
	public boolean ready(long tms) {
		if ((tms - this.lastdraw) >= this.delay ) {
			return true;
		}
		return false;
	}
	//==========================================================================
	//==========================================================================
	public Function func;
	public float delay;
	public boolean interval;
	public long lastdraw;
	//==========================================================================
	//==========================================================================
}