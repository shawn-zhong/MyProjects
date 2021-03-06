package com.shawn.fate.model;

import com.shawn.fate.constance.DATA_TIMES;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 储存农历的数据结构
 */
public class NongLi implements Comparable{

    private int _year = 0;
    private int _month = 1;                  // 1..12
    private boolean _isLeapMonth = false;    // leap
    private int _dayOfMonth;                 // 1...31
    private int _totalDaysOfMonth;           // how many days of this _month

    private LocalDateTime _source;

    public NongLi() {

    }

    /**
     * 根据提供的农历信息得到一个农历日历
     * @param NongliYear
     * @param NongliMonth
     * @param isLeapMonth
     * @param NongliDayOfMonth
     * @return
     * @throws Exception
     */
    public static NongLi of(int NongliYear, int NongliMonth, boolean isLeapMonth, int NongliDayOfMonth, int hour, int minutes, int secs) throws Exception {
        return new NongLi(NongliYear, NongliMonth, isLeapMonth, NongliDayOfMonth, hour, minutes, secs);
    }

    /**
     * 根据提供的LocalDateTime信息得到一个农历日历
     * @param dateTime
     * @return
     * @throws Exception
     */
    public static NongLi of(LocalDateTime dateTime) throws Exception {
        return new NongLi(dateTime);
    }

    /**
     * 根据提供的农历信息初始化农历
     * @param NongliYear
     * @param NongliMonth
     * @param NongliDayOfMonth
     */
    private NongLi(int NongliYear, int NongliMonth, boolean isLeapMonth, int NongliDayOfMonth, int hour, int minute, int secs) throws Exception {

        // 找出所在行数. 表哥的每一行是每一农历年的信息
        int row = NongliYear - DATA_TIMES.MIN_YEAR;

        // 根据农历年月日的描述找出对应的列，信息正确的话一定能找到
        int daysBetween = 0;
        boolean found = false;
        for (int col=1; col<=13; col++) {
            int val = DATA_TIMES.getNongValueAt(row, col);

            int month = val % 100;
            boolean isLeap = (val % 1000 / 100) == 1;
            int daysOfMonth = val / 1000;

            if (isLeapMonth == isLeap && NongliMonth == month && NongliDayOfMonth <= (daysOfMonth+1)) {    // found
                daysBetween += NongliDayOfMonth - 1;
                found = true;
                break;
            }

            daysBetween += daysOfMonth;
        }

        if (!found) {
            throw new Exception("初始化农历失败, 提供的农历信息不正确");
        }

        // 计算LocalDateTime
        int indexDate = DATA_TIMES.getNongValueAt(row, 0);
        LocalDate indexLocalDate = LocalDate.of(indexDate/10000, indexDate%10000/100, indexDate%100);
        indexLocalDate = indexLocalDate.plusDays(daysBetween);

        _source = LocalDateTime.of(indexLocalDate.getYear(), indexLocalDate.getMonthValue(), indexLocalDate.getDayOfMonth(), hour, minute, secs);

    }

    /**
     * 根据LocalDate初始化农历
     * @param dateTime
     */
    private NongLi(LocalDateTime dateTime) throws Exception {

        int y = dateTime.getYear();
        int m = dateTime.getMonth().getValue(); // 1,2,3...
        int d = dateTime.getDayOfMonth();

        int greMark = y*10000 + m*100 + d;

        if (greMark < DATA_TIMES.getNongValueAt(0, 0) || y > DATA_TIMES.MAX_YEAR)
            throw new Exception("初始化农历失败，不在提供范围内（1600～2134）");

        // 找出具体在表中的哪一行
        int row = y - DATA_TIMES.MIN_YEAR;
        if (greMark < DATA_TIMES.getNongValueAt(row, 0))  // 注意col0的起始时间可能是新历的二月
            row -= 1;

        // 分别计算改行第一列值的jdn和输入值的jdn，以便计算差距
        int indexDate = DATA_TIMES.getNongValueAt(row, 0);
        LocalDate indexLocalDate = LocalDate.of(indexDate/10000, indexDate%10000/100, indexDate%100);
        int daysBetween  = (int)ChronoUnit.DAYS.between(indexLocalDate, dateTime.toLocalDate());
        assert (daysBetween >= 0);

        // 根据差距天数找出在该行的第几列
        for (int col=1; col<=13; col++) {
            int val = DATA_TIMES.getNongValueAt(row, col);
            int daysOfMonth = val / 1000;
            if (daysBetween+1 <= daysOfMonth) {
                _year = row + DATA_TIMES.MIN_YEAR;
                _month = val % 100;
                _isLeapMonth = (val % 1000 / 100) == 1;
                _dayOfMonth = daysBetween+1; // 每月从初一开始
                _totalDaysOfMonth = daysOfMonth;

                break;
            }
            daysBetween -= daysOfMonth;
        }

        _source = dateTime;
    }

    /**
     * 得到LocalDate信息
     * @return
     */
    public LocalDateTime getLocalDateTime() throws Exception {
        if (_source == null)
            throw new Exception("Failed: None of such value");

        return _source;
    }

    public String getNongliMonthNameInChinese() {
        throw new NotImplementedException();
    }

    /**
     * 获取农历的年份
     * @return
     */
    public int getNongliYear() {
        return _year;
    }

    /**
     * 获取农历的月份（1，2，3..）
     * @return
     */
    public int getNongliMonth() {
        return _month;
    }

    /**
     * 该月是否是闰月
     * @return
     */
    public boolean getIsLeapMonth() {
        return _isLeapMonth;
    }

    /**
     * 获取农历当月第几天
     * @return
     */
    public int getDayofMonth() {
        return _dayOfMonth;
    }

    /**
     * 获取该农历月共有多少天
     * @return
     */
    public int getTotalDaysOfMonth() {
        return _totalDaysOfMonth;
    }

    @Override
    public int compareTo(Object o) {
        NongLi next = (NongLi)o;
        if (this.getNongliMonth() == next.getNongliMonth())
            return this.getIsLeapMonth() ? 1 : -1;

        return this.getNongliMonth() - next.getNongliMonth();
    }

    @Override
    public String toString() {
        return "NongLi{" +
                "_year=" + _year +
                ", _month=" + _month +
                ", _isLeapMonth=" + _isLeapMonth +
                ", _dayOfMonth=" + _dayOfMonth +
                ", _totalDaysOfMonth=" + _totalDaysOfMonth +
                ", _source=" + _source +
                '}';
    }
}