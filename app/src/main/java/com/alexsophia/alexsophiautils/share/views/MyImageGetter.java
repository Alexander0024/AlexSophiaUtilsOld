package com.alexsophia.alexsophiautils.share.views;

/**
 * Created by lyh on 2016-5-6.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.SparseArray;

import com.alexsophia.alexsophiautils.utils.ImgUtils;
import com.alexsophia.alexsophiautils.utils.LogWrapper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public abstract class MyImageGetter implements Html.ImageGetter {
    /**
     * 图片展现的宽度
     */
    private int width = -1;
    /**
     * 图片展现的高度
     */
    private int height = -1;
    private boolean isDowning = false;

    private AdateperAction mAction;
    private String idenStr;
    private  int intrinsicWidth;
    private  int intrinsicHeight;
    public void setAdapterAction(AdateperAction action) {
        this.mAction = action;
    }

    public AdateperAction getAdapterAction() {
        return this.mAction;
    }

    public boolean isDownLoading() {
        return this.isDowning;
    }

    private SparseArray mKeyedTags;



    public void setTag(int key, final Object tag) {
        setKeyedTag(key, tag);
    }

    private void setKeyedTag(int key, Object tag) {
        if (mKeyedTags == null) {
            mKeyedTags = new SparseArray<Object>();
        }
        mKeyedTags.put(key, tag);
    }

    public Object getTag(int key) {
        if (mKeyedTags != null) return mKeyedTags.get(key);
        return null;
    }

    /**
     * 制定高度和宽度的图片压缩时，调用这个构造方法
     *
     * @param w
     *            压缩后的宽度
     * @param h
     *            压缩后的高度
     */
    public MyImageGetter(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public MyImageGetter(int w, int h, String indenStr) {
        this.width = w;
        this.height = h;
        this.idenStr = indenStr;
    }

    /**
     * 不需要压缩图片时调用这个构造方法
     *
     */
    public MyImageGetter() {
    }

    public MyImageGetter(String indenStr) {
        this.idenStr = indenStr;
    }

    @Override
    public Drawable getDrawable(final String source) {
        Drawable drawable = null;
        String localPath = getLocalPath(source);
        if (localPath != null) {
            // 封装路径
            final File file = new File(getLocalPath(source));

            // 判断是否存在
            if (file.exists()) {
                LogWrapper.e("MyImageGetter",
                        "图片存在===" + file.getAbsolutePath());
                Bitmap bt = ImgUtils.compressImageFromFile(
                        file.getAbsolutePath(), width, height);
                drawable = new BitmapDrawable(bt);
                // drawable = Drawable.createFromPath(file.getAbsolutePath());

                if (drawable != null) {
                    if(intrinsicWidth==0){
                        intrinsicWidth=drawable.getIntrinsicWidth();
                    }
                    if(intrinsicHeight==0){
                        intrinsicHeight = drawable.getIntrinsicHeight();
                    }
                    int top = width == -1 ? height*intrinsicWidth/intrinsicHeight : width;
                    int bottom = height == -1 ? intrinsicHeight
                            : height;
                    drawable.setBounds(0, 0,top
                            ,
                            bottom);
                }
            } else {
                LogWrapper.e("MyImageGetter", "图片不存在");
                String url = "";
                if (source.startsWith("http://")) {
                    url = source;
                } else {
                    url = getDownLoadPathWithPrex(source);
                }
                LogWrapper.e("MyImageGetter", "url====" + url);
                // 不存在即开启异步任务加载网络图片
                HttpUtils http = new HttpUtils();
                http.download(url, file.getAbsolutePath(), new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
//                        LogWrapper.e("MyImageGetter", url + "成功");
                                if (imageGetterListener != null)
                                    imageGetterListener.success();
                                else if (imageGetterListenerForAdapter != null)
                                    imageGetterListenerForAdapter
                                            .successAdapter(idenStr);

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        LogWrapper.e("MyImageGetter", "" + s);
                                if (imageGetterListener != null)
                                    imageGetterListener.fail();
                                else if (imageGetterListenerForAdapter != null)
                                    imageGetterListenerForAdapter.fail();

                    }
                });

            }
        }
        return drawable;
    }

    private ImageGetterListener imageGetterListener;

    public void setImageGetterListener(ImageGetterListener getterListener) {
        this.imageGetterListener = getterListener;
    }

    public interface ImageGetterListener {

        void success();

        void fail();
    }

    private ImageGetterListenerForAdapter imageGetterListenerForAdapter;

    public void setImageGetterListenerForAdapter(
            ImageGetterListenerForAdapter imageGetterListenerForAdapter) {
        this.imageGetterListenerForAdapter = imageGetterListenerForAdapter;
    }

    public interface ImageGetterListenerForAdapter {
        void successAdapter(String idenStr);

        void fail();
    }

    /**
     * 根据网络路径获取本地保存路径
     */
    public abstract String getLocalPath(String source);

    /**
     * 根据网络路径获取真正的网络下载路径,缺少域名的路径加上域名
     */
    public abstract String getDownLoadPathWithPrex(String source);
}
