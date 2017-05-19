package com.yangshunfa.circleview.moose;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yangshunfa on 2017/5/5.
 * tips:
 */

public class BubbleView2 extends View {

    public static final String MOOSE = "moose";
    private static int measuredHeight = 0;
    private static int measuredWidth = 0;
    private Paint mPaint;
    private CornerPathEffect effect;
    private Path path;
    private Thread animThread;
    private Circle circle;
    private List<Bubble> mBubbles;

    public BubbleView2(Context context) {
        super(context);
        init(context);
    }

    public BubbleView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BubbleView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void startAnim(){
        animThread.start();
    }

    private Random mRandom = new Random();
    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ffff5a00"));
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
                int size = mBubbles.size();

                for (int i=0;i<mBubbles.size();i++){
                    mBubbles.get(i).y += (i * (measuredHeight/4));
                    mBubbles.get(i).x = measuredWidth /2;
                }
                while(true) {
                    int i = 0;
                    for (; i< size;i++){
                        Bubble bubble = mBubbles.get(i);
                        bubble.y-=2;
                        if (bubble.y <=100){
                            bubble.y = measuredHeight - 1;
                            bubble.bottomUp = mRandom.nextInt(measuredWidth / 4) + measuredHeight / 4;// 底部出发点
                            bubble.amplitude = ((mRandom.nextInt(100) + 10) / 10) * 10;
                            bubble.scale = 1f;
                        }

                        float height = bubble.y % measuredHeight;
                        double radian = height * Math.PI /180;
                        double width = Math.sin(radian) * bubble.amplitude + bubble.bottomUp;

                        bubble.scale = (float)((20 / (float)measuredHeight) % 2) + bubble.scale;
                        if (i == 0){
                            Log.e(MOOSE, "scale=" + bubble.scale);
                        }
                        bubble.x = (int) width;
                        bubble.y = (int) height;
                    }

                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        });
        mBubbles = new BubbleFactory().blowBubbles(4);

    }
    private class Bubble {
        int x = 0;
        int y = 0;
        double amplitude = 10;//振幅，默认10，
        int bottomUp = 0;//底部起来位置
        float scale = 1;// 缩放比例
        int inversalHeight = 0;
    }
    private class BubbleFactory{

        int MAX_BUBBLE_COUNT = 4;

        public List<Bubble> blowBubbles(int count){
            List<Bubble> bubbles = new ArrayList<>(count);
            int i = 0;
            for (;i<count;i++){
                bubbles.add(blowBubble());
            }
            return bubbles;
        }

        private Bubble blowBubble() {
            Bubble bubble = new Bubble();
            bubble.y = measuredHeight;
            bubble.x = measuredWidth / 2;
//            bubble.y = measuredHeight + (measuredHeight/4) * (mRandom.nextInt(2) + 1);
            return bubble;
        }
    }
    private int cx = 0;
    private int cy = 0;
    private float scale = 1f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Matrix matrix = new Matrix();
        int i = 0;
        int size = mBubbles.size();
        for (;i<size;i++){
            Bubble bubble = mBubbles.get(i);
            matrix.setScale(bubble.scale, bubble.scale);
//            canvas.setMatrix(matrix);
            canvas.drawCircle(bubble.x, bubble.y,3 * bubble.scale,mPaint);
        }
        canvas.save();
        canvas.restore();
//        postInvalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
        circle.x = measuredWidth / 2;
        circle.y = measuredHeight;
        startAnim();
    }
    
    private class Circle {
        int x = 0;
        int y = 0;
        long lastTime = 0;
        double radian = 0;
    }
}
