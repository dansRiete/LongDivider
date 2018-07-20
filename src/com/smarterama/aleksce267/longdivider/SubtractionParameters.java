package com.smarterama.aleksce267.longdivider;

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
