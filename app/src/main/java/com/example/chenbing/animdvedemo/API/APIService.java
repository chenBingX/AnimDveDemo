package com.example.chenbing.animdvedemo.API;

import com.example.chenbing.animdvedemo.API.JavaBean.Articles;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/13
 * Notes:
 */
public interface APIService {
  @GET("/article/list/text")  //表示get请求方式,""内是baseUrl后的内容
  Observable<Articles> getArticles(); // 结合RxJava,写请求
}
