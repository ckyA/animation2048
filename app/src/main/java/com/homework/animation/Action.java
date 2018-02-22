package com.homework.animation;

import java.util.LinkedList;

/**
 * 处理用户动作相关的操作，与游戏相关算法
 */

public class Action {
    public static final int up = 1;
    public static final int down = 2;
    public static final int left = 3;
    public static final int right = 4;
    private int[][] num;
    private int[][] animation;
    private boolean isRunning;
    private boolean isSlip = false;

    public Action(int[][] num) {
        this.num = num;
        isRunning = true;
        animation = new int[4][4];
    }

    //先把0都挪到前面
    private static int[] moveZero(int[] input) {
        LinkedList<Integer> queue = new LinkedList();
        for (int i = 0; i < 4; i++) {
            if (input[i] != 0) queue.offer(input[i]);
        }
        int[] res = new int[4];
        int size = queue.size();
        for (int i = 4 - size; i < 4; i++) {
            res[i] = queue.poll();
        }
        return res;
    }

    private static boolean equal(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    public int[][] getNum() {
        return num;
    }

    public void up() {
        update(up, 0, (new int[]{num[3][0], num[2][0], num[1][0], num[0][0]}));
        update(up, 1, (new int[]{num[3][1], num[2][1], num[1][1], num[0][1]}));
        update(up, 2, (new int[]{num[3][2], num[2][2], num[1][2], num[0][2]}));
        update(up, 3, (new int[]{num[3][3], num[2][3], num[1][3], num[0][3]}));
        if (isSlip) createRandomGrid();
    }

    public void down() {
        update(down, 0, (new int[]{num[0][0], num[1][0], num[2][0], num[3][0]}));
        update(down, 1, (new int[]{num[0][1], num[1][1], num[2][1], num[3][1]}));
        update(down, 2, (new int[]{num[0][2], num[1][2], num[2][2], num[3][2]}));
        update(down, 3, (new int[]{num[0][3], num[1][3], num[2][3], num[3][3]}));
        if (isSlip) createRandomGrid();
    }

    public void left() {
        update(left, 0, (new int[]{num[0][3], num[0][2], num[0][1], num[0][0]}));
        update(left, 1, (new int[]{num[1][3], num[1][2], num[1][1], num[1][0]}));
        update(left, 2, (new int[]{num[2][3], num[2][2], num[2][1], num[2][0]}));
        update(left, 3, (new int[]{num[3][3], num[3][2], num[3][1], num[3][0]}));
        if (isSlip) createRandomGrid();
    }

    public void right() {
        update(right, 0, (new int[]{num[0][0], num[0][1], num[0][2], num[0][3]}));
        update(right, 1, (new int[]{num[1][0], num[1][1], num[1][2], num[1][3]}));
        update(right, 2, (new int[]{num[2][0], num[2][1], num[2][2], num[2][3]}));
        update(right, 3, (new int[]{num[3][0], num[3][1], num[3][2], num[3][3]}));
        if (isSlip) createRandomGrid();
    }

    //将slipOneLines返回的值更新给numOfGrid
    private void update(int dir, int num, int[] input) {
        int[] res = slipOneLines(input);
        Animation.getAnimations(input, res, num, dir);
        switch (dir) {
            case up:
                this.num[0][num] = res[3];
                this.num[1][num] = res[2];
                this.num[2][num] = res[1];
                this.num[3][num] = res[0];
                break;
            case down:
                this.num[0][num] = res[0];
                this.num[1][num] = res[1];
                this.num[2][num] = res[2];
                this.num[3][num] = res[3];
                break;
            case left:
                this.num[num][0] = res[3];
                this.num[num][1] = res[2];
                this.num[num][2] = res[1];
                this.num[num][3] = res[0];
                break;
            case right:
                this.num[num][0] = res[0];
                this.num[num][1] = res[1];
                this.num[num][2] = res[2];
                this.num[num][3] = res[3];
                break;
            default:
                break;
        }
    }

    /**
     * 滑动方向为从前到后，返回滑动后的数组,由于会出现某种特殊情况，
     * 所以该方法要连续调用两次才没有bug。
     *
     * @param input
     * @return
     */
    private int[] slipOneLine(int[] input) {
        int[] res = moveZero(input);
        //再把相同的数字相加
        for (int i = 3; i > 0; i--) {
            if (res[i] == res[i - 1]) {
                res[i - 1] = 0;
                res[i] *= 2;
                res = moveZero(res);
            }
        }
        if (!equal(input, res)) isSlip = true;//前后不相等则视为滑动

        return res;
    }

    private int[] slipOneLines(int[] input) {
        return slipOneLine(slipOneLine(input));
    }

    //如果false则游戏结束
    private void createRandomGrid() {
        isSlip = false;//重置
        int flag = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (num[i][j] == 0) flag++;
            }
        }
        if (flag == 0) {
            isRunning = false;
        } else {
            int x = (int) (Math.random() * 4);
            int y = (int) (Math.random() * 4);
            if (num[y][x] == 0) {
                if (Math.random() < 0.7) {
                    num[y][x] = 2;
                } else {
                    num[y][x] = 4;
                }
            } else {
                createRandomGrid();
            }
        }
    }


}
