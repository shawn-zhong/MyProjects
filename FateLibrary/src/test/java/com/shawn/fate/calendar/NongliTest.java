package com.shawn.fate.calendar;

import com.shawn.fate.model.NongLi;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NongliTest {

    @Test
    public void NongliTest(){

        try {
            // testcase - 01
            NongLi item = NongLi.of(LocalDateTime.of(2017,7,11, 15, 30, 10));
            System.out.println(item);

            Assert.assertEquals(item.getNongliYear(), 2017);
            Assert.assertEquals(item.getNongliMonth(), 6);
            Assert.assertEquals(item.getIsLeapMonth(), false);
            Assert.assertEquals(item.getDayofMonth(), 18);

            NongLi item2 = NongLi.of(2017, 6, false, 18, 15, 30, 10);
            System.out.println(item2.getLocalDateTime());
            Assert.assertEquals(item.getLocalDateTime(), item2.getLocalDateTime());


            // testcase - 02
            item = NongLi.of(LocalDateTime.of(2017,8,11, 23, 59, 59));
            System.out.println(item);

            Assert.assertEquals(item.getNongliYear(), 2017);
            Assert.assertEquals(item.getNongliMonth(), 6);
            Assert.assertEquals(item.getIsLeapMonth(), true);
            Assert.assertEquals(item.getDayofMonth(), 20);

            item2 = NongLi.of(2017, 6, true, 20, 23, 59, 59);
            System.out.println(item2.getLocalDateTime());
            Assert.assertEquals(item.getLocalDateTime(), item2.getLocalDateTime());

        }catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
