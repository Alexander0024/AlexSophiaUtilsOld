package com.alexsophia.alexsophiautils.constants;

import android.test.AndroidTestCase;

/**
 * Test case for constants SizeConstants
 * Created by AlexSophia on 16/2/17.
 */
public class SizeConstantsTest extends AndroidTestCase {

    public void testByteToBit() throws Exception {
        assertEquals(8, SizeConstants.BYTE_2_BIT);
    }

    public void testKBToByte() throws Exception {
        assertEquals(1024, SizeConstants.KB_2_BYTE);
    }

    public void testMBToByte() throws Exception {
        assertEquals(1024 * 1024, SizeConstants.MB_2_BYTE);
    }

    public void testGBToByte() throws Exception {
        assertEquals(1024 * 1024 * 1024, SizeConstants.GB_2_BYTE);
    }
}
