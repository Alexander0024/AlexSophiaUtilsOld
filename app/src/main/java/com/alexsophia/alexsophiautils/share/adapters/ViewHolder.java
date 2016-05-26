package com.alexsophia.alexsophiautils.share.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.share.exception.ViewActionException;
import com.alexsophia.alexsophiautils.share.views.RoundProgressBar;
import com.alexsophia.alexsophiautils.utils.ImgUtils;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * 适配器的viewholder
 *
 * @author liuweiping
 */
public class ViewHolder {
    private int mPos;
    private Context mContext;
    /**
     * 保存复用的item的view，键为view的id，值为view对象
     */
    private SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, int layoutId, ViewGroup parent, int pos) {
        this.mPos = pos;
        mContext = context;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
        AutoUtils.autoSize(mConvertView);
    }

    public static ViewHolder get(View convertView, Context context,
                                 int layoutId, ViewGroup parent, int pos) {
        if (convertView == null) {
            return new ViewHolder(context, layoutId, parent, pos);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPos = pos;
            return viewHolder;
        }
    }

    public View getConVertView() {
        return mConvertView;
    }

    /**
     * 获取listview中的位置
     *
     * @return
     */
    public int getPos() {
        return mPos;
    }

    /**
     * 根据view的id获取view对象
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View v = mViews.get(viewId);
        if (v == null) {
            v = mConvertView.findViewById(viewId);
            mViews.put(viewId, v);
        }
        return (T) v;
    }

    /**
     * 设置view的可见性
     *
     * @param viewId
     * @param visibility 可以是View.GONE,View.VISIBLE,View.INVISIBLE
     * @return
     */
    public ViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置View是否selected
     *
     * @param viewId
     * @param isSelected
     * @return
     */
    public ViewHolder setSelected(int viewId, boolean isSelected) {
        getView(viewId).setSelected(isSelected);
        return this;
    }

    /**
     * 设置CompoundButton的check状态
     *
     * @param viewId
     * @param isChecked
     * @return
     */
    public ViewHolder setChecked(int viewId, boolean isChecked) {
        View v = getView(viewId);
        if (v instanceof CompoundButton) {
            CompoundButton cb = (CompoundButton) v;
            cb.setChecked(isChecked);
            return this;
        } else {
            throw new ViewActionException("this view is not CompoundButton");
        }
    }

    /**
     * 设置view是否可操作
     *
     * @param viewId
     * @param isEnable
     * @return
     */
    public ViewHolder setEnable(int viewId, boolean isEnable) {
        getView(viewId).setEnabled(isEnable);
        return this;
    }

    /**
     * 设置view是否可获取焦点
     *
     * @param viewId
     * @param isFocusable
     * @return
     */
    public ViewHolder setFocusable(int viewId, boolean isFocusable) {
        getView(viewId).setFocusable(isFocusable);
        return this;
    }

    /**
     * 为TextView设置文字
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setText(text);
            return this;
        } else {
            throw new ViewActionException("this view is not TextView");
        }

    }

    /**
     * 为TextView设置文字
     *
     * @param viewId
     * @param textRes
     * @return
     */
    public ViewHolder setText(int viewId, int textRes) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setText(textRes);
            return this;
        } else {
            throw new ViewActionException("this view is not TextView");
        }
    }

    /**
     * 为TextView设置文字大小
     */
    public ViewHolder setTextSize(int viewId, float size) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setTextSize(size);
            return this;
        } else {
            throw new ViewActionException("this view is not TextView");
        }
    }

    /**
     * 为TextView设置文字颜色
     */
    public ViewHolder setTextColor(int viewId, int color) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setTextColor(color);
            return this;
        } else {
            throw new ViewActionException("this view is not TextView");
        }
    }

    /**
     * 为TextView设置drawableLeft
     *
     * @param viewId
     * @param drawableRes
     * @return
     */
    public ViewHolder setCompoundLeftDrawables(int viewId, int drawableRes) {
        Drawable drawable = mContext.getResources().getDrawable(drawableRes);
        if (drawable != null) {
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            TextView tv = getView(viewId);
            tv.setCompoundDrawables(drawable, null, null, null);
        }
        return this;
    }

    /**
     * 为TextView设置drawableRight
     *
     * @param viewId
     * @param drawableRes
     * @return
     */
    public ViewHolder setCompoundRightDrawables(int viewId, int drawableRes) {
        Drawable drawable = mContext.getResources().getDrawable(drawableRes);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            TextView tv = getView(viewId);
            tv.setCompoundDrawables(null, null, drawable, null);
        }
        return this;
    }

    /**
     * 为TextView设置drawableTop
     *
     * @param viewId
     * @param drawableRes
     * @return
     */
    public ViewHolder setCompoundTopDrawables(int viewId, int drawableRes) {
        Drawable drawable = mContext.getResources().getDrawable(drawableRes);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            TextView tv = getView(viewId);
            tv.setCompoundDrawables(null, drawable, null, null);
        }
        return this;
    }

    /**
     * 为TextView设置drawableBottom
     *
     * @param viewId
     * @param drawableRes
     * @return
     */
    public ViewHolder setCompoundBottomDrawables(int viewId, int drawableRes) {
        Drawable drawable = mContext.getResources().getDrawable(drawableRes);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            TextView tv = getView(viewId);
            tv.setCompoundDrawables(null, null, null, drawable);
        }
        return this;
    }

    /**
     * 为view设置backgroundresource
     */
    public ViewHolder setBackGroundResource(int viewId, int resid) {
        getView(viewId).setBackgroundResource(resid);
        return this;
    }

    /**
     * 为view设置点击事件
     */
    public ViewHolder setClickListener(int viewId,
                                       OnClickListener onClickListener) {

        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 设置元素的长按点击事件
     *
     * @param viewId
     * @param onLongClickListener
     * @return
     */
    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener
            onLongClickListener) {
        getView(viewId).setOnLongClickListener(onLongClickListener);
        return this;
    }

    /**
     * 为AbsListView添加itemclick事件
     */
    public ViewHolder setItemClickListener(int viewId,
                                           OnItemClickListener onItemClickListener) {
        View v = getView(viewId);
        if (v instanceof AdapterView) {
            AdapterView absListView = (AdapterView) v;
            absListView.setOnItemClickListener(onItemClickListener);
            return this;
        } else {
            throw new ViewActionException("this view is not AdapterView");
        }

    }

    /**
     * 为AbsListView添加itemLongClick事件
     */
    public ViewHolder setItemLongClickListener(int viewId,
                                               OnItemLongClickListener onItemLongClickListener) {
        View v = getView(viewId);
        if (v instanceof AdapterView) {
            AdapterView absListView = (AdapterView) v;
            absListView.setOnItemLongClickListener(onItemLongClickListener);
            return this;
        } else {
            throw new ViewActionException("this view is not AdapterView");
        }

    }


    /**
     * 为AbsListView添加itemSeclectedClick事件
     */
    public ViewHolder setItemSeclectClickListener(int viewId,
                                                  OnItemSelectedListener onISelectedListener) {
        View v = getView(viewId);
        if (v instanceof AdapterView) {
            AdapterView absListView = (AdapterView) v;
            absListView.setOnItemSelectedListener(onISelectedListener);
            return this;
        } else {
            throw new ViewActionException("this view is not AdapterView");
        }

    }

    /**
     * 为view设置ontouch事件
     */
    public ViewHolder setTouchListener(int viewId,
                                       OnTouchListener onTouchListener) {
        getView(viewId).setOnTouchListener(onTouchListener);
        return this;
    }

    /**
     * 为CompoundButton设置checkChange事件
     */
    public ViewHolder setCheckChangeListener(int viewId,
                                             OnCheckedChangeListener onCheckedChangeListener) {
        View v = getView(viewId);
        if (v instanceof CompoundButton) {
            CompoundButton rd = (CompoundButton) v;
            rd.setOnCheckedChangeListener(onCheckedChangeListener);
            return this;
        } else {
            throw new ViewActionException("this view is not CompoundButton");
        }

    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param resource
     * @return
     */
    public ViewHolder setImageViewResource(int viewId, int resource) {
        View v = getView(viewId);
        if (v instanceof ImageView) {
            ImageView iv = (ImageView) v;
            iv.setImageResource(resource);
            return this;
        } else {
            throw new ViewActionException("this view is not ImageView");
        }
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageViewBitmap(int viewId, Bitmap bm) {
        View v = getView(viewId);
        if (v instanceof ImageView) {
            ImageView iv = (ImageView) v;
            iv.setImageBitmap(bm);
            return this;
        } else {
            throw new ViewActionException("this view is not ImageView");
        }
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public ViewHolder setImageViewUrl(int viewId, String url,
                                      int defaultImageResId, int errorImageResId) {
        View v = getView(viewId);
        if (v instanceof ImageView) {
            ImageView iv = (ImageView) v;
            ImgUtils.displayImage(url, iv, defaultImageResId,
                    errorImageResId);
            return this;
        } else {
            throw new ViewActionException("this view is not ImageView");
        }
    }

    /**
     * 给进度条设进度
     */
    public ViewHolder setProgress(int viewId, float progress) {
        View v = getView(viewId);
        if (v instanceof RoundProgressBar) {
            RoundProgressBar rPgb = (RoundProgressBar) v;
            rPgb.setProgress((int) progress);
            return this;
        } else {
            throw new ViewActionException("this view is not RoundProgressBar");
        }
    }
    /**
     * 设置粗体
     */
    public ViewHolder setBlod(int viewId,boolean b) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.getPaint().setFakeBoldText(b);
            return this;
        } else {
            throw new ViewActionException("this view is not TextView");
        }

    }

}
