package com.shawn.fate.calendar;

import com.shawn.fate.constance.DATA_TIMES;
import org.junit.Test;

public class DataTest {

    @Test
    public void testJieQi() {
        System.out.println("hello");
        long a = DATA_TIMES.SOLAR_TERMS_TIME[50][1];
        System.out.println(a);

        long b = DATA_TIMES.getSolarTermsValueAt(20, 0);
        System.out.println(b);

        int c = DATA_TIMES.getNongValueAt(20, 3);
        System.out.println(c);
    }
}
