package com.homework.animation;

import java.util.HashMap;

public class Color2048 {
    public HashMap<Integer, String> set = new HashMap();

    public Color2048() {
        set.put(0, "#dddddd");
        set.put(2, "#e2885d");
        set.put(4, "#23bf71");
        set.put(8, "#A066D3");
        set.put(16, "#ad1cb2");
        set.put(32, "#138b1b");
        set.put(64, "#E3170D");
        set.put(128, "#948839");
        set.put(256, "#FF7F50");
        set.put(512, "#7FFFD4");
        set.put(1024, "#03A89E");
        set.put(2048, "#FFD700");
    }
}
