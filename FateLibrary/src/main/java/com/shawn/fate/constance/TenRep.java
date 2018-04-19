package com.shawn.fate.constance;

/**
 * 十神 枚举变量
 */
public enum TenRep {

    RIZHU       (0, "日主"),
    BIJIAN      (1, "比肩"),
    JIECAI      (2, "劫财"),
    SHISHEN     (3, "食神"),
    SHANGGUAN   (4, "伤官"),
    PIANCAI     (5, "偏财"),
    ZHENGCAI    (6, "正财"),
    PIANGUAN    (7, "偏官"),
    ZHENGGUAN   (8, "正官"),
    PIANYIN     (9, "偏印"),
    ZHENGYIN    (10, "正印");

    private int _intVal;
    private String _chineseVal;

    TenRep(int val, String chineseName) {
        _intVal = val;
        _chineseVal = chineseName;
    }

    public int getIntVal(){
        return _intVal;
    }

    public String getChineseVal() {
        return _chineseVal;
    }
}
