package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 控件帮助类
 * 
 * @author liuweiping
 * 
 */
public final class ViewUtil {
	/**
	 * 获取屏幕尺寸
	 * @param  context
	 * @return int[]  den[0]为屏幕宽度   den[1]为屏幕高度
	 */
	public final static int[] getScreenWidthAndHeight(Context context) {
		int[] den = new int[2];
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		den[0] = outMetrics.widthPixels;
		den[1] = outMetrics.heightPixels;
		return den;
	}
	/**
	 * 获取屏幕宽度
	 * @param  context
	 * @return 屏幕宽度   
	 */
	public final static int getScreenWidth(Context context){
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}
	/**
	 * 获取屏幕高度
	 * @param  context
	 * @return 屏幕高度 
	 */
	public final static int getScreenHeight(Context context){
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
	/**
	 * 获取
	 * @param context
	 * @param value
	 * @return
	 */
	public final static int getDimensionValue(Context context, int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, context.getResources().getDisplayMetrics());
	}
	/**
	 * dip转换成px
	 * @param context
	 * @param dipValue
	 * @return 转换后的px值
	 */
	public final static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);

	}
	/**
	 * px转换成dip
	 * @param context
	 * @param pxValue
	 * @return 转换后的dip值
	 */
	public final static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
