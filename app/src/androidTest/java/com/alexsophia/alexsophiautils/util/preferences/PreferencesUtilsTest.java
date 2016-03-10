package com.alexsophia.alexsophiautils.util.preferences;

import android.test.AndroidTestCase;

/**
 * Test cases for preferences utils.
 * Created by Administrator on 2016/2/19.
 */
public class PreferencesUtilsTest extends AndroidTestCase {

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        PreferencesUtils.clearData(getContext());
    }

    public void testGetString() throws Exception {
        String key = "testString";
        String value = "testValue";
        String defaultValue = "defaultValue";
        String getValue = PreferencesUtils.getString(getContext(), key, defaultValue);
        assertEquals(defaultValue, getValue);
        boolean result = PreferencesUtils.putString(getContext(), key, value);
        if (result) {
            getValue = PreferencesUtils.getString(getContext(), key);
            assertEquals(value, getValue);
        } else {
            throw new Exception("Can not put String into preferences.");
        }
    }

    public void testGetInt() throws Exception {
        String key = "testInt";
        int value = 1357924680;
        int defaultValue = -1;
        int getValue = PreferencesUtils.getInt(getContext(), key, defaultValue);
        assertEquals(defaultValue, getValue);
        boolean result = PreferencesUtils.putInt(getContext(), key, value);
        if (result) {
            getValue = PreferencesUtils.getInt(getContext(), key);
            assertEquals(value, getValue);
        } else {
            throw new Exception("Can not put int into preferences.");
        }
    }

    public void testGetLong() throws Exception {
        String key = "testLong";
        long value = 1357924680;
        long defaultValue = -1;
        long getValue = PreferencesUtils.getLong(getContext(), key, defaultValue);
        assertEquals(defaultValue, getValue);
        boolean result = PreferencesUtils.putLong(getContext(), key, value);
        if (result) {
            getValue = PreferencesUtils.getLong(getContext(), key);
            assertEquals(value, getValue);
        } else {
            throw new Exception("Can not put long into preferences.");
        }
    }

    public void testGetFloat() throws Exception {
        String key = "testFloat";
        float value = 135790.24f;
        float defaultValue = -1f;
        float getValue = PreferencesUtils.getFloat(getContext(), key, defaultValue);
        assertEquals(defaultValue, getValue);
        boolean result = PreferencesUtils.putFloat(getContext(), key, value);
        if (result) {
            getValue = PreferencesUtils.getFloat(getContext(), key);
            assertEquals(value, getValue);
        } else {
            throw new Exception("Can not put float into preferences.");
        }
    }

    public void testGetBoolean() throws Exception {
        String key = "testBoolean";
        boolean getValue = PreferencesUtils.getBoolean(getContext(), key, false);
        assertEquals(false, getValue);
        boolean result = PreferencesUtils.putBoolean(getContext(), key, true);
        if (result) {
            getValue = PreferencesUtils.getBoolean(getContext(), key);
            assertEquals(true, getValue);
        } else {
            throw new Exception("Can not put boolean into preferences.");
        }
    }
}