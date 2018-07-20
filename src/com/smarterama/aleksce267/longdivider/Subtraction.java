package com.smarterama.aleksce267.longdivider;

public class Subtraction {
    private final long minuend;
    private final long subtrahend;
    private StringBuilder subtractionView = new StringBuilder();
    private int shift;

    Subtraction(long minuend, long subtrahend, int shift){
        if(subtrahend > minuend){
            throw new IllegalArgumentException("Subtrahend was greater than minuend");
        }
        this.minuend = minuend;
        this.subtrahend = subtrahend;
        this.shift = shift;
        buildView();
    }

    public String getView(){
        return subtractionView.toString();
    }

    public String getMinuendLine(){
        return LongDivisionBuilder.getSymbolsLine(shift + 1, ' ') + minuend;
    }

    public String getSubtractionSignLine(){
        return LongDivisionBuilder.getSymbolsLine(shift, ' ') + '-';
    }

    public String getSubtrahendLine(){
        return LongDivisionBuilder.getSymbolsLine(shift + 1 + getSubtrahendsShift(), ' ') + subtrahend;
    }

    public String getSummaryLine(){
        int maxDigitsAmount = Math.max(DecimalNumber.calculateDigitsAmount(minuend), DecimalNumber.calculateDigitsAmount(subtrahend));
        return LongDivisionBuilder.getSymbolsLine(shift + 1, ' ') + LongDivisionBuilder.getSymbolsLine(maxDigitsAmount, '-');
    }

    public String toString(){
        return getView();
    }

    private void buildView(){

        subtractionView
                .append(getMinuendLine()).append('\n')
                .append(getSubtractionSignLine()).append('\n')
                .append(getSubtrahendLine()).append('\n')
                .append(getSummaryLine());
    }

    private int getSubtrahendsShift(){
        return DecimalNumber.calculateDigitsAmount(minuend) - DecimalNumber.calculateDigitsAmount(subtrahend);

    }

}

