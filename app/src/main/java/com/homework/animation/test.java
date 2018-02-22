package com.homework.animation;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Admin on 2018/1/29.
 */

public class test extends View {
    private float process = 50f;
    private Paint mPaint;

    public test(Context context) {
        super(context);
    }

    public test(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public test(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setColor(R.color.colorAccent);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(process, process, (float) (0.8 * process), mPaint);
    }

    public void largen() {
        // 通过静态方法构建一个ValueAnimator对象
        // 设置数值集合
        ValueAnimator animator = ValueAnimator.ofFloat(50f, 500.0f, 0f, 50f);
        // 设置作用对象
        animator.setTarget(this);
        // 设置执行时间(1000ms)
        animator.setDuration(6000);
        // 添加动画更新监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取当前值
                process = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        // 开始动画
        animator.start();
    }
}
