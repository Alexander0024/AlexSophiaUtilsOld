package com.alexsophia.alexsophiautils.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.test.AndroidTestCase;

/**
 * Test cases for TextView Builder.
 * Created by Administrator on 2016/2/19.
 */
public class TextViewBuilderTest extends AndroidTestCase {
    private TextViewBuilder textViewBuilder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        textViewBuilder = new TextViewBuilder();
    }

    public void testNormalBuilder() throws Exception {
        textViewBuilder.setText("TestCase for normal setText").build();
    }

    public void testAllFunctions() throws Exception {
        String testString = "TestCase for all custom functions.";
        textViewBuilder.setText(testString)
                .setBackgroundColor(Color.YELLOW, 0, testString.indexOf(" "))
                .setForegroundColor(Color.BLUE, 0, testString.indexOf(" "))
                .setTextSize(24, true, 0, testString.length())
                .setTextSize(1.5f, 0, testString.indexOf(" "))
                .setStyle(Typeface.BOLD, 8, 12)
                .build();
    }
}
