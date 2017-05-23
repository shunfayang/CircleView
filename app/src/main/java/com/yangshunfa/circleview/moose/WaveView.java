package com.yangshunfa.circleview.moose;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yangshunfa on 2017/5/23.
 * tips:
 */

public class WaveView extends View {

    private Paint mPaint;
    private int measuredHeight = 0;
    private int measuredWidth = 0;
    private float mFraction;

    public WaveView(Context context) {
        super(context);
        init(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (measuredHeight == 0 && measuredWidth == 0) {
            measuredHeight = getMeasuredHeight();
            measuredWidth = getMeasuredWidth();
            startAnim();
        } else {
            Path path = new Path();
            int i = 1;
            float x = 0;
            float y = 50;
            path.moveTo(0, 50);

            float amplitude = mFraction * 15;// 振幅
            float offset = mFraction * 10;
            for (;i<measuredWidth;i++){
                y = (float) ((float) (15) * Math.sin((i * 0.5) * Math.PI / 180 + offset) + mFraction * 100) ;
                path.lineTo(i, y);
//                path.quadTo(i, y,i+1,y);
            }
            path.lineTo(measuredWidth, measuredHeight);
            path.lineTo(0, measuredHeight);
            path.close();
            canvas.drawPath(path, mPaint);
        }
//        canvas.drawLine(0,measuredHeight,measuredWidth, measuredHeight, mPaint);
//        canvas.drawLine(0,0,200, 200, mPaint);
//        Path path = new Path();
//        path.moveTo(0, measuredHeight /2);
//        path.lineTo(measuredWidth /2, measuredHeight /2);
//        path.lineTo(measuredWidth /2, measuredHeight);
//        path.lineTo(0, measuredHeight);
        canvas.save();
        canvas.restore();
    }
    private void setPath(Path path){
//        int x = 0;
//        int y = 0;
//        //每次进来都把path重置一下
//        path.reset();
//        for (int i = 0; i < measuredWidth; i++) {
//            x = i;
//            y = (int) (a * Math.sin((i * w + fai) * Math.PI / 180) + k);
//            if (i == 0) {
//                //x=0的时候，即左上角的点，移动画笔于此
//                path.moveTo(x, y);
//            }
//            //用每个x求得每个y，用quadTo方法连接成一条贝塞尔曲线
//            path.quadTo(x, y, x + 1, y);
//        }
//        //最后连接到右下角底部
//        path.lineTo(measuredWidth, measuredHeight);
//        //连接到左下角底部
//        path.lineTo(0, measuredHeight);
//        //起点在左上角，这个时候可以封闭路径了，封闭。
//        path.close();
    }
    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new SinEvaluator(), 0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(2000);
        animator.start();
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
//        mPaint.

    }
    private class SinEvaluator implements TypeEvaluator{

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            int start = (int) startValue;
            int end = (int) endValue;
            int diffY = end - start;
            float x = start + diffY * fraction;
            Log.e("moose", "fraction=" + fraction);
            return fraction ;
        }
    }
}
