package com.homework.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;

public class Coordinate {

    public float X;
    public float Y;
    private int length;
    private boolean isX;
    private Coordinate cache;
    private GameView gameView;

    public Coordinate(int y, int x, int length) {
        X = x;
        Y = y;
        this.length = length;
    }

    //创建16个方块对应的坐标
    public static Coordinate[][] CreateCoordinate(int width) {
        Coordinate[][] res = new Coordinate[4][4];
        int part = width / 4;
        for (int i = 0; i < 4; i++) {   //j是x,i是y
            for (int j = 0; j < 4; j++) {
                res[i][j] = new Coordinate(part * i, part * j, part);
            }
        }
        return res;
    }

    public void startAnimation(boolean isX, float endPosition, GameView gameView) {
        // 通过静态方法构建一个ValueAnimator对象
        // 设置数值集合
        this.isX = isX;
        cache = this;
        this.gameView = gameView;
        ValueAnimator animator = ValueAnimator.ofFloat(0, endPosition);
        // 设置执行时间(1000ms)
        animator.setDuration(5000);
        // 添加动画更新监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Coordinate.this.isX) {
                    Coordinate.this.X = Coordinate.this.X + length * (Float) animation.getAnimatedValue();
                } else {
                    Coordinate.this.Y = Coordinate.this.Y + length * (Float) animation.getAnimatedValue();
                }
                Coordinate.this.gameView.invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Coordinate.this.X = Coordinate.this.cache.X;
                Coordinate.this.Y = Coordinate.this.cache.Y;
                Coordinate.this.gameView.invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        // 开始动画
        animator.start();
    }
}
