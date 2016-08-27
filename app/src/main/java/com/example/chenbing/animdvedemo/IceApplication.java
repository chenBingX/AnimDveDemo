package com.example.chenbing.animdvedemo;

import com.example.chenbing.animdvedemo.Utils.LogUtils;
import com.example.chenbing.animdvedemo.Utils.ManifestUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/25
 * Notes:
 */
public class IceApplication extends Application {
  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;

    String appkey = "57bf375ee0f55ae3e20018a3";
    String channelId = ManifestUtils.getChannel(this);
    MobclickAgent.UMAnalyticsConfig config =
        new MobclickAgent.UMAnalyticsConfig(this, appkey, channelId);
    LogUtils.e(config.mChannelId);
    MobclickAgent.startWithConfigure(config);
  }

  public static Context getAppContext() {
    return context;
  }
}
