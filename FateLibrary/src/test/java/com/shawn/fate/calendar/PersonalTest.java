package com.shawn.fate.calendar;

import com.shawn.fate.model.NongLi;
import com.shawn.fate.tools.PersonalFate;
import org.junit.Test;

import java.time.LocalDateTime;

public class PersonalTest {

    @Test
    public void TestPersonal() throws Exception {

        PersonalFate person = new PersonalFate(LocalDateTime.of(1985, 9, 11, 5, 10, 0), true, false);
        PersonalFate person8 = new PersonalFate(LocalDateTime.of(1985, 9, 11, 6, 50, 0), true, true);
        PersonalFate person9 = new PersonalFate(NongLi.of(1943, 3, false, 11, 8, 0, 0), true, true);
    }
}
