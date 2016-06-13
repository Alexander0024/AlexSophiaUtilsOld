package com.alexsophia.alexsophiautils.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liuweiping on 2016-2-26.
 */
public class ImgUtils {
    private static final String TAG = "ImgUtils";

    /**
     * 使用ImageLoader显示图片
     *
     * @param url               　图片地址
     *                          "http://site.com/image.png" // from Web
     *                          "file:///mnt/sdcard/image.png" // from SD card
     *                          "file:///mnt/sdcard/video.mp4" // from SD card (video thumbnail)
     *                          "content://media/external/images/media/13" // from content provider
     *                          "content://media/external/video/media/13" // from content
     *                          provider (video thumbnail)
     *                          "assets://image.png" // from assets
     *                          "drawable://" + R.drawable.img // from drawables (non-9patch images)
     * @param iv                需要显示到的ImageView
     * @param defaultImageResId 默认图片，加载过程中显示的图片
     * @param errorImageResId   加载失败时显示的图片
     */
    public static void displayImage(String url, ImageView iv, int defaultImageResId, int
            errorImageResId) {
        if (StringUtil.isEmpty(url)) {
            iv.setImageResource(defaultImageResId);
        } else {
            ImageLoader.getInstance().displayImage(formatURL(url), iv, new DisplayImageOptions
                    .Builder().showImageOnLoading(defaultImageResId).showImageOnFail
                    (errorImageResId).cacheInMemory(true).cacheOnDisk(true).build());
        }
    }

    /**
     * 初始化ImageLoader
     *
     * @param context Context
     */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, CommonDirectory.imgCache());
        // 获取到缓存的目录地址
        LogWrapper.e(TAG, "Image Loader Cache Directory: " + cacheDir.getPath());
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLICATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .memoryCacheExtraOptions(480, 800)
                // 线程池内加载的数量
                .threadPoolSize(3)
                // 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
            /*
             * When you display an image in a small ImageView
             *  and later you try to display this image (from identical URI) in a larger ImageView
             *  so decoded image of bigger size will be cached in memory as a previous decoded
             *  image of smaller size.
             *  So the default behavior is to allow to cache multiple sizes of one image in memory.
             *  You can deny it by calling this method:
             *  so when some image will be cached in memory then previous cached size of this
             *  image (if it exists)
             *   will be removed from memory cache before.
             */
                .denyCacheImageMultipleSizesInMemory()

                /**
                 * 设置内存缓存
                 */
//                 .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
//                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
//                .memoryCacheSizePercentage(13) // default

                /**
                 * 设置SD卡缓存
                 */
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 可以自定义缓存路径
//                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
//                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())

                //将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
                // 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间

                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default

                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    /**
     * 初始化加载image的option
     */
    private static void initOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
