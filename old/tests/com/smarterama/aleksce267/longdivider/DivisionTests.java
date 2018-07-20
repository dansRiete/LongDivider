package com.smarterama.aleksce267.longdivider;

import org.junit.Rule;
import org.junit.rules.*;
import org.junit.runner.Description;

import java.util.Random;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DivisionTests {
    private static String watchedLog;
    private static Random random = new Random();
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println(watchedLog);
        }
    };

    public static int getRandomBoundaryValue(){
        return (int) (2 * Math.pow(10, random.nextInt(9)));

    }
    @org.junit.Test
    public void randomDividingTest() {

        for (int i = 0; i < 1000000; i++) {

            int randomDivident = 0;
            int randomDivisor = 0;

            do {
                randomDivident = random.nextInt(getRandomBoundaryValue());
                randomDivisor = random.nextInt(getRandomBoundaryValue());
            } while (randomDivisor <= 0);

            Division longDivision = new Division(randomDivident, randomDivisor);
            double receivedResult = longDivision.getMathDividingResult();
            double correctResult = new CorrectDecimal(randomDivident, randomDivisor, longDivision.getDecimalAccuracy()).getCorrectDecimal();
            watchedLog = longDivision.getLongDivisionsView() + "\n\n\n";
            assertThat(receivedResult, is(correctResult));

        }

    }

    @org.junit.Test
    public void testDisplayViews() {

        nullTest();
        System.out.println("\n\n\n");
        Random random = new Random();

        for (int i = 0; i < 200; i++) {//

            int randomDivident;
            int randomDivisor;

            do {
                randomDivident = random.nextInt(getRandomBoundaryValue());
                randomDivisor = random.nextInt(getRandomBoundaryValue());
            } while (randomDivisor <= 0);

            Division longDivision = new Division(randomDivident, randomDivisor);
            double receivedResult = longDivision.getMathDividingResult();
            double correctResult = new CorrectDecimal(randomDivident, randomDivisor, longDivision.getDecimalAccuracy()).getCorrectDecimal();
//            System.out.println("Divident = " + randomDivident + " Divisor = " + randomDivisor);
            System.out.println(longDivision.getLongDivisionsView() + "\n\n\n");
            assertThat(receivedResult, is(correctResult));
        }

    }


    @org.junit.Test
    public void nullTest() {

        int divident = 0;
        int divisor = 5500;
        Division currentDivision = new Division(divident, divisor);
        System.out.println(currentDivision.getLongDivisionsView());
        assertThat(currentDivision.getMathDividingResult(), is(0.0));
    }

    @org.junit.Test
    public void manualTest(){
        int divident = 25;
        int divisor = 39;

        Division longDivision = new Division(divident, divisor);
        double receivedResult = longDivision.getMathDividingResult();
        double correctResult = new CorrectDecimal(divident, divisor, longDivision.getDecimalAccuracy()).getCorrectDecimal();
//            System.out.println("Divident = " + randomDivident + " Divisor = " + randomDivisor);
        System.out.println(longDivision.getLongDivisionsView() + "\n\n\n");
        assertThat(receivedResult, is(correctResult));

    }
}
