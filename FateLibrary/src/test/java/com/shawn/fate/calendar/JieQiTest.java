package com.shawn.fate.calendar;

import com.shawn.fate.model.JieQi;
import org.junit.Test;

import java.time.LocalDateTime;

public class JieQiTest {

    @Test
    public void JieQiFunc() throws Exception {
        JieQi jieqi = JieQi.of(LocalDateTime.now());
        System.out.println(jieqi);

        JieQi jieqi2 = JieQi.of(LocalDateTime.of(2018, 4, 5, 2, 0, 0));
        System.out.println(jieqi2);

        System.out.println(jieqi2.getPreJieQi());
        System.out.println(jieqi2.getNextJieQi());
    }

    @Test
    public void TestPlus() throws Exception {
        JieQi jieQi = JieQi.of(LocalDateTime.now());

        for (int i=0; i<=13; i++) {
            JieQi newJieqi = jieQi.plusYearsAndMonth(2, i);
            System.out.println(newJieqi);
        }
    }
}
