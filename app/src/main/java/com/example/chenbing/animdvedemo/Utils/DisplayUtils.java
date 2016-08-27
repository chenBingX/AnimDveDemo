package com.example.chenbing.animdvedemo.Utils;

import android.content.res.Resources;

/**
 * Project Name:AnimDveDemo
 * Author:Ice
 * Date:16/8/9
 * Notes:
 */
public class DisplayUtils {

  public static int dipToPx(int dip) {
    return (int) (dip * getDensity() + 0.5f);
  }

  public static int pxToDip(int px) {
    return (int) ((px - 0.5f) / getDensity());
  }

  public static float getDensity() {
    return Resources.getSystem().getDisplayMetrics().density;
  }
}
