package com.example.chenbing.animdvedemo.API.JavaBean;

import java.io.Serializable;

import com.example.chenbing.animdvedemo.Utils.GsonUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/24
 * Notes:
 */
public class User implements Serializable, Cloneable {
  public static final long serialVersionUID = 1L;

  private int userId;
  private String userNikeName;
  private String userRealName;
  private int userLevel;
  private int vipLevel;

  public User() {

  }

  public User(int userId, String userNikeName, String userRealName, int userLevel, int vipLevel) {
    this.userId = userId;
    this.userNikeName = userNikeName;
    this.userRealName = userRealName;
    this.userLevel = userLevel;
    this.vipLevel = vipLevel;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserNikeName() {
    return userNikeName;
  }

  public void setUserNikeName(String userNikeName) {
    this.userNikeName = userNikeName;
  }

  public String getUserRealName() {
    return userRealName;
  }

  public void setUserRealName(String userRealName) {
    this.userRealName = userRealName;
  }

  public int getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(int userLevel) {
    this.userLevel = userLevel;
  }

  public int getVipLevel() {
    return vipLevel;
  }

  public void setVipLevel(int vipLevel) {
    this.vipLevel = vipLevel;
  }
}
