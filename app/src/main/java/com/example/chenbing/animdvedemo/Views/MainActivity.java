package com.example.chenbing.animdvedemo.Views;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.chenbing.animdvedemo.AppConstant;
import com.example.chenbing.animdvedemo.R;
import com.example.chenbing.animdvedemo.API.APIClient;
import com.example.chenbing.animdvedemo.API.JavaBean.Articles;
import com.example.chenbing.animdvedemo.API.JavaBean.User;
import com.example.chenbing.animdvedemo.Animation.ViewWrapper.SizeViewWrapper;
import com.example.chenbing.animdvedemo.Animation.ViewWrapper.ViewWrapper;
import com.example.chenbing.animdvedemo.Utils.DisplayUtils;
import com.example.chenbing.animdvedemo.Utils.FileUtils;
import com.example.chenbing.animdvedemo.Utils.LogUtils;
import com.example.chenbing.animdvedemo.Utils.SharePreferencesUtils;
import com.umeng.analytics.AnalyticsConfig;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {

  @BindView(R.id.btn_play_anim)
  Button btnPlayAnim;
  @BindView(R.id.ll)
  LinearLayout ll;
  @BindView(R.id.v_stop_game_icon)
  View vStopGameIcon;
  @BindView(R.id.iv_display_image)
  ImageView ivDisplayImage;
  @BindView(R.id.btn_save_user)
  Button btnSaveUser;
  @BindView(R.id.btn_restore_user)
  Button btnRestoreUser;
  @BindView(R.id.tv_display_user_info)
  TextView tvDisplayUserInfo;

  private ViewWrapper viewWrapper;

  private int i = 0;
  private User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initView();
    initData();
    addListener();
  }

  private void initView() {
    viewWrapper = new SizeViewWrapper(ll);
  }

  public void initData() {
    requestDataFromNet();
  }

  private void requestDataFromNet() {
    APIClient.getArticles()
        // 在调用OnNext()之前先调用这个方法,这里的OnNext()是由Retrofit去调用的。
        .doOnNext(articles -> {
          articles.items.remove(0);
        })
        // 用上一个Observable返回的结果来创建一个新的Observable
        .flatMap(articles -> Observable.from(articles.items)) // 这里使用了from方法创建Observable
        // 指定接收者的工作线程
        .observeOn(AndroidSchedulers.mainThread())
        // 发射给订阅者
        .subscribe(
            // 创建订阅者
            new Subscriber<Articles.ItemsTestBean>() {
              @Override
              public void onCompleted() {

            }

              @Override
              public void onError(Throwable e) {

            }

              @Override
              public void onNext(Articles.ItemsTestBean itemsTestBean) {}
            });
  }

  private void addListener() {
    btnPlayAnim.setOnClickListener(v -> {
      int fromWidth = DisplayUtils.dipToPx(90);
      int toWidth = DisplayUtils.dipToPx(35);
      ObjectAnimator objectAnimator =
          ObjectAnimator.ofInt(viewWrapper, "width", fromWidth, toWidth);
      int duration = 1000 * 3;
      objectAnimator.setDuration(duration);
      objectAnimator.start();

      String channelId = AnalyticsConfig.getChannel(getApplicationContext());
      LogUtils.e("channelId = " + channelId);
    });

    btnSaveUser.setOnClickListener(v -> {
      if (user == null) {
        createUser();
      }
      // saveUser(user);
      saveUserToSP(user);
    });

    btnRestoreUser.setOnClickListener(v -> {
      // User restoreUser = restoreUser();
      User restoreUser = restoreUserFromSP();
      user = restoreUser;
      showUserInfo(restoreUser);
      scanAllObjectFile();
    });



    ivDisplayImage.setOnClickListener(v -> initData());
  }

  private void scanAllObjectFile() {
    LogUtils.e(FileUtils.scanDirectory(FileUtils.getAppObjectDir()));
  }

  private void createUser() {
    user = new User(1000000, "ice", "未知", 100, 8);
    LogUtils.e("preUserId = " + user.hashCode());
  }

  private void saveUser(@NonNull User user) {
    String savePath = FileUtils.getAppObjectDir() + "user" + user.getUserId() + ".obj";
    FileUtils.saveObject(savePath, user);
  }

  private User restoreUser() {
    User restoreUser = null;
    String path = FileUtils.getAppObjectDir() + "user" + 1000000 + ".obj";
    try {
      restoreUser = FileUtils.restoreObject(path, User.class);
    } catch (FileNotFoundException | NullPointerException e) {
      e.printStackTrace();
    }
    return restoreUser;
  }

  private void showUserInfo(@NonNull User user) {
    String content =
        String.format("userId:%d\nuserNikeName:%s\nuserRealName:%s\nlevel:%d\nvipLevel:%d",
            user.getUserId(), user.getUserNikeName(), user.getUserRealName(), user.getUserLevel(),
            user.getVipLevel());
    tvDisplayUserInfo.setText(content);
    LogUtils.e("fatUserId = " + user.hashCode());
  }

  private void saveUserToSP(User user) {
    try {
      SharePreferencesUtils.saveObject(this, AppConstant.USER_KEY, user);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private User restoreUserFromSP() {
    User user = null;
    try {
      user = SharePreferencesUtils.restoreObject(this, AppConstant.USER_KEY, User.class);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return user;
  }
}
