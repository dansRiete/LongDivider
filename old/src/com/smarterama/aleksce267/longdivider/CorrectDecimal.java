package com.smarterama.aleksce267.longdivider;

/**
 * Created by Aleks on 13.10.2016.
 */
public class CorrectDecimal {
    private final int divident;
    private final int divisor;
    private final int accuracy;
    private double decimal;

    CorrectDecimal(int divident, int divisor, int accuracy){
        this.divident = divident;
        this.divisor = divisor;
        this.accuracy = accuracy;
        divide();
    }

    public double getCorrectDecimal(){
        return decimal;
    }

    private void divide(){
        String result = "";
        long remainder = 0;
        int decimalPlace = 0;
        result += divident / divisor + ".";
        remainder = divident % divisor;
        while (remainder != 0 && decimalPlace < accuracy){
            remainder *= DecimalNumber.SCALE;
            result += (remainder / divisor);
            remainder = remainder % divisor;
            decimalPlace++;
        }
        decimal = Double.valueOf(result);
    }

}
