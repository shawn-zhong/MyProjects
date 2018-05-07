package com.shawn.fate.tools;

import com.shawn.fate.calendar.TimeTool;
import com.shawn.fate.constance.Display;
import com.shawn.fate.constance.Gan;
import com.shawn.fate.constance.Jie;
import com.shawn.fate.model.GanZhi;
import com.shawn.fate.model.JieQi;
import com.shawn.fate.model.NongLi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PersonalFate {

    private boolean _isMale;        // 是否是男生
    private GanZhi _yearGanZhi;     // 年柱
    private GanZhi _monthGanZhi;    // 月柱
    private GanZhi _dayGanZhi;      // 日柱
    private GanZhi _hourGanzhi;     // 时柱

    private LocalDateTime _birthTime;   // 出生具体时间
    private JieQi _birthJieqi;          // 出生时间所在的节气（之后）
    private int _dayOfBirthJieqi;       // 出生在某個節氣後x天

    private JieQi _engageJieqi;         // 上運時間
    private int   _dayOfEngageJieqi;    // 上運在某個節氣後x天

    private String _desc1;           // 生于xx（节气）后/前 xx 天
    private String _desc2;           // 大运于xx年xx月xx天后上运
    private String _desc3;           // 每逢xx年xx后xx天交托

    private void init(LocalDateTime birthTime, boolean isMale, boolean goodArgorithm) throws Exception{

        System.out.println("initiating personal fate by parameters : " + birthTime.toString() + " isMale = " + isMale);

        _isMale = isMale;

        _birthJieqi = JieQi.of(birthTime);
        _birthTime = birthTime;

        _yearGanZhi = GanZhi.ofYear(_birthJieqi.getSolarYear());
        _monthGanZhi = GanZhi.ofJieQi(_birthJieqi);
        _dayGanZhi = GanZhi.ofDate(birthTime.toLocalDate());
        _hourGanzhi = GanZhi.ofHour(birthTime);

        System.out.println("initiating personal , birth JieQi is " + _birthJieqi.toString());
        System.out.println("initiating personal , GanZhi of year, month, day, hour is " + _yearGanZhi.toString() + " " + _monthGanZhi.toString() + " " + _dayGanZhi.toString() + " " + _hourGanzhi.toString() );

        generateDesc01();

        if(goodArgorithm) {
            generatetDesc02And03Precisely();
        } else {
            generatetDesc02And03ByHuYiMing();
        }

    }

    public PersonalFate(LocalDateTime birthTime, boolean isMale, boolean goodArgorithm) throws Exception {
        init(birthTime, isMale, goodArgorithm);
    }

    /* 不单独提供农历算法，在外层把农历先化为阳历
    public PersonalFate(NongLi birthMonth, boolean isMale, boolean goodArgorithm) throws Exception {
        LocalDateTime birthTime = birthMonth.getLocalDateTime();
        init(birthTime, isMale, goodArgorithm);
    }*/

    private void generateDesc01() throws Exception {
        JieQi firstJieQi = _birthJieqi;
        JieQi secondJieQi = firstJieQi.getNextJieQi();

        long hoursAfter = ChronoUnit.HOURS.between(TimeTool.getBigHourLocalTime(firstJieQi.getTransitTime()), _birthTime);
        long hoursBefore = ChronoUnit.HOURS.between(_birthTime, TimeTool.getBigHourLocalTime(secondJieQi.getTransitTime()));

        if (hoursBefore >= 0 && hoursBefore <= 3 * 24) {

            // 如果没有跨天的话，指明生于xx节气前xx个时辰 (同一天精確到時辰，因為如果顯示生於xx節氣前0天看起來很怪)
            if (_birthTime.getDayOfMonth() == secondJieQi.getTransitTime().getDayOfMonth()) {
                long bigHours = hoursBefore/2;

                _desc1 = Display.generateDesc01(false, Jie.getChineseName(secondJieQi.getSolarMonth()), 0, (int)bigHours);
                _dayOfBirthJieqi = (int)ChronoUnit.DAYS.between(firstJieQi.getTransitTime().toLocalDate(), secondJieQi.getTransitTime().toLocalDate());

            } else {
                // 如果跨天的话，指明生于xx节气前x天（精確到天就好）
                LocalDate birthDay = _birthTime.toLocalDate();
                LocalDate transitDate = secondJieQi.getTransitTime().toLocalDate();
                long days = ChronoUnit.DAYS.between(birthDay, transitDate);

                _desc1 = Display.generateDesc01(false, Jie.getChineseName(secondJieQi.getSolarMonth()), (int)days, 0);
                _dayOfBirthJieqi = (int)ChronoUnit.DAYS.between(firstJieQi.getTransitTime().toLocalDate(), birthDay);
            }
        } else {
            // 如果沒有跨天的話，指明生於xx節氣後xx個時辰 （同一天精確到時辰，因為如果顯示生於xx節氣後0天看起來很奇怪）
            if (_birthTime.getDayOfMonth() == firstJieQi.getTransitTime().getDayOfMonth()) {
                long bigHours = hoursAfter/2;

                _desc1 = Display.generateDesc01(true, Jie.getChineseName(firstJieQi.getSolarMonth()), 0, (int)bigHours);
                _dayOfBirthJieqi = 0;
            } else {
                // 如果跨天的話，指明生於xx節氣後xx天（精確到天就好）
                LocalDate birthDay = _birthTime.toLocalDate();
                LocalDate transitDate = firstJieQi.getTransitTime().toLocalDate();
                long days = ChronoUnit.DAYS.between(transitDate, birthDay);

                _desc1 = Display.generateDesc01(true, Jie.getChineseName(firstJieQi.getSolarMonth()), (int)days, 0);
                _dayOfBirthJieqi = (int)days;
            }
        }

        System.out.println(_desc1);
        System.out.println("生於節氣之後" + _dayOfBirthJieqi + "天");
    }

    /**
     * 使用胡一鳴的方法進行計算
     * @throws Exception
     */
    private void generatetDesc02And03ByHuYiMing() throws Exception {
        /* 基本計算方法：
        1. 如果是 男命生於陽年（甲年，丙年..），女命生於陰年（乙年，丁年..），則順排（順到下一個節氣）, 否則逆排 (逆到上一個節氣)
        2. 順排的話計算出生時間到下一個節氣差距多少個時辰X，逆排的話計算出生時間到上一個節氣差距多少個時辰X
        3. 計算出生後要多久上運：1個時辰＝10天，1天＝4個月，3天＝1年 （1個月30天＝10年，一年360天＝120年＝一生）：這種方法並不太精確，因為實際上出生所在的節氣不一定剛好等於30天，
        4. 計算首次上運時間：出生時間 + 步驟3 （按30天一個節氣往時間前進方向推）
        5. 計算交接年/節氣

        弊端：因為出生所在節氣不一定剛好30天，最後算出上運最大誤差大概在 －125 ～ ＋ 125 天之間 （）
         */

        boolean isYangYear = GanZhiTool.isYangYear(_birthJieqi.getSolarYear());
        boolean forwardCalc = (_isMale && isYangYear) || (!_isMale && !isYangYear);

        float bigHoursBetween = forwardCalc ?
                        ChronoUnit.HOURS.between(_birthTime, TimeTool.getBigHourLocalTime(_birthJieqi.getNextJieQi().getTransitTime())) :     // 如果是順排，計算出生時間到下一個節氣的時間跨度
                        ChronoUnit.HOURS.between(TimeTool.getBigHourLocalTime(_birthJieqi.getTransitTime()), _birthTime);                     // 如果是逆排，計算出生時間到上一個節氣的時間跨度

        bigHoursBetween /= 2;  // 化為時程
        long totalDaysToEngage = (int)(bigHoursBetween * 10);   // 共計要多少天上大運（一個時辰＝10天）

        System.out.println("和上運節氣差n個時辰: " + bigHoursBetween);

        // 大運於xx年xx月xx日後上運：
        long yearsToAdd = totalDaysToEngage / 360;
        long monthsToAdd = totalDaysToEngage % 360 / 30;
        long daysToAdd = totalDaysToEngage % 30;

        _desc2 = Display.generateDesc02((int)yearsToAdd, (int)monthsToAdd, (int)daysToAdd);


        // 計算首次大運交接時刻: engageDayOfJieQi, engageJieQi
        long deltaMonth = (_dayOfBirthJieqi- 1 + daysToAdd) / 30;
        monthsToAdd += deltaMonth;

        long engageDayOfJieQi = (_dayOfBirthJieqi - 1 + daysToAdd) % 30 + 1;
        JieQi engageJieQi = _birthJieqi.plusYearsAndMonth((int) yearsToAdd, (int) monthsToAdd);

        // 每逢xx之年交托
        _desc3 = Display.generateDesc03(engageJieQi.getSolarYear(), engageJieQi.getSolarMonth(), (int)engageDayOfJieQi);

        System.out.println(_desc2);
        System.out.println(_desc3);
    }

    /**
     * 使用精準算法進行計算
     * @throws Exception
     */
    private void generatetDesc02And03Precisely() throws Exception {
        /* 基本計算方法：
        1. 如果是 男命生於陽年（甲年，丙年..），女命生於陰年（乙年，丁年..），則順排（順到下一個節氣）, 否則逆排 (逆到上一個節氣)
        2. 順排的話計算出生時間到下一個節氣差距多少個時辰X，逆排的話計算出生時間到上一個節氣差距多少個時辰X (考慮精確到分)
        3. 計算出生後要多久上運：因為一個節氣月代表10歲（出生所在年代表120歲），所以每個時辰代表歲數＝ 365*10 / (出生所在節氣共擁有幾個時辰)
        4. 計算首次上運時間：出生時間 + 步驟3
        5. 計算交接年/節氣
         */

        boolean isYangYear = GanZhiTool.isYangYear(_birthJieqi.getSolarYear());
        boolean forwardCalc = (_isMale && isYangYear) || (!_isMale && !isYangYear);

        long minutesBetween = forwardCalc ?
                ChronoUnit.MINUTES.between(_birthTime, _birthJieqi.getNextJieQi().getTransitTime()) :
                ChronoUnit.MINUTES.between(_birthJieqi.getTransitTime(), _birthTime);

        System.out.println(String.format("和上運節氣差%f個時辰(%d分钟) ", minutesBetween/60.0/2.0, minutesBetween));

        long minutesOfBirthJie = ChronoUnit.MINUTES.between(_birthJieqi.getTransitTime(), _birthJieqi.getNextJieQi().getTransitTime());
        float minuteRepresentDays = (365.2422f * 10.0f)/ minutesOfBirthJie;

        System.out.println(String.format("當月多少天：%f, 每個時辰代表天數: %f", minutesOfBirthJie /60.0/24.0,  minuteRepresentDays*60.0*2.0));

        // 上運要經過這麼多天：
        float daysToAdd = minuteRepresentDays * minutesBetween;

        //
        System.out.println(String.format("上運要經過%d天%d小時", (int)daysToAdd, (int)((daysToAdd - (int)daysToAdd)*24)));

        // 計算首次上運時間點
        LocalDateTime engageDatetime = _birthTime.plusDays((int)daysToAdd).plusHours((int)((daysToAdd - (int)daysToAdd)*24));

        int y = (int)ChronoUnit.YEARS.between(_birthTime, engageDatetime);
        int m = (int)ChronoUnit.MONTHS.between(_birthTime, engageDatetime.minusYears(y));
        int d = (int)ChronoUnit.DAYS.between(_birthTime, engageDatetime.minusYears(y).minusMonths(m));
        _desc2 = Display.generateDesc02(y, m, d);

        // 首次上運節氣以及日子
        JieQi engageJieqi = JieQi.of(engageDatetime);
        int dayOfEngageJieqi = (int)ChronoUnit.DAYS.between(engageJieqi.getTransitTime(), engageDatetime);

        // 每逢xx之年交托
        _desc3 = Display.generateDesc03(engageJieqi.getSolarYear(), engageJieqi.getSolarMonth(), (int)dayOfEngageJieqi);

        System.out.println(_desc2);
        System.out.println(_desc3);
    }
}
