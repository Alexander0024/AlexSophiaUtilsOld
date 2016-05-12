package com.alexsophia.alexsophiautils.util;

import android.util.Log;

import junit.framework.TestCase;

/**
 * Common test cases for Java functions.
 * Created by Alexander on 16/5/12.
 */
public class CommonTest extends TestCase {

    class Num {
        private int i;

        public Num(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    public void testReferences() throws Exception {
        Num numA = new Num(5);
        Num numB = new Num(9);
        Log.e("TEST", "A = " + numA.getI() + "; B = " + numB.getI());
        switchNum(numA, numB);
        Log.e("TEST", "A = " + numA.getI() + "; B = " + numB.getI());
    }

    private void switchNum(Num numA, Num numB) {
        numA.setI(numB.getI());
        numB.setI(888);
    }
}
