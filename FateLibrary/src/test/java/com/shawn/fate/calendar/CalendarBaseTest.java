package com.shawn.fate.calendar;


import com.shawn.fate.model.JieQi;
import com.shawn.fate.model.NongLi;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalendarBaseTest {

    @Test
    public void get() {
        LocalDate date = LocalDate.now();
        int jdn = TimeTool.localDateToJdn(date);
        System.out.println("date of jdn is : " + jdn);

        LocalDate newdate = TimeTool.jdnToLocalDate(jdn);
        System.out.println(newdate);

        NongLi month = new NongLi();
    }

    @Test
    public void daysBetween() {
        LocalDateTime time1 = LocalDateTime.of(2018, 4, 15, 23, 59, 0 );
        LocalDateTime time2 = LocalDateTime.of(2018, 4, 16, 0, 59, 0 );
        long days = ChronoUnit.DAYS.between(time1, time2);
        System.out.println(days);

        LocalDate date1 = time1.toLocalDate();
        LocalDate date2 = time2.toLocalDate();
        days = ChronoUnit.DAYS.between(date1, date2);
        System.out.println(days);
    }

    @Test
    public void JieQi() throws Exception {

        for (int y=2000; y<=2019; y++) {
            for (int i = 1; i < 12; i++) {
                long hours = ChronoUnit.HOURS.between(JieQi.of(y, i).getTransitTime(), JieQi.of(y, i + 1).getTransitTime());
                System.out.print("year:" + y + " month:" + i + " has days=" + hours / 24 + " hours=" + hours % 24 + " total hours="+hours+ " hour represent days: " + 365.0*10/hours + " max delta = " + (365.0*10/hours - 5)*30*24);
                System.out.println("");
            }

            long hours = ChronoUnit.HOURS.between(JieQi.of(y, 12).getTransitTime(), JieQi.of(y + 1, 1).getTransitTime());
            System.out.print("year:" + y + " month:12" + " has days=" + hours / 24 + " hours=" + hours % 24 + " total hours="+hours+ " hour represent days: " + 365.0*10/hours + " max delta = " + (365.0*10/hours - 5)*30*24);
            System.out.println("");
        }

        System.out.println("");
    }
}
