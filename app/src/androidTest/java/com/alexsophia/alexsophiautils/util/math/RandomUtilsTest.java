package com.alexsophia.alexsophiautils.util.math;

import android.test.AndroidTestCase;

/**
 * Test cases for Random Utils.
 * Created by AlexSophia on 16/2/17.
 */
public class RandomUtilsTest extends AndroidTestCase {
    private static int randomTimes = 10;

    public void testGetRandomNumbersAndLetters() throws Exception {
        for (int i = 0; i < randomTimes; i++) {
            assertNotNull(RandomUtils.getRandomNumbersAndLetters(8));
        }
    }

    public void testGetRandomNumbers() throws Exception {
        for (int i = 0; i < randomTimes; i++) {
            assertNotNull(RandomUtils.getRandomNumbers(8));
        }
    }

    public void testGetRandomLetters() throws Exception {
        for (int i = 0; i < randomTimes; i++) {
            assertNotNull(RandomUtils.getRandomLetters(8));
        }
    }
}
