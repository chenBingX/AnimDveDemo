package com.example.chenbing.animdvedemo;

import android.os.Handler;
import android.test.InstrumentationTestCase;
import android.util.Log;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.example.chenbing.animdvedemo.API.APIClient;
import com.example.chenbing.animdvedemo.API.JavaBean.Articles;
import com.example.chenbing.animdvedemo.Utils.ReflectUtils;
import com.example.chenbing.animdvedemo.Views.MainActivity;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/12
 * Notes:
 */
public class MainActivityTest extends TestCase {
  MainActivity mainActivity;


  @Before
  public void setUp() throws Exception {
    mainActivity = new MainActivity();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    System.out.println("________");
  }

  @Test
  public void testInitData() throws Throwable {
    final CountDownLatch latch = new CountDownLatch(1);
    APIClient.getArticles().subscribe(new Action1<Articles>() {
      @Override
      public void call(Articles articles) {
        System.out.println(articles.toString());
        latch.countDown();
      }
    });
    latch.await();
  }

  @Test
  public void testInitViewData() throws Exception {
    ReflectUtils.invokeMethod(MainActivity.class, "initView");
    assertEquals(null, ReflectUtils.getVariable(MainActivity.class, "viewWrapper"));

  }
}
