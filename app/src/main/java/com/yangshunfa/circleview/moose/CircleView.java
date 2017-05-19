package com.yangshunfa.circleview.moose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by yangshunfa on 2017/4/27.
 * tips:
 */

public class CircleView extends View {

    private Paint mPaint;
    private GestureDetector mDetector;
    private float touchX = 0;
    private float touchY = 0;
    private float[] mPoints;
    private Bitmap bitmap;
    private Thread animThread;
    private int measuredHeight;
    private int measuredWidth;
    private float width;
    private int height;

    public CircleView(Context context) {
        super(context);
        init(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private int index = 0;

    private void init(Context context) {
        mPoints = new float[4 * 7];
        tempPoints = new float[4 * 7];
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        bitmap = Bitmap.createBitmap(200,200, Bitmap.Config.RGB_565);
        mDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                touchX = e.getX();
                touchY = e.getY();
                mPoints[0] = touchX;
                mPoints[1] = touchY;

                postInvalidate();
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        animThread = new Thread(anim);
    }

    private float[] tempPoints ;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measuredHeight = getMeasuredHeight() - 5;
        measuredWidth = getMeasuredWidth();
        width = measuredWidth / 8;
        height = measuredHeight / 8;

        canvas.drawColor(Color.GRAY);
        canvas.drawLine(5, 0,5, measuredHeight, mPaint);
        canvas.drawLine(0, measuredHeight, measuredWidth, measuredHeight -2, mPaint);
//        canvas.save();
//        canvas.restore();

//        mPoints = new float[]{
//                width * 0, measuredHeight,
//                width * 1, (measuredHeight / 8) * 7,
//
//                width * 1, (measuredHeight / 8) * 7,
//                width * 2, (measuredHeight / 10) * 9,
//
//                width * 2, (measuredHeight / 10) * 9,
//                width * 3, height * 6,
//
//                width * 3, height * 6,
//                width * 4, height * 5,
//
//                width * 4, height * 5,
//                width * 5, height * 5 - 10,
//
//                width * 5, height * 5 - 10,
//                width * 6, height * 5 + 10,
//
//                width * 6, height * 5 + 10,
//                width * 7, height * 4
//        };
//        float [] points = new float[]{width * 0, measuredHeight,
//                width * 1, (measuredHeight / 8) * 7,
//
//                width * 2, (measuredHeight / 10) * 9,
//                width * 3, height * 6,
//
//                width * 4, height * 5,
//                width * 5, height * 5 - 10,
//
//                width * 6, height * 5 + 10,
//                width * 7, height * 4,
//        };

        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        canvas.drawLines(mPoints,mPaint);
        canvas.save();
        canvas.restore();

//        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLUE);
//        for (int i= 0;i < points.length;i = i + 2){
//            Log.e("moose", "point=" + i);
//            canvas.drawCircle(points[i], points[ i+1 ], 8, mPaint);
//        }
//        canvas.drawPoints(points, mPaint);
//        canvas.save();
//        canvas.restore();

//        Toast.makeText(getContext(), "第：" + canvas.getSaveCount() + "次", 0).show();
//        canvas.drawPoint(touchX, touchY, mPaint);
//        canvas.drawLine(0,0, 100, 100,mPaint);
//        canvas.saveLayer(0, 0, 200, 200, mPaint, Canvas.ALL_SAVE_FLAG);

//        animThread.start();
    }

    public void setPoints(float points){

    }

    public void startAnim(){
        animThread.start();
    }


    private Random mRandom = new Random();
    private Runnable anim = new Runnable() {
        @Override
        public void run() {
            index = 0;
            mPoints = new float[28];
//            touchY = measuredHeight;
            while(index < 28) {
                Log.e("moose", "radian: " + index);
                try {
                    Thread.sleep(100);

                    mPoints[index] = touchX;
                    mPoints[++index] = touchY;

                    touchX = width + touchX;
                    touchY = mRandom.nextInt(8) * height;

                    mPoints[++index] = touchX;
                    mPoints[++index] = touchY;

                    index++;
//                    Log.e("moose", "for radian = " + radian);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        touchX = e.getX();
        touchY = e.getY();
//        mPoints[0] = touchX;
//        mPoints[1] = touchY;
        startAnim();
        invalidate();
        return super.onTouchEvent(e);
//        return mDetector.onTouchEvent(event);
    }
}
