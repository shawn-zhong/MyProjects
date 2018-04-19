package com.shawn.fate.constance;

/**
 * 天干 枚举变量
 */
public enum Gan {

    JIA     (0, "甲"),
    YI      (1, "已"),
    BING    (2, "丙"),
    DING    (3, "丁"),
    WU      (4, "戊"),
    JI      (5, "己"),
    GENG    (6, "庚"),
    XIN     (7, "辛"),
    REN     (8, "壬"),
    GUI     (9, "癸");

    private int _intVal;
    private String _chineseVal;

    Gan (int val, String chineseName) {
        _intVal = val;
        _chineseVal = chineseName;
    }

    public int getIntVal() {
        return this._intVal;
    }

    public String getChineseVal() {
        return this._chineseVal;
    }
}
