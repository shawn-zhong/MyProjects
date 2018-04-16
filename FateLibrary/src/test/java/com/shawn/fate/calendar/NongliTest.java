package com.shawn.fate.calendar;

import com.shawn.fate.model.NongLi;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class NongliTest {

    @Test
    public void NongliTest(){

        try {
            // testcase - 01
            NongLi item = NongLi.of(LocalDate.of(2017,7,11));
            System.out.println(item);

            Assert.assertEquals(item.getNongliYear(), 2017);
            Assert.assertEquals(item.getNongliMonth(), 6);
            Assert.assertEquals(item.getIsLeapMonth(), false);
            Assert.assertEquals(item.getDayofMonth(), 18);

            NongLi item2 = NongLi.of(2017, 6, false, 18);
            System.out.println(item2.getLocalDate());
            Assert.assertEquals(item.getLocalDate(), item2.getLocalDate());

            // testcase - 02
            item = NongLi.of(LocalDate.of(2017,8,11));
            System.out.println(item);

            Assert.assertEquals(item.getNongliYear(), 2017);
            Assert.assertEquals(item.getNongliMonth(), 6);
            Assert.assertEquals(item.getIsLeapMonth(), true);
            Assert.assertEquals(item.getDayofMonth(), 20);

            item2 = NongLi.of(2017, 6, true, 20);
            System.out.println(item2.getLocalDate());
            Assert.assertEquals(item.getLocalDate(), item2.getLocalDate());

        }catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
