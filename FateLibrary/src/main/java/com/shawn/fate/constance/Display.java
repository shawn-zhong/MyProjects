package com.shawn.fate.constance;

import com.shawn.fate.model.GanZhi;

public class Display {

    private static final String DESC_01_HOUR = "生於%s%s%d個時辰";
    private static final String DESC_01_DAYS = "生於%s%s%d天";

    public static String generateDesc01(boolean forward, String jieQi, int days, int hours) {
        String direcct = forward ? "後" : "前";

        if (days == 0) {
            return String.format(DESC_01_HOUR, jieQi, direcct, hours);
        } else {
            return String.format(DESC_01_DAYS, jieQi, direcct, days);
        }
    }

    public static String generateDesc02(int years, int months, int days) {
        String y="", m="", d="";

        if (years > 0) {
            y = years + "年";

            if (months==0 && days == 0) {
                y += "整";
            } else if (months==0 || days == 0) {
                y += "又";
            }
        }

        if (months > 0) {
            m = months + "個月";
        }

        if (days> 0) {
            d = days + "天";
        }

        return "大運於" + y + m + d + "後上運";
    }

    public static String generateDesc03(int solarYear, int solarMonths, int days) {
        Gan transitGan1 = GanZhi.ofYear(solarYear).getGan();
        Gan transitGan2 = GanZhi.ofYear(solarYear + 5).getGan();
        String transitGanStr;

        if (transitGan1.getIntVal() < transitGan2.getIntVal()) {
            transitGanStr = String.format("%s%s", transitGan1.getChineseVal(), transitGan2.getChineseVal());
        } else {
            transitGanStr = String.format("%s%s", transitGan2.getChineseVal(), transitGan1.getChineseVal());
        }

        String jieqi = Jie.getChineseName(solarMonths);

        String d = "";
        if (days == 0) {
            d = "當天交脫";
        } else {
            d = String.format("後%d天交脫", days);
        }

        return "每逢" + transitGanStr + jieqi + d;
    }

}
