package com.shawn.fate.calendar;

import com.shawn.fate.constance.Gan;
import com.shawn.fate.model.GanZhi;
import com.shawn.fate.model.JieQi;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GanZhiTest {

    @Test
    public void testOfYear() {

        System.out.println(GanZhi.ofYear(1983));
        System.out.println(GanZhi.ofYear(2017));
        System.out.println(GanZhi.ofYear(2018));
    }

    @Test
    public void testOfDate() {

        System.out.println(GanZhi.ofDate(LocalDate.now()));
        System.out.println(GanZhi.ofDate(LocalDate.of(2020, 4, 18)));
    }

    @Test
    public void testOfHour() {

        for (int i=0; i<=10; i++) {
            System.out.println(LocalDateTime.now().plusDays(i));
            System.out.println(GanZhi.ofDate(LocalDate.now().plusDays(i)));
            System.out.println(GanZhi.ofHour(LocalDateTime.now().plusDays(i)));
            System.out.println(GanZhi.ofHour(LocalDateTime.of(2018, 4, 22, 0, 0, 1).plusDays(i)));
            System.out.println(GanZhi.ofHour(LocalDateTime.of(2018, 4, 22, 23, 0, 0).plusDays(i)));
        }

        System.out.println("-");


        System.out.println(GanZhi.ofHour(Gan.BING, 14));
    }

    @Test
    public void testOfJieqi() throws Exception {

        for (int k=0; k<12; k++) {
            for (int i = 1; i <= 12; i++) {
                System.out.println(GanZhi.ofJieQi(JieQi.of(2019 + k, i)));
            }
        }
    }
}
