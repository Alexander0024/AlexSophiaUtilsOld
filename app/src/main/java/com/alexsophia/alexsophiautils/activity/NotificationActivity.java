package com.alexsophia.alexsophiautils.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

import com.alexsophia.alexsophiautils.R;

/**
 * Notification activity
 * Created by Alexander on 2016/2/26.
 */
public class NotificationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        // Look up the notification manager service
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Cancel the notification that we started
        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }
}
