package com.smarterama.aleksce267.longdivider;

public class DecimalNumber {

    private final int number;
    private final int[] digitalSequence;
    private int position = -1;
    public final static int SCALE = 10;

    DecimalNumber(int number) {

        if (number < 0) {
            throw new IllegalArgumentException("Initial value was less than zero");
        }

        this.number = number;
        this.digitalSequence = splitToDigits(number);
    }

    public String toString(){
        return String.valueOf(number);
    }

    public static int calculateDigitsAmount(long number) {
        if (number == 0) {
            return 1;
        }
        int digitsAmount = 0;
        while (number > 0) {
            number /= SCALE;
            digitsAmount++;
        }
        return digitsAmount;
    }

    public static long appendDigit(long initialValue, int digit) {
        if (digit < 0) {
            throw new IllegalArgumentException("Digit was less than zero");
        }
        return initialValue * SCALE + digit;
    }

    public int getCurrentDigit() {
        return digitalSequence[position];
    }

    public boolean nextDigit() {
        if (position + 1 < digitalSequence.length) {
            position++;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasNextDigit() {
        return position < digitalSequence.length - 1;
    }

    public int getNumber() {
        return number;
    }

    private int[] splitToDigits(int number) {
        int[] digitalSequence;
        int digitsAmount = calculateDigitsAmount(number);
        if (number == 0) {
            digitalSequence = new int[] { 0 };
            return digitalSequence;
        }
        digitalSequence = new int[digitsAmount];
        for (int i = digitsAmount - 1; number > 0; number /= SCALE, i--) {
            digitalSequence[i] = number % SCALE;
        }
        return digitalSequence;
    }

}
