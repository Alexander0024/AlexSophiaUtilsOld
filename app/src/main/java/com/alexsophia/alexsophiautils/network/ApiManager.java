package com.alexsophia.alexsophiautils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.alexsophia.alexsophiautils.utils.LogWrapper;
import com.alexsophia.alexsophiautils.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.retrofit_adapters.RxJavaCallAdapterFactory;
import retrofit2.retrofit_converters.GsonConverterFactory;

/**
 * Created by liuweiping on 2016-2-24. 网络请求管理类 本项目中使用Retrofit+RxJava请求网络数据
 * 在ApiInterface定义网络接口，在这里实现接口方法，包括参数的封装、对象的转换等
 */
public class ApiManager {
    /**
     * 网络请求的基础地址
     */
    private static String BASE_URL;
    /**
     * 创建Retrofit对象，并添加转换工厂（添加Gson解析）和回调工厂（添加RxJava转换）
     */
    private static Retrofit retrofit;
    /**
     * 网络请求接口
     */
    private static ApiInterface apiInterface;
    /**
     * 是否初始化BaseUrl
     */
    private static boolean isInit = false;

    public static void init(String baseUrl, final Context context,
                            boolean isNeedCache) {
        BASE_URL = baseUrl;
        OkHttpClient okHttpClient;
        if (isNeedCache) {
            // 缓存策略：首先，判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取
            // 缓存路径
            File cacheFile = new File(context.getExternalCacheDir(),
                    "response_cache");
            // 缓存大小
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 100Mb
            // 添加日志拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            LogWrapper.e("ApiManager", message);
                        }
                    });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 添加拦截器
            okHttpClient = new OkHttpClient.Builder().cache(cache)
                    .addInterceptor(interceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain)
                                throws IOException {
                            Request request = chain.request();
                            if (!isNetworkReachable(context)) {
                                request = request.newBuilder()
                                        .cacheControl(CacheControl.FORCE_CACHE)
                                        .build();
                                ToastUtil.show(context, "没网",
                                        Toast.LENGTH_SHORT);
                            }

                            Response response = chain.proceed(request);
                            if (isNetworkReachable(context)) {
                                int maxAge = 60 * 60; // read from cache for 60
                                // minute
                                response.newBuilder()
                                        .removeHeader("Pragma")
                                        .header("Cache-Control",
                                                "public, max-age=" + maxAge)
                                        .build();
                            } else {
                                int maxStale = 60 * 60 * 24 * 48; // tolerate
                                // 4-weeks
                                // stale
                                response.newBuilder()
                                        .removeHeader("Pragma")
                                        .header("Cache-Control",
                                                "public, only-if-cached, "
                                                        + "max-stale="
                                                        + maxStale).build();
                            }
                            return response;
                        }
                    }).build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        apiInterface = retrofit.create(ApiInterface.class);
        isInit = true;
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
