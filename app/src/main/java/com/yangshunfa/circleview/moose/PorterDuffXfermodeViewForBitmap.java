package com.yangshunfa.circleview.moose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.yangshunfa.circleview.R;

/**
 * Created by yangshunfa on 2017/5/26.
 * tips:
 */

public class PorterDuffXfermodeViewForBitmap extends View {

    private Paint mPaint;
    private int measuredWidth;
    private int measuredHeight;
    private Paint mPaint2;
    private PorterDuffXfermode porterDuffXfermode;
    private Bitmap bitmapDis;
    private Bitmap bitmapSrc;

    public PorterDuffXfermodeViewForBitmap(Context context) {
        super(context);
        init(context);
    }

    public PorterDuffXfermodeViewForBitmap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PorterDuffXfermodeViewForBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
//
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),100,100,mPaint);
//        // 设置xmode
//        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
//        mPaint.setXfermode(xfermode);
//        canvas.drawCircle(measuredWidth /2, measuredHeight /2, measuredHeight /2,  mPaint);
//        mPaint.setXfermode(null);
//        canvas.save();
//        canvas.restore();
        drawAige(canvas);
    }

    /**
     * copy 自 <a href="http://blog.csdn.net/aigestudio/article/details/41316141">爱哥博客</a>
     * @param canvas
     */
    private void drawAige(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        /*
         * 将绘制操作保存到新的图层（更官方的说法应该是离屏缓存）我们将在1/3中学习到Canvas的全部用法这里就先follow me
         */
        int sc = canvas.saveLayer(0, 0, measuredWidth, measuredHeight, null, Canvas.ALL_SAVE_FLAG);

        // 先绘制dis目标图
        canvas.drawBitmap(bitmapDis, 0, 0, mPaint);
//        canvas.drawRect(new Rect(0,150,100, 300), mPaint);// 验证未交汇的部分画布是没有效果的
//        canvas.drawRect(new Rect(0,0,measuredWidth, measuredHeight), mPaint);

        // 设置混合模式
        mPaint.setXfermode(porterDuffXfermode);

        // 再绘制src源图
        canvas.drawBitmap(bitmapSrc, 0, 0, mPaint);

        // 还原混合模式
        mPaint.setXfermode(null);

        // 还原画布
        canvas.restoreToCount(sc);
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
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);

        bitmapDis = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle);
        bitmapSrc = BitmapFactory.decodeResource(context.getResources(), R.drawable.rectangle);
    }
}
