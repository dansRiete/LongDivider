package com.smarterama.aleksce267.longdivider;

import java.util.ArrayList;
import java.util.List;


public class Division {

    public static final int FLOATING_POINT_ACCURACY = 20;
    private final DecimalNumber divident;
    private final int divisor;
    private int decimalPlace = 0;
    private long remainder = -1;
    private boolean decimalPointAdded = false;
    private boolean lastAppendAdded = false;
    private String wholePart = "";
    private String mathDividingResult = "";
    private LongDivisionBuilder divisionBuilder;
    private DecimalFractionBuilder fraction;
    private ArrayList<SubtractionParameters> wholePartSubtractions = new ArrayList<>();


    Division(int divident, int divisor) {

        if (divisor <= 0) {
            throw new IllegalArgumentException("Divisor cannot be zero, or less than zero");
        } else {
            this.divisor = divisor;
        }

        this.divident = new DecimalNumber(divident);
        divide();
    }

    public String getLongDivisionsView() {
        return divisionBuilder.getLongDivisionsView();
    }

    public double getMathDividingResult(){
        return Double.valueOf(mathDividingResult);
    }

    public int getDecimalAccuracy(){
        return mathDividingResult.length() - mathDividingResult.indexOf(".") - 1;
    }

    private boolean isLastAppend(){
        return !divident.hasNextDigit() && (decimalPlace >= FLOATING_POINT_ACCURACY || remainder == 0);
    }

    private void divide() {

        while (!isLastAppend()) {

            if(!divident.hasNextDigit())
                decimalPlace++;
            long minuend = 0;
            long subtrahend = 0;

            if (remainder >= 0) {
                if(divident.nextDigit()){
                    minuend = DecimalNumber.appendDigit(remainder, divident.getCurrentDigit());
                }else {
                    decimalPointAdded = true;
                    minuend = DecimalNumber.appendDigit(remainder, 0);
                }
            } else {
                divident.nextDigit();
                minuend = divident.getCurrentDigit();
            }

            while (minuend < divisor) {

                if(decimalPointAdded){
                    decimalPlace++;
                    appendSubtraction(new SubtractionParameters(minuend, subtrahend), isLastAppend());
                }else {
                    wholePart += 0;
                    mathDividingResult += 0;
                }
                int secondDigit;
                if (divident.nextDigit()) {
                    secondDigit = divident.getCurrentDigit();
                } else {
                    decimalPointAdded = true;
                    secondDigit = 0;
                }
                if(minuend == 0 && secondDigit == 0){
                    break;
                }
                minuend = DecimalNumber.appendDigit(minuend, secondDigit);
            }

            subtrahend = minuend / divisor * divisor;
            remainder = minuend - subtrahend;

            if(!decimalPointAdded){
                wholePart += minuend / divisor;
            }

            appendSubtraction(new SubtractionParameters(minuend, subtrahend), isLastAppend());
        }
    }

    private void appendSubtraction(SubtractionParameters subtractionParameters, boolean lastAppend){

        if(fraction == null){
            fraction = new DecimalFractionBuilder(divisor);
        }

        if(!decimalPointAdded){
            wholePartSubtractions.add(subtractionParameters);
            mathDividingResult += subtractionParameters.minuend / divisor;
        }else {
            fraction.appendDecimalPlace(subtractionParameters);
        }

        if(lastAppend && !lastAppendAdded){
            lastAppendAdded = true;
            List<SubtractionParameters> fractionPartSubtractions = fraction.getListOfSubtractions();
            String dividingResult = Integer.valueOf(wholePart.equals("") ? "0" : wholePart) + (fraction.getDecimalView().equals("") ? "" : "." + fraction.getDecimalView());
            divisionBuilder = new LongDivisionBuilder(divident.getNumber(), divisor, dividingResult);

            for (int i = 0; i < wholePartSubtractions.size(); i++){
                divisionBuilder.appendSubtraction(wholePartSubtractions.get(i), i == wholePartSubtractions.size() - 1 && fractionPartSubtractions.size() == 0);
            }

            for (int i = 0; i < fractionPartSubtractions.size(); i++){
                SubtractionParameters currentSubtraction = fractionPartSubtractions.get(i);

                boolean lastElement = (i == fractionPartSubtractions.size() - 1);
                if(lastElement && currentSubtraction.minuend < divisor && divident.getNumber() != 0){
                    divisionBuilder.appendDivisionsRemainder();
                }else {
                    divisionBuilder.appendSubtraction(currentSubtraction, lastElement);
                }
            }

            mathDividingResult += "." + fraction.getMathResult();
        }
    }
}

