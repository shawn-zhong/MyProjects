package com.shawn.fate.calendar;


import com.shawn.fate.model.NongLi;
import org.junit.Test;
import java.time.LocalDate;

public class CalendarBaseTest {

    @Test
    public void get() {
        LocalDate date = LocalDate.now();
        int jdn = CalendarBase.localDateToJdn(date);
        System.out.println("date of jdn is : " + jdn);

        LocalDate newdate = CalendarBase.jdnToLocalDate(jdn);
        System.out.println(newdate);

        NongLi month = new NongLi();
    }
}
