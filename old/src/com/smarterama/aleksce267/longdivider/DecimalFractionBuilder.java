package com.smarterama.aleksce267.longdivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleks on 11.10.2016.
 */
public class DecimalFractionBuilder {
    private final int divisor;
    private int periodLength = 0;
    private int periodStartIndex = -1;
    private String mathResult;
    private ArrayList<Integer> givenRemaindersSequence = new ArrayList<>();
    private ArrayList<SubtractionParameters> appropriateSubtractionList = new ArrayList<>();
    private int appendsNumber = 0;

    DecimalFractionBuilder(int divisor){
        this.divisor = divisor;
    }

    public String getMathResult(){
        return mathResult;
    }

    public String getDecimalView(){
        mathResult = "";
        String decimalView = "";
        int currentIndex = 0;
        for(SubtractionParameters currentParameter : getListOfSubtractions()){

            if(periodLength > 0 && currentIndex == periodStartIndex){
                decimalView = decimalView.concat("(");
            }

            String currentDigit = String.valueOf(currentParameter.minuend/divisor);
            decimalView = decimalView.concat(currentDigit);
            mathResult = mathResult.concat(currentDigit);
            currentIndex++;

            if(periodLength > 0 && currentIndex == periodStartIndex + periodLength){
                decimalView = decimalView.concat(")");
            }
        }
        return decimalView;
    }


    public List<SubtractionParameters> getListOfSubtractions(){
        findPeriod();
        if(appendsNumber == 0){
            return new ArrayList<>();
        }
        if(periodLength == 0){
            return appropriateSubtractionList.subList(0, appendsNumber);
        }else {
            return appropriateSubtractionList.subList(0, periodStartIndex + periodLength);
        }
    }

    public void appendDecimalPlace(SubtractionParameters parameters){
        appendsNumber++;
        givenRemaindersSequence.add((int)parameters.minuend);
        appropriateSubtractionList.add(parameters);
    }

    private void findPeriod(){
        ArrayList<Integer> currRemainderSequnce = new ArrayList<>();
        int currentAddedElementsIndex = 0;
        int calculatedPeriodLength = 0;
        boolean periodConfirmed = false;
        for(int currentRemainder : givenRemaindersSequence){
            if(currRemainderSequnce.contains(currentRemainder)){
                if(periodStartIndex == -1){
                    periodStartIndex = currRemainderSequnce.lastIndexOf(currentRemainder);
                }
                calculatedPeriodLength = currentAddedElementsIndex - currRemainderSequnce.lastIndexOf(currentRemainder);
                if(periodLength != calculatedPeriodLength){
                    periodLength = calculatedPeriodLength;
                }else if(!periodConfirmed){
                    periodConfirmed = true;
                }
            }
            currRemainderSequnce.add(currentRemainder);
            currentAddedElementsIndex++;
        }
    }
}
