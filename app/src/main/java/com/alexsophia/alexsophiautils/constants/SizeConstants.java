/**
 * CopyRight (C) 1989-2989 <Alexander>
 * Copy Right Author     : Alexander_LWZ
 * JDK Version Used      : jdk1.7.0_45
 * Comments              : Constant for data size transfer.
 * Version               : 1.0.0
 * Create Date           : 2016.1.5
 * Modification History  
 * Sr Date       Why & What is modified
 */

package com.alexsophia.alexsophiautils.constants;

public final class SizeConstants {

    /** GB to byte 1024*1024*1024 **/
    public static final long GB_2_BYTE = 1073741824;
    /** MB to byte 1024*1024 **/
    public static final long MB_2_BYTE = 1048576;
    /** KB to byte 1024 **/
    public static final long KB_2_BYTE = 1024;
    /** Byte to bit 8 **/
    public static final long BYTE_2_BIT = 8;

    private SizeConstants() {
        throw new AssertionError();
    }
}
