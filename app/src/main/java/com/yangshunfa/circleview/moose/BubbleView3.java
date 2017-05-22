package com.yangshunfa.circleview.moose;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by yangshunfa on 2017/5/22.<br/><br/>
 * tips: 使用 <b>属性动画</b> 实现自底部向上的模仿水泡的动画
 */

public class BubbleView3 extends View {

    public static final String MOOSE = "moose";
    private Paint mPaint;
    private int measuredWidth = 0;
    private int measuredHeight = 0;
    private List<Bubble> mBubbles;
    private PointF mCurrentValue;
    private Random mRandom;
    private BubbleFactory bubbleFactory;
    private Map<Animator, Bubble> bubblesMap;

    public BubbleView3(Context context) {
        super(context);
        init(context);
    }

    public BubbleView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BubbleView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (measuredHeight == 0 && measuredWidth == 0) {
            measuredHeight = getMeasuredHeight();
            measuredWidth = getMeasuredWidth();
            startAnimation();
//            startAnimation2();
        } else {
            drawCircle(canvas);
//            drawCircle2(canvas);

            canvas.save();
            canvas.restore();
        }
    }

    private void drawCircle(Canvas canvas) {
        // draw
//        Iterator<Bubble> iterator = .iterator();
        Set<Map.Entry<Animator, Bubble>> entries = bubblesMap.entrySet();
        for (Map.Entry<Animator,Bubble> entry: entries){
            Bubble bubble = entry.getValue();
            canvas.drawCircle(bubble.x, bubble.y, 10, mPaint);
        }
    }
    private void drawCircle2(Canvas canvas) {
        // draw
        canvas.drawCircle(mCurrentValue.x, mCurrentValue.y, 10, mPaint);
    }

    private void startAnimation() {
        int count = 4;
        Bubble endBubble = bubbleFactory.blowBubble();
//        List<Bubble> bubbles = bubbleFactory.blowBubbles(count);
        endBubble.x = measuredWidth / 2;
        endBubble.y = 0;
        bubblesMap = new HashMap<>(count);
        int i = 0;
        for (;i<count;i++) {
            Bubble bubble = bubbleFactory.blowBubble();
            ValueAnimator animator = ValueAnimator.ofObject(new SinEvaluator(), bubble, endBubble);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Bubble animatedValue = (Bubble) animation.getAnimatedValue();
                    bubblesMap.put(animation, animatedValue);
                    Log.e(MOOSE, "x：" + animatedValue.x + " y=" + animatedValue.y);
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    animation.setDuration((mRandom.nextInt(5) + 5) * 1000);
                }
            });
            bubblesMap.put(animator, bubble);
            animator.setDuration((mRandom.nextInt(5) + 5) * 1000);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.start();
        }

    }
    private void startAnimation2() {
        PointF startPointF = new PointF(measuredWidth / 2, measuredHeight);
        PointF endPointF = new PointF(measuredWidth / 2, 0);
//        TypeEvaluator evaluator = new IntEvaluator();
        TypeEvaluator evaluator = new SinEvaluator2();
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startPointF, endPointF);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                mBubbles = (List<Bubble>) animation.getAnimatedValue();
                mCurrentValue = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                animation.setDuration((mRandom.nextInt(10) + 1) * 1000);
            }
        });
        animator.setDuration((mRandom.nextInt(10) + 1) * 1000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // paint
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);
        //
        mRandom = new Random();
        bubbleFactory = new BubbleFactory();
    }

    private class Bubble {
        int x = 0;
        int y = 0;
        double amplitude = 100;//振幅，默认100，
        int bottomUp = 0;//底部起来位置
        float scale = 1;// 缩放比例
        int inversalHeight = 0;
    }

    private class BubbleFactory {

        int MAX_BUBBLE_COUNT = 2;

        public List<BubbleView3.Bubble> blowBubbles(int count) {
            List<BubbleView3.Bubble> bubbles = new ArrayList<>(count);
            int i = 0;
            for (; i < count; i++) {
                bubbles.add(blowBubble());
            }
            return bubbles;
        }

        private BubbleView3.Bubble blowBubble() {
            BubbleView3.Bubble bubble = new BubbleView3.Bubble();
            bubble.y = measuredHeight + mRandom.nextInt(100) + 50;
//            bubble.y = measuredHeight + mRandom.nextInt(100) + 50;
            bubble.x = measuredWidth / 2;
            int i = measuredWidth / 3;
            bubble.bottomUp = i + mRandom.nextInt(i);
//            bubble.y = measuredHeight + (measuredHeight/4) * (mRandom.nextInt(2) + 1);
            return bubble;
        }
    }

    private class SinEvaluator implements TypeEvaluator {

        @Override
        public Bubble evaluate(float fraction, Object lists, Object endValue) {
            Bubble startValue = (Bubble) lists;
            Bubble endPoint = (Bubble) endValue;
            // 只需要y的差值
            float diffY = endPoint.y - startValue.y;
            float y = startValue.y + diffY * fraction;
            float x = (float) ((float) Math.sin(y * Math.PI / 180)  * startValue.amplitude + startValue.bottomUp);
//            startValue.x = (int) x;
//            startValue.y = (int) y;
            Bubble bubble = bubbleFactory.blowBubble();
            bubble.x = (int) x;
            bubble.y = (int) y;

            return bubble;
        }
    }
    private class SinEvaluator2 implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
//            Log.e(MOOSE, "startValueX:" + startValue.x + "startValueY:" + startValue.y + "endValueX:" + endValue.x + "endValueY:" + endValue.y + "  fraction:" + fraction);
            // 只需要y的差值
            float diffY = endValue.y - startValue.y;
            float y = startValue.y + diffY * fraction;
            float x = (float) Math.sin(y * Math.PI / 180) * 100 + measuredWidth / 2/* * bubble.amplitude + bubble.bottomUp*/;
//            float x = (float) Math.sin(y * Math.PI /180) * bubble.amplitude + bubble.bottomUp;
            Log.e(MOOSE, "x=" + x + " y=" + y);
            return new PointF(x, y);
        }
    }
}
