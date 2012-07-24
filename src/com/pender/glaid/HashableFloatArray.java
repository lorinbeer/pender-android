package com.pender.glaid;

import java.util.Arrays;



public final class HashableFloatArray {
    public HashableFloatArray(float[] data)
    {
        if (data == null) {
            throw new NullPointerException();
        }
        this.d = data;
    }
    //==========================================================================
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof HashableFloatArray)) {
            return false;
        }
        return Arrays.equals(d, ((HashableFloatArray)other).getData());
    }
    //==========================================================================
    @Override
    public int hashCode() {
        return Arrays.hashCode(d);
    }
    //==========================================================================
    /**
     * 
     * @return
     */
    public float[] getData() { 
    	return d; 
    }

    //==========================================================================
    // private members
    //==========================================================================

    private final float[] d;
    
    //==========================================================================
    //==========================================================================
}