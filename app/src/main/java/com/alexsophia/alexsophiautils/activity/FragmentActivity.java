package com.alexsophia.alexsophiautils.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.alexsophia.alexsophiautils.fragments.Fragment1;
import com.alexsophia.alexsophiautils.fragments.Fragment2;

/**
 * Fragment Manager for add fragment on the air.
 * Created by Alexander on 2016/2/26.
 */
public class FragmentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment manager and fragment transaction for fragment control
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Get the current display info
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        // android.R.id.content 为引用活动的内容视图
        //noinspection deprecation
        if (display.getWidth() > display.getHeight()) {
            // Landscape Mode
            Fragment1 fragment1 = new Fragment1();
            fragmentTransaction.replace(android.R.id.content, fragment1);
        } else {
            // Portrait Mode
            Fragment2 fragment2 = new Fragment2();
            fragmentTransaction.replace(android.R.id.content, fragment2);
        }
        // Call addToBackStack for back to the previous fragment.
        // Different with Activity here.
        fragmentTransaction.addToBackStack(null);
        // Make the change
        fragmentTransaction.commit();
    }
}