//                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
//                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
//                .preProcessor(...)
//                .postProcessor(...)
//                .extraForDownloader(...)
//                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
//                .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
//                .decodingOptions(...)  // 图片的解码设置
//                .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new
// RoundedBitmapDisplayer(20)
//                .handler(new Handler()) // default
                .build();
    }

    public static void loadImage(String url, ImageLoadingListener imageLoadingListener) {
        ImageLoader.getInstance().loadImage(url, imageLoadingListener);
    }

    /**
     * 将bitmap保存到SD卡
     */
    public static boolean saveBitmapToSDCard(Bitmap b, String path,
                                             String fileName, boolean isNeedRecycle) {
        FileOutputStream fos = null;
        try {
            String strPath = new String(path);
            File fPath = new File(strPath);
            if (!fPath.exists()) {
                // fPath.mkdirs();
                FileUtil.mkDir(fPath);
            }
            File file = new File(strPath + fileName);
            LogWrapper.e(TAG, "file = " + file.toString());
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            if (b != null) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                if (isNeedRecycle) {
                    if (!b.isRecycled())
                        b.recycle();
                    b = null;
                    System.gc();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogWrapper.e(TAG, "send picture to dbserver 关闭上传图片的数据流失败！");
                }
            }
        }

    }

    /**
     * 获取压缩比例
     *
     * @param options
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int maxWidth, int maxHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) maxHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) maxWidth);
            }
            final float totalPixels = width * height;
            final float maxTotalPixels = maxWidth * maxHeight * 2;
            while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * 获取本地图片的Bitmap,未压缩
     *
     * @param imgPath 本地图片的路径
     * @return 图片的bitmap
     */
    public static Bitmap getLocalBitmap(String imgPath) {
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 1;
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true; // 获取资源图片
            FileInputStream fis = new FileInputStream(imgPath);
            return BitmapFactory.decodeStream(fis, null, opt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取本地图片的Bitmap，根据传入的width ,height进行压缩
     */
    public static Bitmap getLocalBitmap(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeFile(path, options);
        // 生成压缩的图片
        int w = options.outWidth;
        int h = options.outHeight;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        float scaleWidth = 1f;
        float scaleHeight = 1f;
        float scale = 1f;
        if (w > width || h > height) {
            // 计算缩放比例
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
            scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        }
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
        if (bm != null && bm != newbm && !bm.isRecycled()) {
            LogWrapper.e(TAG, "图片回收中...");
            bm.recycle();
        }
        return newbm;

    }

    /**
     * 将bitmap压缩成指定大小并保存到指定文件
     *
     * @param bmp
     * @param file
     * @param kb
     */
    public static void compressBitmapToFile(Bitmap bmp, File file, int kb) {
        FileOutputStream fos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int options = 100;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > kb) {
                baos.reset();
                options -= 10;
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }

            fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 将图片压缩成指定大小
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap calseImg(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeFile(path, options);
        // 生成压缩的图片
        int w = options.outWidth;
        int h = options.outHeight;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        if (bm == null)
            return null;
        float scaleWidth = 1f;
        float scaleHeight = 1f;
        float scale = 1f;
        if (w > width || h > height) {
            // 计算缩放比例
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
            scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
        }
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
        if (bm != null && bm != newBitmap && !bm.isRecycled()) {
            LogWrapper.e(TAG, "图片回收中...");
            bm.recycle();
        }
        return newBitmap;
    }

    /**
     * 将彩色图片转换成灰色图片
     *
     * @param bitmap     源Bitmap
     * @param saturation a value of 0 maps the color to gray-scale. 1 is identity.
     * @return
     */
    public static Bitmap toGrayImage(Bitmap bitmap, float saturation) {
        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap grayImg = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(grayImg);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(saturation < 0f || saturation > 1f ? 1f
                : saturation);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmap.recycle();
        return grayImg;
    }

    /**
     * 将彩色图片转换成灰色图片
     *
     * @param source     源文件
     * @param dest       目标文件
     * @param saturation a value of 0 maps the color to gray-scale. 1 is identity.
     */
    public static void toGrayImage(String source, String dest, float saturation) {
        FileOutputStream stream = null;
        Bitmap bitmap = null;
        Bitmap grayImg = null;
        try {
            bitmap = BitmapFactory.decodeFile(source);
            if (bitmap == null)
                return;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            grayImg = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(grayImg);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(saturation < 0f || saturation > 1f ? 1f
                    : saturation);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                    colorMatrix);
            paint.setColorFilter(colorMatrixFilter);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            File file = new File(dest);
            if (!file.exists()) {
                file.createNewFile();
            }
            stream = new FileOutputStream(file);
            grayImg.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
        } catch (Exception e) {
            @SuppressWarnings("unused")
            String msg = e.getMessage();
        } finally {
            if (bitmap != null)
                bitmap.recycle();
            if (grayImg != null)
                grayImg.recycle();
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    /**
     * 保证大小，还得保证清晰度
     *
     * @param imagePath
     * @param maxSize
     * @return
     */
    public static void compressLocalImage(String imagePath, int maxSize,
                                          int width, int height) {
        File file = new File(imagePath);
        if (!file.exists()) {
            return;
        }
        // 根据屏幕尺寸获取一个压缩后的Bitmap

        Bitmap image = calseImg(imagePath, width, height);
        file = new File(imagePath);
        if (file.exists()) {
            file.delete();
        }
        if (image == null)
            return;
        // 再根据像素进行压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        LogWrapper.e(TAG, baos.toByteArray().length / 1024 + "=====");
        while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            LogWrapper.e(TAG, baos.toByteArray().length / 1024 + "-----");
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
            // 这里压缩options%，把压缩后的数据存放到baos中
        }
        LogWrapper.e(TAG, baos.toByteArray().length
                + "<<<<<<baos<<<<<<<<<<<<<<");
        // 把压缩后的数据baos存放到文件中
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (Exception e) {
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        // 把ByteArrayInputStream数据生成图片
        if (image != null && !image.isRecycled()) {
            LogWrapper.e(TAG, "图片回收中...");
            image.recycle();
        }
    }

    /**
     * 只管压缩大小，不管图片失真
     *
     * @param imagePath
     * @param kb
     * @return
     */
    public static Bitmap compressImage(String imagePath, int kb) {
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        return compressImage(image, kb);
    }

    /**
     * 只管压缩大小，不管图片失真
     *
     * @param image
     * @param kb
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int kb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > kb) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
            // 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap compressImageFromFile(String srcPath, int width,
                                               int height) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // float hh = 800f;//
        // float ww = 480f;//
        int be = 1;
        if (w > h && w > width) {
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    public static Bitmap getBitmapByUri(Context context, Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        try {
            // 使用ContentProvider通过URI获取原始图片
            Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, uri);
            if (photo != null) {
                // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                Bitmap smallBitmap = zoomBitmap(photo,
                        (int) photo.getWidth() / 2, (int) photo.getHeight() / 2);
                // 释放原始图片占用的内存，防止out of memory异常发生
                photo.recycle();
                return smallBitmap;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bitMap
     * @param maxSize 图片允许最大空间 单位：KB
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitMap, int maxSize) {
        // 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomBitmap(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bgimage, double newWidth,
                                    double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap    要放大的图片
     * @param dstWidth  目标宽
     * @param dstHeight 目标高
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int dstWidth, int dstHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scale = 1f;
        if (width > dstWidth || height > dstHeight) {
            float scaleWidht = ((float) dstWidth / width);
            float scaleHeight = ((float) dstHeight / height);
            scale = scaleWidht < scaleHeight ? scaleWidht : scaleHeight;
        }
        LogWrapper.e(TAG, "scake===" + scale);
        matrix.postScale(scale, scale);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newbmp;
    }

    public static Bitmap zoomBitmapToMaxSize(Bitmap bitmap, int maxWidth,
                                             int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scale = 1f;
        if (width > maxWidth || height > maxHeight) {
            if (width > maxWidth && height <= maxHeight) {
                scale = (float) maxWidth / width;
                LogWrapper.e(TAG, "宽度超大");
            }
            if (height > maxHeight && width <= maxWidth) {
                scale = (float) maxHeight / height;
                LogWrapper.e(TAG, "高度超大");
            }
            if (width > maxWidth && height > maxHeight) {
                float scaleX = (float) maxWidth / width;
                LogWrapper.e(TAG, "scaleX===" + scaleX);
                float scaleY = (float) maxHeight / height;
                LogWrapper.e(TAG, "scaleY===" + scaleY);
                scale = scaleX < scaleY ? scaleX : scaleY;
                LogWrapper.e(TAG, "宽度和高度超大");
            }
        }
        LogWrapper.e(TAG, "scale===" + scale);
        matrix.postScale(scale, scale);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        if (bitmap != null && bitmap != newbmp && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return newbmp;
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 将文件生成位图
     *
     * @param path
     * @return
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static BitmapDrawable getImageDrawable(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);// 生成位图
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        return bd;
    }

    /**
     * 获取相机 相册path Uri 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context,
                                                final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null,
                    null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor
                            .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 将头像中的“|”和“%7C”去除掉，只保留域名及最后一个分隔符以后的头像地址
     *
     * @param avatar 头像的URL
     * @return 截取后的第一个URL
     */
    public static String formatURL(String avatar) {
        if (avatar.contains("|")) {
            String domain = avatar.substring(0, avatar.indexOf("/upload"));
            LogWrapper.d(TAG, "Avatar = " + avatar);
            avatar = domain + avatar.substring(avatar.lastIndexOf('|') + 1);
            LogWrapper.d(TAG, "Update avatar location to: " + avatar);
        }
        if (avatar.contains("%7C")) {
            String domain = avatar.substring(0, avatar.indexOf("/upload"));
            LogWrapper.d(TAG, "Avatar = " + avatar);
            avatar = domain + avatar.substring(avatar.lastIndexOf("%7C") + 4);
            LogWrapper.d(TAG, "Update avatar location to: " + avatar);
        }
        return avatar;
    }

}
