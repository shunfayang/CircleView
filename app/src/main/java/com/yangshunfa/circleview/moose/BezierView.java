package com.yangshunfa.circleview.moose;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yangshunfa on 2017/6/6.
 * tips:
 */

public class BezierView extends View{

    private Paint mPaint;
    private int measuredHeight;
    private int measuredWidth;
    private float controlX = 0;
    private float controlY = 0;
    private Path path;
    private Paint assistPaint;

    public BezierView(Context context) {
        super(context);
        init(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        assistPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ff9920"));
        assistPaint.setStrokeWidth(2);
        assistPaint.setStyle(Paint.Style.STROKE);
        assistPaint.setColor(Color.parseColor("#ff000000"));
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
        path.reset();
        int startY = measuredHeight * 3 /4;
        int startX = 20;
        int endY = 50;
        int endX = measuredWidth /3;
//        int controlX = measuredWidth * 4/5;
//        int controlY = startY -20;
        path.moveTo(startX,startY);
        int x2 = measuredWidth - 350;
        int y2 = measuredHeight * 4 / 5;
        path.cubicTo(controlX, controlY, x2, y2, endX, endY);
//        path.quadTo(controlX, controlY, endX, endY);
        canvas.drawLine(startX,startY, controlX, controlY,assistPaint);
        canvas.drawLine(endX,endY, x2, y2,assistPaint);
        canvas.drawLine(x2,y2, controlX, controlY,assistPaint);
        canvas.drawPoint(controlX,controlY, assistPaint);
        canvas.drawPoint(x2,y2, assistPaint);
        canvas.drawPath(path, mPaint);
        canvas.save();
        canvas.restore();
    }

    private void drawAssist(Canvas canvas) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                controlY = event.getY();
//                event.();
                controlX = event.getX();
                invalidate();
                break;
        }
        return true;
    }
}
