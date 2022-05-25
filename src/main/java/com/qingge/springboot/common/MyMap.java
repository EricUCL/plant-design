package com.qingge.springboot.common;

import java.util.HashMap;

public class MyMap {
    public static HashMap<String, String> PLANT = new HashMap<String, String>() {
        {
            put("shanmu", "杉木");
            put("songshu", "松树");
            put("huamuke", "桦木科");
            put("yangliuke", "杨柳科");
            put("douke", "豆科");
            put("zhangshu", "樟树");
            put("mulanke", "木兰科");
        }
    };

    public static HashMap<Integer, String> FORMULA = new HashMap<Integer, String>() {
        {
            put(1, "生物量回归模型");
            put(2, "相对生长关系模型");
            put(3, "生物量转换因子连续函数法");
            put(4, "IPCC估算模型");
        }
    };
}
