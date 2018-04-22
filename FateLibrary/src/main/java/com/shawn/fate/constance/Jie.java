package com.shawn.fate.constance;

public enum Jie {

    LICHUN(1, "立春"),
    JINGZHI(2, "驚蟄"),
    QINGMING(3, "清明"),

    LIXIA (4, "立夏"),
    MANZHONG(5, "芒種"),
    XIAOSHU (6, "小暑"),

    LIQIU(7, "立秋"),
    BAILU(8, "白露"),
    HANLU(9, "寒露"),

    LIDONG(10, "立冬"),
    DAXUE(11, "大雪"),
    XIAOHAN(12, "小寒");


    private int _intVal;
    private String _chineseVal;

    Jie (int val, String chineseName) {
        _intVal = val;
        _chineseVal = chineseName;
    }

    public int getIntVal() {
        return _intVal;
    }

    public String getChineseVal() {
        return _chineseVal;
    }

    public static String getChineseName(int solarMonth) {
        return Jie.values()[solarMonth-1]._chineseVal;
    }
}
