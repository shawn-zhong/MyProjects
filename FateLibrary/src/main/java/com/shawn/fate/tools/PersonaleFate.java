package com.shawn.fate.tools;

import com.shawn.fate.model.GanZhi;
import com.shawn.fate.model.JieQi;
import com.shawn.fate.model.NongLi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PersonaleFate {

    private GanZhi _yearGanZhi;     // 年柱
    private GanZhi _monthGanZhi;    // 月柱
    private GanZhi _dayGanZhi;      // 日柱
    private GanZhi _hourGanzhi;     // 时柱

    private JieQi _birthJieqi;          // 出生时间所在的节气（之后）
    private LocalDateTime _birthTime;   // 出生具体时间

    private String desc1;           // 生于xx（节气）后/前 xx 天
    private String desc2;           // 大运于xx年xx月后上运
    private String desc3;           // 妹逢xx年xx后xx天交托

    private void init(LocalDateTime birthTime) throws Exception{
        _birthJieqi = JieQi.of(birthTime);
        _birthTime = birthTime;

        _yearGanZhi = GanZhi.ofYear(_birthJieqi.getSolarYear());
        _monthGanZhi = GanZhi.ofJieQi(_birthJieqi);
        _dayGanZhi = GanZhi.ofDate(birthTime.toLocalDate());
        _hourGanzhi = GanZhi.ofHour(birthTime);


    }

    public PersonaleFate(LocalDateTime birthTime) throws Exception {
        init(birthTime);
    }

    public PersonaleFate(NongLi birthMonth, int birthHour) throws Exception {
        LocalDate birthday = birthMonth.getLocalDate();
        LocalDateTime birthTime = LocalDateTime.of(birthday.getYear(), birthday.getMonthValue(), birthday.getDayOfMonth(), birthHour, 1);
        init(birthTime);
    }


    private void generateDesc01() throws Exception {
        JieQi birthAfter = _birthJieqi;
        JieQi birthBefore = birthAfter.getNextJieQi();

        long hoursAfter = ChronoUnit.HOURS.between(birthAfter.getTransitTime(), _birthTime);
        long hoursBefore = ChronoUnit.HOURS.between(_birthTime, birthBefore.getTransitTime());

        if (hoursBefore > 0 && hoursBefore < 3 * 24) {  // 关于早子时判断 ＊＊＊

        }

    }

}
