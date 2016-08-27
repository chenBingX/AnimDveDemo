package com.example.chenbing.animdvedemo.API.JavaBean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/13
 * Notes:
 */
public class Articles {

  public int count;
  public int err;
  public int total;
  public int page;
  public int refresh;

  public List<ItemsTestBean> items;

  public static Articles objectFromData(String str) {

    return new Gson().fromJson(str, Articles.class);
  }

  public static class ItemsTestBean {
    public String format;
    public Object image;
    public int published_at;
    public String tag;
    /**
     * avatar_updated_at : 1470492953
     * uid : 11177867
     * last_visited_at : 1378741917
     * created_at : 1378741917
     * state : bonded
     * last_device : android_2.6
     * role : n
     * login : 孟婆卖萌不卖汤、
     * id : 11177867
     * icon : 2016080614155363.JPEG
     */

    public UserTestBean user;
    public Object image_size;
    public int id;
    /**
     * down : -18
     * up : 652
     */

    public VotesTestBean votes;
    public int created_at;
    public String content;
    public String state;
    public int comments_count;
    public boolean allow_comment;
    public int share_count;

    public static ItemsTestBean objectFromData(String str) {

      return new Gson().fromJson(str, ItemsTestBean.class);
    }

    public static class UserTestBean {
      public int avatar_updated_at;
      public int uid;
      public int last_visited_at;
      public int created_at;
      public String state;
      public String last_device;
      public String role;
      public String login;
      public int id;
      public String icon;

      public static UserTestBean objectFromData(String str) {

        return new Gson().fromJson(str, UserTestBean.class);
      }
    }

    public static class VotesTestBean {
      public int down;
      public int up;

      public static VotesTestBean objectFromData(String str) {

        return new Gson().fromJson(str, VotesTestBean.class);
      }
    }
  }

  public String toString(){
    return new Gson().toJson(this);
  }
}
