package com.yangshunfa.circleview.moose;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by yangshunfa on 2017/5/24.
 * tips:
 */

public class ArcView extends View {

    float arc = 0;

    private Paint mPaint;
    private int measuredHeight;
    private int measuredWidth;
    private Paint mPaint1;
    private Paint mPaint2;

    public ArcView(Context context) {
        super(context);
        init(context);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
//        canvas.drawArc(0,0,100,100,0,180,false,mPaint);
            float bottom;
            float right;
            float top;
            float left;
            if (measuredWidth > measuredHeight){
                top = getPaddingTop();
                bottom = measuredHeight - getPaddingBottom();
                left = (measuredWidth - measuredHeight - getPaddingRight() - getPaddingLeft() + getPaddingTop() + getPaddingBottom()) / 2 + getPaddingLeft();
                right = left + measuredHeight - getPaddingBottom() - getPaddingTop();
            } else{
                top = (measuredHeight - getPaddingTop()-getPaddingBottom()-measuredWidth + getPaddingRight() + getPaddingLeft())/2 + getPaddingTop();
                bottom = top + measuredWidth -getPaddingLeft() -getPaddingRight();
                left = getPaddingLeft();
                right = measuredWidth -getPaddingRight();
            }

            RectF oval = new RectF(left, top, right, bottom);
            if (arc >= -30f && arc <=0){
                canvas.drawArc(oval, -90f, arc, false, mPaint);
            }
            if (arc >= -180f && arc < -30f){
                canvas.drawArc(oval, -90f, -30f, false, mPaint);
                canvas.drawArc(oval, -120f, arc+30f, false, mPaint);
            }
            if (arc >= -360f && arc < -180f){
                canvas.drawArc(oval, -90f, -30f, false, mPaint);
                canvas.drawArc(oval, -120f, -150f, false, mPaint);
                canvas.drawArc(oval, -270f, arc+180f, false, mPaint);
            }
            canvas.save();
            canvas.restore();
        }
    }

    private void startAnim() {
        ObjectAnimator arc = ObjectAnimator.ofFloat(this, "arc", 0f, -360f);
        arc.setDuration(5000);
        arc.setInterpolator(new LinearInterpolator());
//        arc.setRepeatCount(ObjectAnimator.INFINITE);
        arc.setRepeatMode(ObjectAnimator.RESTART);
        arc.start();
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#000099"));
        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.parseColor("#009900"));
        mPaint1.setStrokeWidth(20);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setAntiAlias(true);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.parseColor("#990099"));
        mPaint2.setStrokeWidth(50);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setAntiAlias(true);
    }

    public float getArc() {
        return arc;
    }

    public void setArc(float arc) {
        this.arc = arc;
        invalidate();
    }
}
