package com.yangshunfa.circleview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yangshunfa.circleview.moose.BubbleView;
import com.yangshunfa.circleview.moose.BubbleView2;
import com.yangshunfa.circleview.moose.CircleView;

public class MainActivity extends AppCompatActivity {

    private CircleView view;
    private BubbleView2 viewById;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        view = (CircleView) findViewById(R.id.test);
//        viewById = (BubbleView2) findViewById(R.id.test2);
        btn = (Button) findViewById(R.id.btn);

//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                viewById.startAnim();
//            }
//        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation(v);
//                animator(v);
            }
        });
    }

    public void animator(final View view){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("moose", "animation:" + animation.toString());
//                view.setAnimation(animation);
                view.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
//        view.setAnimation();

    }

    public void rotation(View view){
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(view, "alpha", 1f, 0f,1f)
                .setDuration(3000);
//        view.setAlpha();
        animator.setRepeatMode(ObjectAnimator.RESTART);
//        animator.set/
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.w("moose", "value: " + animation.getAnimatedValue());
            }
        });
        ObjectAnimator animatorTranslation = ObjectAnimator.ofFloat(view, "translationX", 0f,view.getMeasuredWidth(), 0f);
        AnimatorSet set = new AnimatorSet();
        AnimatorSet.Builder play = set.play(animator).after(animatorTranslation);
//        animator.start();
        set.setDuration(3000);
        set.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        viewById.startAnim();
//        view.startAnim();

    }
}
