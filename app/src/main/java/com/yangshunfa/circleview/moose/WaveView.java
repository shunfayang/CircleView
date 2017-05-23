package com.yangshunfa.circleview.moose;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
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
import android.view.animation.LinearInterpolator;

import java.util.Random;

/**
 * Created by yangshunfa on 2017/5/23.
 * tips:
 */

public class WaveView extends View {

    private Paint mPaint;
    private int measuredHeight = 0;
    private int measuredWidth = 0;
    private float mFraction;
    private int scale;
    private Random mRandom;

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
            scale = new Random().nextInt(2) + 1;
        } else {
            Path path = new Path();
            int i = 1;
            float x = 0;
            float y = 50;
            path.moveTo(0, 50);

            float amplitude = mFraction * 20;// 振幅
            float offset = mFraction * 10 * (scale);
            float height = mFraction * 100;
            for (;i<measuredWidth;i++){
                y = (float) ((float) (amplitude) * Math.sin((i * 0.5) * Math.PI / 180 + offset) + 190) ;
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

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new SinEvaluator(), 0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        TimeInterpolator value = new LinearInterpolator();
        animator.setInterpolator(value);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(4000);
        animator.start();
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
        mPaint.setColor(Color.parseColor(mColor));
    }

    private String mColor = "#00aa90";
    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.parseColor(mColor));
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mRandom = new Random();
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
