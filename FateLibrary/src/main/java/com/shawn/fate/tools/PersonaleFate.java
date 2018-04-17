package com.shawn.fate.tools;

import com.shawn.fate.model.GanZhi;
import com.shawn.fate.model.JieQi;
import com.shawn.fate.model.NongLi;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonaleFate {

    private GanZhi _yearGanZhi;
    private GanZhi _monthGanZhi;
    private GanZhi _dayGanZhi;
    private GanZhi _hourGanzhi;

    private JieQi _birthJieqi;

    public PersonaleFate(LocalDateTime birthTime) throws Exception {
        _birthJieqi = JieQi.of(birthTime);
        _yearGanZhi = GanZhi.ofYear(_birthJieqi.getSolarYear());
        _monthGanZhi = GanZhi.ofJieQi(_birthJieqi);
        _dayGanZhi = GanZhi.ofDate(birthTime.toLocalDate());
        _hourGanzhi = GanZhi.ofHour(birthTime);
    }

    public PersonaleFate(NongLi birthMonth, int birthHour) throws Exception {
        LocalDate birthday = birthMonth.getLocalDate();
        LocalDateTime birthTime = LocalDateTime.of(birthday.getYear(), birthday.getMonthValue(), birthday.getDayOfMonth(), birthHour, 1);
        this(birthTime);
    }
}
