package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtil {
	private static Toast toast;

	public final static Toast show(Context context, String showText, int duration,
			int gravity, int xOffset, int yOffset) {
		Toast t = Toast.makeText(context, showText, duration);
		t.setGravity(gravity, xOffset, yOffset);
		t.show();
		return t;
	}

	public final static Toast show(Context context, int showText, int duration,
			int gravity, int xOffset, int yOffset) {
		Toast t = Toast.makeText(context, showText, duration);
		t.setGravity(gravity, xOffset, yOffset);
		t.show();
		return t;
	}

	public final static Toast show(Context context, String showText, int duration,
			int gravity) {
		return show(context, showText, duration, gravity, 0, 0);
	}

	public final static Toast show(Context context, int showText, int duration,
			int gravity) {
		return show(context, showText, duration, gravity, 0, 0);
	}

	public final static Toast show(Context context, String showText, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, showText, duration);
		} else {
			toast.setText(showText);
		}
		toast.show();

		return toast;
	}

	public final static Toast show(Context context, int showText, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, showText, duration);
		} else {
			toast.setText(showText);
		}
		toast.show();

		return toast;
	}

	public final static Toast showShort(Context context, String showText) {
		return show(context, showText, Toast.LENGTH_SHORT);
	}

	public final static Toast showShort(Context context, String showText, int gravity) {
		return show(context, showText, Toast.LENGTH_SHORT, gravity);
	}

	public final static Toast showLong(Context context, String showText) {
		return show(context, showText, Toast.LENGTH_LONG);
	}

	public final static Toast showLong(Context context, String showText, int gravity) {
		return show(context, showText, Toast.LENGTH_LONG, gravity);
	}
}
