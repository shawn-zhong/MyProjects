package com.shawn.fate.model;

import com.shawn.fate.constance.DATA_CAL;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JieQi {
    private int _solarYear;                 // 节气年
    private int _solarMonth;                // 节气月
    private LocalDateTime _transitTime;     // 交接时刻

    /**
     *
     * @param solarYear
     * @param solarMonth 1,2,3...12
     * @throws Exception
     */
    private JieQi(int solarYear, int solarMonth) throws Exception {
        this._solarYear = solarYear;
        this._solarMonth = solarMonth;

        if (solarYear < DATA_CAL.MIN_YEAR || solarYear > DATA_CAL.MAX_YEAR)
            throw new Exception("给出的时间超出查询范围，支持1600～2134");

        if (solarMonth < 1 || solarMonth > 12)
            throw new IllegalArgumentException("solarMonth must between 1..12");

        int row = solarYear - DATA_CAL.MIN_YEAR;
        int col = solarMonth - 1;

        // 查找到交接时刻（到小时）
        int timeMark = DATA_CAL.getSolarTermsValueAt(row, col);
        int year = timeMark/1000000;
        int month = timeMark%1000000/10000;
        int day = timeMark%10000/100;
        int hour = timeMark%100;
        _transitTime = LocalDateTime.of(year, month, day, hour, 0, 0);
    }

    /**
     * 根据节气年月返回节气结构
     * @param solarYear  节气年
     * @param solarMonth 节气月 1,2,3...12
     * @return
     * @throws Exception
     */
    public static JieQi of(int solarYear, int solarMonth) throws Exception {
        return new JieQi(solarYear, solarMonth);
    }

    /**
     * 给出一个时间点，得到一个节气（年、月）以及和节气差距多少小时
     * @param dateTime  需要定位的时间点
     * @return
     * @throws Exception
     */
    public static JieQi of(LocalDateTime dateTime) throws Exception {
        int y = dateTime.getYear();
        int m = dateTime.getMonthValue();
        int d = dateTime.getDayOfMonth();
        int h = dateTime.getHour();

        int timeMark = y * 1000000 + m * 10000 + d * 100 + h;
        if (timeMark < DATA_CAL.getSolarTermsValueAt(0, 0) ||
                timeMark > DATA_CAL.getSolarTermsValueAt(DATA_CAL.TERMS_COUNT, 11)) {
            throw new Exception("给出的时间超出查询范围，支持1600020417～2135010603");
        }

        int row = y - DATA_CAL.MIN_YEAR;
        if (timeMark < DATA_CAL.getSolarTermsValueAt(row, 0))
            row -= 1;

        int solarMonth = 0;
        for (int i=0; i<12; i++) {
            int val = DATA_CAL.getSolarTermsValueAt(row, i);

            // 找到给定的时间位于哪一列（节气）中
            if (val >= timeMark) {  // 只支持到时辰：如果出生时辰和交接时辰一样的话，认为进入了新的节气
                solarMonth = (val == timeMark) ? i+1 : i;   // 月份从1开始
                break;
            }
        }

        return new JieQi(DATA_CAL.MIN_YEAR + row, solarMonth);
    }

    /**
     * 得出上一个节气
     * @return
     * @throws Exception
     */
    public JieQi getPreJieQi() throws Exception {

        int newSolarMonth = _solarMonth -1;
        int newSolarYear = _solarYear;

        if (newSolarMonth <= 0) {
            newSolarMonth = 12;
            newSolarYear -= 1;
        }

        return new JieQi(newSolarYear, newSolarMonth);
    }

    /**
     * 得出下一个节气
     * @return
     * @throws Exception
     */
    public JieQi getNextJieQi() throws Exception {
        int newSolarMonth = _solarMonth +1;
        int newSolarYear = _solarYear;

        if (newSolarMonth > 12) {
            newSolarMonth = 1;
            newSolarYear += 1;
        }

        return new JieQi(newSolarYear, newSolarMonth);
    }

    /**
     * 在此節氣基礎上加上n年n月得到新的節氣
     * @param years
     * @param months
     * @return
     * @throws Exception
     */
    public JieQi plusYearsAndMonth(int years, int months) throws Exception {

        int indexMonth = (_solarMonth - 1) + months;
        int newSolarYear = _solarYear + years + indexMonth / 12;
        int newSolarMonth = indexMonth % 12 + 1;

        return new JieQi(newSolarYear, newSolarMonth);
    }

    /**
     * 返回节气的交接时刻
     * @return
     */
    public LocalDateTime getTransitTime() {
        return _transitTime;
    }

    /**
     * 获取节气年
     * @return
     */
    public int getSolarYear() {
        return _solarYear;
    }

    /**
     * 获取节气顺序（1.立春 2.惊蛰..）
     * @return
     */
    public int getSolarMonth() {
        return _solarMonth;
    }

    @Override
    public String toString() {
        return "JieQi{" +
                "_solarYear=" + _solarYear +
                ", _solarMonth=" + _solarMonth +
                ", _transitTime=" + _transitTime +
                '}';
    }
}
