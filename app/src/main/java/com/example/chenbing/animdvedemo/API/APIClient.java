package com.example.chenbing.animdvedemo.API;

import android.util.Config;
import android.util.TimeUtils;

import com.example.chenbing.animdvedemo.API.JavaBean.Articles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/13
 * Notes:
 */
public class APIClient {

  private static String BASE_URL = "http://m2.qiushibaike.com/";

  private static Retrofit getRetrofit() {
    // 创建OkHttpClient来处理网络访问
    final OkHttpClient client = new OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS) //设置超时
      .retryOnConnectionFailure(true) //设置请求失败时是否重新请求
      .build();

    // 创建Retrofit来调度网络请求
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL) // 设置基础Url
      .addConverterFactory(GsonConverterFactory.create()) //设置Json解析器
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //设置RxJava作为回调
      .client(client) //设置网络请求器
      .build();
    return retrofit;
  }

  private static final APIService API_SERVICE;

  static {
    API_SERVICE = getRetrofit().create(APIService.class); //创建顶层网络访问服务器
  }

  public static Observable<Articles> getArticles(){
    return API_SERVICE.getArticles().subscribeOn(Schedulers.io());
  }

}
