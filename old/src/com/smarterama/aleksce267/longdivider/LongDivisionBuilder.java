package com.smarterama.aleksce267.longdivider;

public class LongDivisionBuilder {

    private long divident;
    private long divisor;
    private long minuend;
    private long subtrahend;
    private long remainder;
    private int divisorsDigitsAmount;
    private int dividentsDigitsAmount;
    private int dividingResultsDigitsAmount;
    private boolean firstAppend = true;
    private String dividingResult;
    private String paragraph = "";
    private StringBuilder longDivisionsView = new StringBuilder();


    LongDivisionBuilder(int divident, int divisor, String dividingResult) {

        this.divident = divident;
        this.divisor = divisor;
        this.dividentsDigitsAmount = DecimalNumber.calculateDigitsAmount(divident);
        this.divisorsDigitsAmount = DecimalNumber.calculateDigitsAmount(divisor);
        this.dividingResultsDigitsAmount = dividingResult.length();
        this.dividingResult = dividingResult;

    }

    public static String getSymbolsLine(int number, char c) {
        StringBuilder symbolsLine = new StringBuilder("");
        for (; number > 0; number--) {
            symbolsLine.append(c);
        }
        return symbolsLine.toString();
    }

    public void appendSubtraction(SubtractionParameters parameters, boolean lastAppend) {

        this.minuend = parameters.minuend;
        this.subtrahend = parameters.subtrahend;
        this.remainder = minuend - subtrahend;
        Subtraction currentSubtraction = new Subtraction(minuend, subtrahend, paragraph.length());

        if (divident == 0 ||firstAppend && minuend >= divisor) {
            longDivisionsView.append(getDivisionsFirstLine()).append('\n');
            longDivisionsView.append(getDivisionsSecondLine()).append('\n');
            longDivisionsView.append(getDivisionsThirdLine(currentSubtraction)).append('\n');
            longDivisionsView.append(getDivisionsFourthLine(currentSubtraction));
            firstAppend = false;
        } else if(subtrahend != 0) {    // If subtrahend is zero, operation of appending subtraction is skipped
            longDivisionsView.append('\n').append(currentSubtraction);
        }

        shiftParagraph();

        if (lastAppend) {
            appendDivisionsRemainder();
        }
    }

    public String getLongDivisionsView() {
        return longDivisionsView.toString();
    }

    private String getDivisionsFirstLine(){
        return " " + this.divident + getSymbolsLine(getDividentsShift(), ' ') + '|' +
                this.divisor;
    }

    private String getDivisionsSecondLine(){
        return "-" + getSymbolsLine(dividentsDigitsAmount + getDividentsShift() - 1, ' ') + " " +
                '|' + getSymbolsLine(Math.max(divisorsDigitsAmount, dividingResultsDigitsAmount), '-');
    }

    private String getDivisionsThirdLine(Subtraction currentSubtraction){

        StringBuilder thirdLine = new StringBuilder();

        if(subtrahend!=0){
            thirdLine.append(" ");
            int subtrahendShift = DecimalNumber.calculateDigitsAmount(minuend) - DecimalNumber.calculateDigitsAmount(subtrahend);
            thirdLine.append(getSymbolsLine(subtrahendShift, ' ')).append(String.valueOf(subtrahend))
                    .append(getSymbolsLine(dividentsDigitsAmount - DecimalNumber.calculateDigitsAmount(subtrahend) - subtrahendShift,
                            ' ')).append('|').append(dividingResult);
        }else {
            thirdLine.append(currentSubtraction.getSubtrahendLine()).append(getSymbolsLine(dividentsDigitsAmount - DecimalNumber.calculateDigitsAmount(subtrahend),' ')).append('|').append(dividingResult);
        }

        return thirdLine.toString();
    }

    private String getDivisionsFourthLine(Subtraction currentSubtraction){

        StringBuilder fourthLine = new StringBuilder();
        fourthLine.append(' ');

        if(subtrahend != 0 || divident == 0){
            if (getDividentsShift() > 0) {
                fourthLine.append(getSymbolsLine(DecimalNumber.calculateDigitsAmount(divident) - DecimalNumber.calculateDigitsAmount(subtrahend), ' '));
            }
            fourthLine.append(getSymbolsLine(DecimalNumber.calculateDigitsAmount(Math.max(subtrahend, minuend)),'-'));
        }else {
            fourthLine.append(currentSubtraction.getSummaryLine());
        }

        return fourthLine.toString();
    }

    private void shiftParagraph(){
        long difference = minuend - subtrahend;
        int nextOperationShift = DecimalNumber.calculateDigitsAmount(minuend) - DecimalNumber.calculateDigitsAmount(difference);
        if (difference == 0) {
            nextOperationShift++;
        }
        paragraph += getSymbolsLine(nextOperationShift, ' ');
    }

    private int getDividentsShift(){
        return DecimalNumber.calculateDigitsAmount(minuend)
                - DecimalNumber.calculateDigitsAmount(divident) > 0 ? DecimalNumber.calculateDigitsAmount(minuend)
                - DecimalNumber.calculateDigitsAmount(divident) : 0;
    }

    public void appendDivisionsRemainder() {
        paragraph += remainder == 0 ? "" : " ";
        longDivisionsView.append('\n').append(paragraph).append(remainder);
    }

}
