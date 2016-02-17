package com.alexsophia.alexsophiautils.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

public class TextViewBuilder {
	@SuppressWarnings("unused")
	private Context context;
	private SpannableString msp;
	private int flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

	public TextViewBuilder() {
		
	}
	
	public TextViewBuilder(Context context) {
		this.context = context;
	}

	/**
	 * 设置内容，必须首先调用，且只能调用一次。
	 * 
	 * @param text
	 * @return
	 */
	public TextViewBuilder setText(String text) {
		msp = new SpannableString(text);
		return this;
	}
	
	/**
	 * 完成TextView的设置，返回后供setText调用。
	 * @return
	 */
	public SpannableString build() {
		return msp;
	}

	/**
	 * 设置字体样式
	 * 
	 * @param family
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setTextFamily(String family, int start, int end) {
		msp.setSpan(new TypefaceSpan(family), start, end, flags);
		return this;
	}

	/**
	 * 设置字体大小 - 像素/dp数值设置
	 * 
	 * @param size
	 * @param isDip 标示size的单位 true为dp数值，false为像素数值。
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setTextSize(int size, boolean isDip, int start, int end) {
		msp.setSpan(new AbsoluteSizeSpan(size, isDip), start, end, flags);
		return this;
	}

	/**
	 * 设置字体大小 - 原始大小的比例设置
	 * 
	 * @param proportion
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setTextSize(float proportion, int start, int end) {
		msp.setSpan(new RelativeSizeSpan(proportion), start, end, flags);
		return this;
	}

	/**
	 * 设置字体颜色
	 * 
	 * @param color
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setForegroundColor(int color, int start, int end) {
		msp.setSpan(new ForegroundColorSpan(color), start, end, flags);
		return this;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param color
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setBackgroundColor(int color, int start, int end) {
		msp.setSpan(new BackgroundColorSpan(color), start, end, flags);
		return this;
	}

	/**
	 * 设置字体样式
	 * 
	 * @param style
	 *            android.graphics.Typeface.BOLD 粗体；<br>
	 *            android.graphics.Typeface.ITALIC 斜体；<br>
	 *            android.graphics.Typeface.BOLD_ITALIC 粗斜体；
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setStyle(int style, int start, int end) {
		msp.setSpan(new StyleSpan(style), start, end, flags);
		return this;
	}
	
	/**
	 * 设置下划线
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setUnderline(int start, int end) {
		msp.setSpan(new UnderlineSpan(), start, end, flags);
		return this;
	}
	
	/**
	 * 设置删除线
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setStrikethrough(int start, int end) {
		msp.setSpan(new StrikethroughSpan(), start, end, flags);
		return this;
	}
	
	/**
	 * 设置下标
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setSubscript(int start, int end) {
		msp.setSpan(new SubscriptSpan(), start, end, flags);
		return this;
	}
	
	/**
	 * 设置上标
	 * @param start
	 * @param end
	 * @return
	 */
	public TextViewBuilder setSuperscript(int start, int end) {
		msp.setSpan(new SuperscriptSpan(), start, end, flags);
		return this;
	}
	
	@SuppressWarnings("unused")
	private void demo() {
        msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合/bot"); 

 
        //超级链接（需要添加setMovementMethod方法附加响应）
        //电话
        msp.setSpan(new URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     
        //邮件
        msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     
         
        //网络
        msp.setSpan(new URLSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     
        //短信   使用sms:或者smsto:
        msp.setSpan(new URLSpan("sms:4155551212"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     
        //彩信   使用mms:或者mmsto:
        msp.setSpan(new URLSpan("mms:4155551212"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     
        //地图   
        msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     
 
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
        //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
        msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
 
        //设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
        ColorStateList csllink = null;
        ColorStateList csl = null;
//        XmlResourceParser xppcolor=getResources().getXml (R.color.color);
//        try {
//            csl= ColorStateList.createFromXml(getResources(),xppcolor);
//        }catch(XmlPullParserException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }catch(IOException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }
// 
//        XmlResourceParser xpplinkcolor=getResources().getXml(R.color.linkcolor);
//        try {
//            csllink= ColorStateList.createFromXml(getResources(),xpplinkcolor);
//        }catch(XmlPullParserException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }catch(IOException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        msp.setSpan(new TextAppearanceSpan("monospace",android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
// 
//        //设置项目符号
//        //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色
//        msp.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH,Color.GREEN), 0 ,msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
// 
//        //设置图片
//        Drawable drawable = getResources().getDrawable(R.drawable.icon);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        msp.setSpan(new ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
// 
//        mTextView.setText(msp);
//        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
	}
}
