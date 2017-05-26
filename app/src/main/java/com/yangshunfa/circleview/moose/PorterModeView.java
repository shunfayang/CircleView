package com.yangshunfa.circleview.moose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by yangshunfa on 2017/5/26.
 * tips:
 */

public class PorterModeView extends View {

    private Paint mPaint;
    private int measuredWidth;
    private int measuredHeight;
    private Paint mPaint2;

    public PorterModeView(Context context) {
        super(context);
        init(context);
    }

    public PorterModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PorterModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
//        canvas.drawColor(Color.TRANSPARENT);
//        canvas.saveLayer(new RectF(0,0,measuredWidth, measuredHeight),null, Canvas.ALL_SAVE_FLAG);

        Rect r  =new Rect(10,10,measuredWidth, measuredHeight - 50);
//        canvas.drawRect(r, mPaint2);// dis 目标图
        // 设置xmode
        canvas.drawCircle(measuredWidth/3, measuredHeight/3,measuredHeight/3, mPaint2);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mPaint.setXfermode(xfermode);
        canvas.drawCircle(measuredWidth /2, measuredHeight /2, measuredHeight /2,  mPaint);
        mPaint.setXfermode(null);
        canvas.save();
        canvas.restore();
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(0);
        mPaint2 = new Paint();
        mPaint2.setStrokeWidth(5);
        mPaint2.setColor(Color.parseColor("#ffffff"));
        mPaint.setColor(Color.parseColor("#ff9209"));
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }
}
