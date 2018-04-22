package com.shawn.fate.calendar;

import com.shawn.fate.model.NongLi;
import com.shawn.fate.tools.PersonalFate;
import org.junit.Test;

import java.time.LocalDateTime;

public class PersonalTest {

    @Test
    public void TestPersonal() throws Exception {
        //PersonalFate person = new PersonalFate(LocalDateTime.of(1943, 3, 11, 10, 50, 0), true, false);
        PersonalFate person = new PersonalFate(NongLi.of(1943, 3, false, 11), 8, true, false);
    }
}
