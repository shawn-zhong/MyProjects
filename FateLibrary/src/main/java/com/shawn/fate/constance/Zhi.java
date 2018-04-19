package com.shawn.fate.constance;

/**
 * 地址 枚举变量
 */
public enum  Zhi {

    ZI      (0, "子"),
    CHOU    (1, "丑"),
    YIN     (2, "寅"),
    MAO     (3, "卯"),
    CHEN    (4, "辰"),
    SI      (5, "巳"),
    WU      (6, "午"),
    WEI     (7, "未"),
    SHEN    (8, "申"),
    YOU     (9, "酉"),
    XU      (10, "戌"),
    HAI     (11, "亥");

    private int _intVal;
    private String _chineseVal;

    Zhi(int val, String chineseName) {
        _intVal = val;
        _chineseVal = chineseName;
    }

    public int getIntVal() {
        return _intVal;
    }

    public String getChineseVal() {
        return _chineseVal;
    }
}
