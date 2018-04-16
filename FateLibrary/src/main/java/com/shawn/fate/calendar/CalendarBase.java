package com.shawn.fate.calendar;
import java.time.LocalDate;

public class CalendarBase {

    /*
     * Input parameters:
     *     calendar style (GREGORIAN)
     *     Year (must be > -4800). The year 1 BC must be given as 0, the
     *         year 2 BC must be given as -1, etc.
     *     Month (1..12)
     *     Day (1..31)
     * Returns:
     *     Julian Day Number
     */
    private static int dateToJdn(int year, int month, int day) {
    // only for GREGORIAN
        int a = (14-month)/12;
        int y = year+4800-a;
        int m = month + 12*a - 3;

        return day + (153*m+2)/5 + y*365 + y/4 - y/100 + y/400 - 32045;
    }

    public static int localDateToJdn(LocalDate date) {
        return dateToJdn(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    /*
     * Calculates the date for a given Julian Day Number.
     * Input parameter:
     *     calendar style (JULIAN or GREGORIAN)
     *     Julian Day Number
     * Output parameters:
     *     Address of year. The year 1 BC will be stored as 0, the year
     *         2 BC will be stored as -1, etc.
     *     Address of month (1..12)
     *     Address of day (1..31)
     *
     */
    public static LocalDate jdnToLocalDate(int jd) {
    // only for GrEGORIAN
        int b, c, d, e, m;

        int a = jd + 32044;
        b = (4*a+3)/146097;
        c = a - (b*146097)/4;

        d = (4*c+3)/1461;
        e = c - (1461*d)/4;
        m = (5*e+2)/153;

        int day = e - (153*m+2)/5 + 1;
        int month = month = m + 3 - 12*(m/10);
        int year = b*100 + d - 4800 + m/10;

        return LocalDate.of(year, month, day);
    }
}
