package com.shawn.fate.model;

import com.shawn.fate.calendar.TimeTool;
import com.shawn.fate.constance.Gan;
import com.shawn.fate.constance.Zhi;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GanZhi {

    private Gan _gan;       // 天干
    private Zhi _zhi;       // 地支
    private int _ganzhi;    // 天干地址


    public Gan getGan() {
        return _gan;
    }

    public Zhi getZhi() {
        return _zhi;
    }

    /**
     * 根据干支序号（0.。59）得到干支结构
     * @param ganzhi   干支序号： 0...59
     * @throws Exception
     */
    private GanZhi(int ganzhi) throws Exception {
        if (ganzhi < 0 || ganzhi >= 60)
            throw new InvalidParameterException();

        _gan = Gan.values()[ganzhi % 10];
        _zhi = Zhi.values()[ganzhi % 12];
        _ganzhi = ganzhi;
    }

    /**
     * 根据天干（0.。9）和地址序号（0.。11）得到干支结构
     * @param gan
     * @param zhi
     * @throws Exception
     */
    private GanZhi(int gan, int zhi) throws Exception {
        if (gan < 0 || gan > 9 || zhi < 0 || zhi > 11)
            throw new InvalidParameterException();

        _gan = Gan.values()[gan];
        _zhi = Zhi.values()[zhi];

        int val = gan*6 - zhi*5;
        _ganzhi = val < 0 ? val+60 : val;
    }

    /**
     * 计算某一年的天干地支（以交立春为一年）
     * @param year
     * @return
     */
    public static GanZhi ofYear(int year) {
        try {
            return new GanZhi((year + 4736) % 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算某一天的干支
     * @param date
     * @return
     */
    public static GanZhi ofDate(LocalDate date) {

        try {
            int y = date.getYear();
            int m = date.getMonthValue();
            int d = date.getDayOfMonth();

            int jdn = TimeTool.localDateToJdn(date);
            return new GanZhi((jdn + 49) % 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 计算某一时辰的干支
     * @param time
     * @return
     */
    public static GanZhi ofHour(LocalDateTime time) {

        try {
            GanZhi dateGanZhi = GanZhi.ofDate(LocalDate.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth()));
            return ofHour(dateGanZhi.getGan(), time.getHour());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 计算某一时辰的干支
     * @param dayGan
     * @param hour
     * @return
     */
    public static GanZhi ofHour(Gan dayGan, int hour) {

        try {
            int startGan = 2 * (dayGan.getIntVal() % 5) % 10;         // 早子时的天干序号
            int zhi = (hour + 1) / 2;     // 包含早子時晚子時

            int gan = (startGan + zhi) % 10;
            zhi %= 12;

            return new GanZhi(gan, zhi);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /***
     * 计算某个节的干支
     * @param jieQi
     * @return
     */
    public static GanZhi ofJieQi(JieQi jieQi) {

        try {
            int startGan = ((jieQi.getSolarYear() + 6) % 5 + 1) * 2 % 10;        // 寅月的天干序号
            int zhi = jieQi.getSolarMonth()+1;
            int gan = (startGan + zhi - 2) % 10;
            zhi %= 12;

            return new GanZhi(gan, zhi);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String toString() {
        return "GanZhi{" +
                "_gan=" + _gan.getChineseVal() +
                ", _zhi=" + _zhi.getChineseVal() +
                ", _ganzhi=" + _ganzhi +
                '}';
    }
}
