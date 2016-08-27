package com.example.chenbing.animdvedemo.Animation.ViewWrapper;

import android.view.View;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/25
 * Notes:
 */
public class SizeViewWrapper implements ViewWrapper {
  View view;

  public SizeViewWrapper(View view) {
    this.view = view;
  }

  public int getWidth() {
    return view.getMeasuredWidth();
  }

  public void setWidth(int width) {
    view.getLayoutParams().width = width;
    view.requestLayout();
  }
}
