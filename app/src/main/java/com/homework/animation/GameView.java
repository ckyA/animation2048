package com.homework.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    public static int[][] animLength = new int[4][4];
    public Context context;
    public int totalGrade;
    public Coordinate[][] coor;//坐标
    float startX = 0;
    float startY = 0;
    float endX = 0;
    float endY = 0;
    private int length;
    private int[][] numOfGrid = new int[4][4];
    private int padding;
    private Color2048 color = new Color2048();
    private Action action;
    private boolean[][] isAnimation = new boolean[4][4];
    private Animation animation;
    private OnNumChangeListener onNumChangeListener;

    public GameView(Context context) {
        super(context);
        InitRandomNum();
        this.context = context;
        animation = new Animation(context, this);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitRandomNum();
        this.context = context;
        animation = new Animation(context, this);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitRandomNum();
        this.context = context;
        animation = new Animation(context, this);
    }

    public static void clearAnimLength() {
        animLength = new int[4][4];
    }

    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //正方形
        if (widthSize < heightSize) {
            length = widthSize;
        } else {
            length = heightSize;
        }
        setMeasuredDimension(length, length);
        coor = Coordinate.CreateCoordinate(length);//创建坐标
        padding = length / 50;
    }

    //刚开始游戏的两个随机位置的’2’,1-8一个，9-16一个
    private void InitRandomNum() {
        numOfGrid[(int) (2 * Math.random())][(int) (4 * Math.random())] = 2;
        numOfGrid[(int) (2 * Math.random()) + 2][(int) (4 * Math.random())] = 2;
        action = new Action(numOfGrid);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.paint));
        //画背景 and 16个格子
        canvas.drawRect(0, 0, length, length, paint);
        paint.setColor(getResources().getColor(R.color.background));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                canvas.drawRoundRect(coor[i][j].X + padding, coor[i][j].Y + padding,
                        coor[i][j].X + length / 4 - padding, coor[i][j].Y + length / 4 - padding,
                        padding, padding, paint);
            }
        }
        drawGrid(canvas, paint);//根据 numOfGrid 画每一个格子
    }

    private void drawGrid(Canvas canvas, Paint paint) {
        int num = 0;
        for (int i = 0; i < 4; i++) {   //j是x,i是y
            for (int j = 0; j < 4; j++) {
                num = numOfGrid[i][j];
                if (num == 0) continue;
                paint.setColor(Color.parseColor(color.set.get(num)));
                canvas.drawRoundRect(coor[i][j].X + padding, coor[i][j].Y + padding,
                        coor[i][j].X + length / 4 - padding, coor[i][j].Y + length / 4 - padding,
                        padding, padding, paint);
                //画数字
                paint.setColor(Color.parseColor(color.set.get(0)));
                paint.setTextSize(length / 8);//这个8是试出来的。。
                canvas.drawText(num + "", coor[i][j].X + length / 11, coor[i][j].Y + length / 6, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //if(isAnimation()) return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();
                if (endX - startX > 0 && Math.abs(endX - startX) > Math.abs(endY - startY)) {
                    action.right();
                } else if (endX - startX <= 0 && Math.abs(endX - startX) > Math.abs(endY - startY)) {
                    action.left();
                } else if (endY - startY > 0 && Math.abs(endX - startX) <= Math.abs(endY - startY)) {
                    action.down();
                } else if (endY - startY < 0 && Math.abs(endX - startX) <= Math.abs(endY - startY)) {
                    action.up();
                }
                numOfGrid = action.getNum();
                setTotalGrade();
                invalidate();
                break;
        }
        return true;
    }

    private boolean isAnimation() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (isAnimation[i][j]) return true;
            }
        }
        return false;
    }

    private void setTotalGrade() {
        totalGrade = 0;//清零
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                totalGrade += numOfGrid[i][j];
            }
        }
        //回调
        if (onNumChangeListener != null) {
            onNumChangeListener.OnChange();
        }
    }

    interface OnNumChangeListener {
        void OnChange();
    }

}
