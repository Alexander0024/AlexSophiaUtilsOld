package com.alexsophia.alexsophiautils.share.views;

/**
 * Created by lyh on 2016-5-6.
 */

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.utils.LogWrapper;


/**
 * 加载图片的TextView
 *
 * @author liuweiping
 *
 */
public class ImageTextView extends TextView {
    private Context mContext;
    private String TAG = ImageTextView.class.getSimpleName();

    public ImageTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    /**
     * 设置文本内容
     *
     * @param text
     * @param imageGetter
     */
    public void setHtmltext(final String text, final MyImageGetter imageGetter) {
        imageGetter.setImageGetterListener(new MyImageGetter.ImageGetterListener() {
            @Override
            public void success() {
                LogWrapper.e(TAG, "text===" + text);
                setHtmltext(text, imageGetter);
            }

            @Override
            public void fail() {
            }
        });
        setText(Html.fromHtml(text == null ? "" : text, imageGetter, null));
    }


    public static int TAG_KEY;
    /**
     * 设置图片文本内容，为解决ListView的item的复用提供
     */
	public void setHtmlTextForListViewItem(final String text, int tagKey,
			int position, final MyImageGetter imageGetter) {
		TAG_KEY = tagKey;
		this.setTag(tagKey, position);
		imageGetter.setTag(tagKey, position);
		imageGetter.setImageGetterListener(new MyImageGetter.ImageGetterListener() {
			@Override
			public void success() {
				LogWrapper.e(TAG, "text===" + text);
				int imageGetterTag = (Integer) imageGetter.getTag(TAG_KEY);
				int imageViewTag = (Integer) ImageTextView.this.getTag(TAG_KEY);
				LogWrapper.e(TAG, "imageGetterTag==" + imageGetterTag
						+ ";imageViewTag===" + imageViewTag);
				if (imageGetterTag == imageViewTag)
					setHtmltext(text, imageGetter);
			}

			@Override
			public void fail() {

			}
		});
		setText(Html.fromHtml(text == null ? "" : text, imageGetter, null));
	}

    /**
     * 设置文本内容并刷新列表
     *
     * @param text
     * @param imageGetter
     * @param action
     */
    public void setHtmltextForAdatper(final String text,
                                      final MyImageGetter imageGetter,
                                      final MyImageGetter.ImageGetterListenerForAdapter action) {
        imageGetter.setImageGetterListenerForAdapter(action);
        setText(Html.fromHtml(text == null ? "" : text, imageGetter, null));
    }
}
