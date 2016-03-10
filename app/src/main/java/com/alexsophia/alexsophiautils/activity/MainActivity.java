package com.alexsophia.alexsophiautils.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.util.TextViewBuilder;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = null;
		tv.setText(new TextViewBuilder().setText("asdfasdfas")
				.setTextSize(24, true, 0, 4).build());
	}
}