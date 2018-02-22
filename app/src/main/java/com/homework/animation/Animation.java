package com.homework.animation;

import android.content.Context;
import android.util.Log;

/**
 * Created by Admin on 2018/2/6.
 */

public class Animation {
    public static int[][] animation = new int[4][4];
    private Context mContext;
    private GameView gameView;

    public Animation(Context mContext, GameView gameView) {
        this.mContext = mContext;
        this.gameView = gameView;
    }

    public static void reset() {
        animation = new int[4][4];
    }

    public static String getAnimation() {
        String res = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res += animation[i][j];
            }
            res += "\n";
        }
        return res;
    }

    public static void getAnimations(int[] input, int[] result, int num, int dir) {
        int start = howManyZero(input);
        int end = howManyZero(result);
        int[] res = new int[4];
        int diff = end - start;
        Log.i("ii", String.valueOf(start) + " " + diff);
        switch (diff) {
            case 0:
                int flag = 0;
                for (int i = 0; i < 4; i++) {
                    if (input[i] != 0) {
                        res[i] = end + flag - i;
                        flag++;
                    }
                }
                break;
            case 1:
                res = getAnimation1(input, result, start, end);
                break;
            case 2:
                res = getAnimation2(input, result);
                break;
            case 3:
                res = new int[]{3, 2, 1, 0};
                break;
        }

        switch (dir) {
            case Action.up:
                animation[0][num] = res[3];
                animation[1][num] = res[2];
                animation[2][num] = res[1];
                animation[3][num] = res[0];
                break;
            case Action.down:
                animation[0][num] = res[0];
                animation[1][num] = res[1];
                animation[2][num] = res[2];
                animation[3][num] = res[3];
                break;
            case Action.left:
                animation[num][0] = res[3];
                animation[num][1] = res[2];
                animation[num][2] = res[1];
                animation[num][3] = res[0];
                break;
            case Action.right:
                animation[num][0] = res[0];
                animation[num][1] = res[1];
                animation[num][2] = res[2];
                animation[num][3] = res[3];
                break;
            default:
                break;
        }
    }

    //when diff = 1;
    public static int[] getAnimation1(int[] input, int[] result, int start, int end) {
        int[] res = new int[4];

        if (start == 2) {
            for (int i = 0; i < 4; i++) {
                if (input[i] != 0) res[i] = 3 - i;
            }

        } else if (start == 1) {

            int zero_position = 0;
            boolean isFront = false;
            for (int i = 0; i < 4; i++) {
                if (input[i] == 0) zero_position = i;
                break;
            }
            for (int i = 0; i < 4; i++) {
                if (input[i] == result[3]) isFront = true;
            }
            if (isFront) {
                switch (zero_position) {
                    case 0:
                        res = new int[]{0, 1, 0, 0};
                        break;
                    case 1:
                        res = new int[]{2, 0, 0, 0};
                        break;
                    case 2:
                        res = new int[]{2, 1, 0, 0};
                        break;
                    case 3:
                        res = new int[]{2, 1, 1, 0};
                        break;
                }
            } else {
                switch (zero_position) {
                    case 0:
                        res = new int[]{0, 1, 1, 0};
                        break;
                    case 1:
                        res = new int[]{2, 0, 1, 0};
                        break;
                    case 2:
                        res = new int[]{2, 2, 0, 0};
                        break;
                    case 3:
                        res = new int[]{2, 2, 1, 0};
                        break;
                }
            }
        } else {

            int position = -1;
            for (int i = 3; i >= 0; i--) {
                if (input[i] != result[i]) {
                    position = i;
                    break;
                }
            }
            switch (position) {
                case 1:
                    res = new int[]{1, 0, 0, 0};
                    break;
                case 2:
                    res = new int[]{1, 1, 0, 0};
                    break;
                case 3:
                    res = new int[]{1, 1, 1, 0};
                    break;
            }

        }
        return res;
    }

    //when diff = 2;
    public static int[] getAnimation2(int[] input, int[] result) {
        int[] res = new int[4];
        int[] position = new int[2];
        boolean flag = false;
        if ((input[0] == input[1]) && (input[2] == input[3]))
            flag = true;
        if (flag) {
            res = new int[]{2, 1, 1, 0};
        } else if (input[0] == result[0]) {
            res = new int[]{2, 2, 1, 0};
        } else if (input[3] == result[3]) {
            res = new int[]{3, 2, 1, 0};
        }
        return res;
    }

    private static int howManyZero(int[] input) {
        int res = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 0) res++;
        }
        return res;
    }

    public void startAnimations(int dir) {
        switch (dir) {
            case Action.up:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        gameView.coor[i][j].startAnimation(false, -animation[i][j], gameView);
                    }
                }
                break;
            case Action.down:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        gameView.coor[i][j].startAnimation(false, animation[i][j], gameView);
                    }
                }
                break;
            case Action.left:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        gameView.coor[i][j].startAnimation(true, -animation[i][j], gameView);
                    }
                }
                break;
            case Action.right:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        gameView.coor[i][j].startAnimation(true, animation[i][j], gameView);
                    }
                }
                break;
        }
    }


}
