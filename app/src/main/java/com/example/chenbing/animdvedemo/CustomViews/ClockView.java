package com.example.chenbing.animdvedemo.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.chenbing.animdvedemo.Utils.LogUtils;
import com.example.chenbing.animdvedemo.Utils.ThreadUtil;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/26
 * Notes:
 */
public class ClockView extends View{
    private Paint circlePaint;
    private Paint longLinePaint;
    private Paint shortLinePaint;
    private Paint pointerPaint;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private int radius;
    private float sPointer;
    private float mPointer;
    private float hPointer;
    private float mainLine;
    private float minorLine;
    private int space;
    private int offset;
    private float sDegrees;
    private float mDegrees;
    private float hDegrees;
    private boolean isRunThread = true;
    private boolean isRunClock = true;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
      super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ClockView(Context context) {
      super(context);
      initPaint(); //初始化画笔
    }

    public ClockView(Context context, AttributeSet attrs) {
      super(context, attrs);
      initPaint(); //初始化画笔

    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      initPaint(); //初始化画笔
    }

    private void initPaint() {
      //画圆圈的笔
      circlePaint = new Paint();
      circlePaint.setAntiAlias(true); //设置抗锯齿
      circlePaint.setColor(Color.parseColor("#7e7e81")); //设置颜色
      circlePaint.setStyle(Paint.Style.STROKE); //设置画笔为空心
      circlePaint.setStrokeWidth(1); //设置画笔宽度

      //画刻度线的笔
      longLinePaint = new Paint();
      longLinePaint.setAntiAlias(true);
      longLinePaint.setColor(Color.BLACK);

      //画刻度的笔
      shortLinePaint = new Paint();
      shortLinePaint.setAntiAlias(true);
      shortLinePaint.setColor(Color.BLACK);
      shortLinePaint.setTextSize(12); //设置刻度字体大小

      //画指针的笔
      pointerPaint = new Paint();
      pointerPaint.setAntiAlias(true);
      pointerPaint.setColor(Color.BLACK);
      pointerPaint.setStrokeWidth(2);

      //刻度与主刻度线的间距
      space = 12;
      //刻度与主刻度线的横向偏移量
      offset = 5;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      //获得view的尺寸
      width = getMeasuredWidth();
      height = getMeasuredHeight();

      //计算圆心位置，使圆心位于view中心
      centerX = width / 2; //圆心X = 宽/2
      centerY = height / 2; //圆心Y = 高/2

      //计算半径
      radius = (Math.min(width, height) - 10 * 2) / 2; //半径 = （最小尺寸 - 两个边距 ）/2

      //计算刻度线长度
      mainLine = (float) (radius * 0.1); //主刻度线长度 = 半径 * 0.1
      minorLine = (float) (mainLine * 0.5); //次刻度线的长度 = 主刻度线长度 * 0.5

      //计算秒针
      sPointer = (float) (radius * 0.92); //秒针 = 半径 * 0.92
      sDegrees = 0;

      //计算分针
      mPointer = (float) (sPointer * 0.85); //分针 = 秒针 * 0.7
      mDegrees = 0;

      //计算时针
      hPointer = (float) (mPointer * 0.7); //时针 = 分针 * 0.5
      hDegrees = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
      //绘制圆圈
      canvas.drawCircle(centerX, centerY, radius, circlePaint); //绘制圆形

      //绘制刻度
      for (int i = 0; i < 60; i++) {
        canvas.save();
        canvas.rotate(6 * i, centerX, centerY); //每格角度是6度
        if (i % 5 == 0) { //能被5整除的是主刻度
          longLinePaint.setAlpha(255);
          canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + mainLine, longLinePaint);
          if (i == 0) {
            canvas.drawText("" + 12, centerX - offset, centerY - radius + mainLine + space, shortLinePaint); //绘制刻度

          } else {
            canvas.drawText("" + i / 5, centerX - offset, centerY - radius + mainLine + space, shortLinePaint); //绘制刻度

          }
        } else { //次刻度
          longLinePaint.setAlpha(200);
          canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + minorLine, longLinePaint);
        }
        canvas.restore();

        if (isRunThread) {
          isRunThread = false;
          run();
        }


      }

      //绘制刻度线
      //绘制秒针
      drawS(canvas);
      //绘制分针
      drawM(canvas);
      //绘制时针
      drawH(canvas);

      super.onDraw(canvas);
    }

    /**
     * 绘制时针
     *
     * @param canvas
     */
    private void drawH(Canvas canvas) {
      pointerPaint.setColor(Color.BLACK);
      pointerPaint.setAlpha(255);
      canvas.save();
      canvas.rotate(hDegrees, centerX, centerY);
      canvas.translate(centerX, centerY);
      canvas.drawLine(0, 0, 0, -hPointer, pointerPaint);
      canvas.restore();

    }

    /**
     * 绘制分针
     *
     * @param canvas
     */
    private void drawM(Canvas canvas) {
      pointerPaint.setColor(Color.BLACK);
      pointerPaint.setAlpha(255);
      canvas.save();
      canvas.rotate(mDegrees, centerX, centerY);
      canvas.translate(centerX, centerY);
      canvas.drawLine(0, 0, 0, -mPointer, pointerPaint);
      canvas.restore();
    }

    /**
     * 绘制秒针
     *
     * @param canvas
     */
    private void drawS(Canvas canvas) {
      pointerPaint.setColor(Color.RED);
      pointerPaint.setAlpha(200);
      canvas.save();
      canvas.rotate(sDegrees, centerX, centerY);
      canvas.translate(centerX, centerY);
      canvas.drawLine(0, 0, 0, -sPointer, pointerPaint);
      canvas.restore();
    }

    private void run() {
      ThreadUtil.getThreadPool().run(()->{
        while (isRunClock) {
          try {
            Thread.sleep(1000); //一秒重绘一次
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          post(new Runnable() {
            @Override
            public void run() {
              sDegrees = sDegrees+6;
              mDegrees =mDegrees +(float) 0.1;
              hDegrees = hDegrees+(float) 0.0083333;
              invalidate();
            }
          });
          LogUtils.i("又过了1秒钟了...");
        }
      });
    }
}
