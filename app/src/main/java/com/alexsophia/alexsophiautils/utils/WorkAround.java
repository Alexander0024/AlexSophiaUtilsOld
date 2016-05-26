package com.alexsophia.alexsophiautils.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 输入法弹出时，让activity重新定位
 * @author liuweiping
 *
 */
public final class WorkAround {
	public final static void assistActivity(Activity activity) {
		new WorkAround(activity);
	}
	private View mChildOfContent;
	private int usableHeightPrevious;
//	private FrameLayout.LayoutParams frameLayoutParams;
	private int screenWidth;
	private WorkAround(Activity activity) {
		FrameLayout content = (FrameLayout) activity
				.findViewById(android.R.id.content);
		screenWidth = ViewUtil.getScreenWidthAndHeight(activity)[0];
		mChildOfContent = content.getChildAt(0);
		mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					public void onGlobalLayout() {
						possiblyResizeChildOfContent();
					}
				});
//		frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent
//				.getLayoutParams();
	}

	private final void possiblyResizeChildOfContent() {
		int usableHeightNow = computeUsableHeight();
		if (usableHeightNow != usableHeightPrevious) {
			int usableHeightSansKeyboard = mChildOfContent.getRootView()
					.getHeight();
			int heightDifference = usableHeightSansKeyboard - usableHeightNow;
			int height = 0;
			if (heightDifference > (usableHeightSansKeyboard / 4)) {
				// frameLayoutParams.height = usableHeightSansKeyboard
				// - heightDifference;
				height = usableHeightSansKeyboard - heightDifference;
			} else {
				// frameLayoutParams.height = usableHeightSansKeyboard;
				height = usableHeightSansKeyboard;
			}
			mChildOfContent.layout(0, 0, screenWidth, height);
			// mChildOfContent.requestLayout();
			usableHeightPrevious = usableHeightNow;
		}
	}

	private final int computeUsableHeight() {
		Rect r = new Rect();
		mChildOfContent.getWindowVisibleDisplayFrame(r);
		return (r.bottom - r.top);
	}

}