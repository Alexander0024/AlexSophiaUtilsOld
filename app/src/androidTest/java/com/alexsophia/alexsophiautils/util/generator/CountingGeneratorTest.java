package com.alexsophia.alexsophiautils.util.generator;

import android.test.AndroidTestCase;

/**
 * Test Counting generator
 * Created by Administrator on 2016/2/19.
 */
public class CountingGeneratorTest extends AndroidTestCase {
    private int times = 20;

    public void testBooleanGenerator() throws Exception {
        CountingGenerator.Boolean boolGen = new CountingGenerator.Boolean();
        Boolean init = false;
        for (int i = 0; i < times; i++) {
            init = !init;
            assertEquals(init, boolGen.next());
        }
    }

    public void testByteGenerator() throws Exception {
        CountingGenerator.Byte bytes = new CountingGenerator.Byte();
        byte excepted = 0;
        for (int i = 0; i < times; i++) {
            assertEquals(excepted, (byte)bytes.next());
        }
    }


}