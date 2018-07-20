package com.smarterama.aleksce267.longdivider;

/**
 * Created by Aleks on 09.10.2016.
 */
public class SubtractionParameters {
    public final long minuend;
    public final long subtrahend;

    public SubtractionParameters(long minuend, long subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    public String toString(){
        return "minuend=" + String.valueOf(minuend)+" subtrahend=" + String.valueOf(subtrahend);
    }
}
