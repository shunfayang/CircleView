package com.yangshunfa.circleview.moose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by yangshunfa on 2017/5/5.
 * tips:
 */

public class BubbleView extends View {

    public static final String MOOSE = "moose";
    private int measuredHeight = 0;
    private int measuredWidth;
    private Paint mPaint;
    private CornerPathEffect effect;
    private Path path;
    private Thread animThread;
    private Circle circle;

    public BubbleView(Context context) {
        super(context);
        init(context);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void startAnim(){
        animThread.start();
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(5);
//        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.path = new Path();
        effect = new CornerPathEffect(100);
        circle = new Circle();
        circle.lastTime = System.currentTimeMillis();
        animThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if (circle.y <=50 || circle.x >= measuredWidth -1){
                            circle.x = measuredWidth/2;
                            circle.radian = 0;
                        }
                        circle.x +=1;
                        double radian = (circle.radian + 90/(measuredWidth / 2) )% 90;
                        double s = (circle.radian+(measuredWidth/360)) % 90;
                        circle.radian = s;
                        circle.x = circle.x % (measuredWidth /2) + (measuredWidth / 2);

//                        double radian = circle.radian * Math.PI / 180;// 弧度
                        double y = Math.tan(radian * Math.PI /180) * circle.x;

                        double intervalHeight = Math.abs(y) % measuredHeight;
                        int absY = (int) (measuredHeight - intervalHeight);
                        circle.y = absY;
                        Log.e(MOOSE, "x=" + circle.x + " y="+ circle.y + " intervalHeight=" + intervalHeight + "   radian=" + s);
                        Thread.sleep(60);
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private int alpha = 255;
    private int index = 1;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (measuredHeight <= 0) {
//            radian++;
            measuredHeight = getMeasuredHeight();
            measuredWidth = getMeasuredWidth();
//            alpha = 255;
        }
        mPaint.setPathEffect(effect);
//        int cy = measuredHeight-=10;

        drawCircle(canvas, circle);
//        canvas.drawCircle(measuredWidth/2, cy, 30, mPaint);
        canvas.save();
        canvas.restore();
//        postInvalidate();
    }

    /**
     * 画圆
     * @param canvas
     * @param circle
     */
    private void drawCircle(Canvas canvas, Circle circle) {
        // 1. 需要确定y
        // 2. y由时间和x值确定
        // 3. 上一个时间与当前时间差
        long currTime = System.currentTimeMillis();
        long intervalTime = currTime - circle.lastTime;
//        circle.radian++;

//        if (circle.y <= 0 ) {
//            circle.x = measuredWidth / 2;
//            circle.y = measuredHeight;
//        }
//        circle.x +=5;
//        double s = (circle.radian+(measuredWidth/360)) % 90;
//        circle.radian = s;
//        circle.x = circle.x % (measuredWidth /2) + (measuredWidth / 2);
//
//        double radian = circle.radian * Math.PI / 180;// 弧度
//        double tan = Math.tan(radian) * circle.x;
//
//        double intervalHeight = Math.abs(tan) % measuredHeight;
//        int absY = (int) (measuredHeight - intervalHeight);
////        circle.y = circle.y - ((int) (tan * circle.x) % circle.y);
////        circle.lastTime = currTime;
//        circle.y = absY;
//
//        Log.e(MOOSE, "x=" + circle.x + " y="+ circle.y + " intervalHeight=" + intervalHeight + "   radian=" + s);
        canvas.drawCircle(circle.x, circle.y, 30, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
        circle.x = measuredWidth / 2;
        circle.y = measuredHeight;
        Log.e(MOOSE, "height=" + measuredHeight + " width=" + measuredWidth);
    }
    
    private class Circle {
        int x = 0;
        int y = 0;
        long lastTime = 0;
        double radian = 0;
    }
}
