package com.alexsophia.alexsophiautils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alexsophia.alexsophiautils.R;

/**
 * Main activity
 */
public class MainActivity extends BaseActivity {
	private Button fragment_activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void findViewById() {
		fragment_activity = (Button) findViewById(R.id.fragment_activity);
	}

	@Override
	public void initView() {

	}

	@Override
	public void setListener() {
		fragment_activity.setOnClickListener(listener);
	}

	@Override
	public void clickView(View v) {
		switch (v.getId()) {
			case R.id.fragment_activity:
				startActivity(new Intent(this, FragmentActivity.class));
				break;
			default:
				super.clickView(v);
		}
	}
}