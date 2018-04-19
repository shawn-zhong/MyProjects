package com.shawn.fate.tools;

import com.shawn.fate.constance.Gan;
import com.shawn.fate.constance.TenRep;
import com.shawn.fate.constance.Zhi;

import java.util.ArrayList;
import java.util.List;

public class GanZhiTool {


    /**
     * 阳年/阴年
     * @return
     */
    public static boolean isYangYear() {
        throw new RuntimeException("");
    }


    /**
     * 根据地址得到藏干
     * @param zhi
     * @return
     */
    public static List<Gan> getInnerGanFromZhi(Zhi zhi) {
        int branchToStem[][] =
        {
            { 9,	-1,		-1	},	// 子：癸
            { 5,	9,		7	},	// 丑：己 癸 辛
            { 0,	2,		4	},	// 寅: 甲 丙 戊
            { 1,	-1,		-1	},	// 卯: 乙
            { 4,	1,		9	},	// 辰: 戊 乙 癸
            { 2,	4,		6	},	// 巳: 丙 戊 庚
            { 3,	5,		-1	},	// 午: 丁 己
            { 5,	3,		1	},	// 未: 己 丁 乙
            { 6,	8,		4	},	// 申: 庚 壬 戊
            { 7,	-1,		-1	},	// 酉: 辛
            { 4,	7,		3	},	// 戌: 戊 辛 丁
            { 8,	0,		-1	},	// 亥: 壬 甲
        };

        List<Gan> retArray = new ArrayList<>(3);
        for (int i=0; i<3; i++) {
            int _zhi = branchToStem[zhi.getIntVal()][i];
            if (_zhi >= 0) {
                retArray.add(Gan.values()[_zhi]);
            }
        }

        return retArray;
    }

    /**
     * 根据日主的天干，得到输入的干的十神
     * @param birthdayGan
     * @param calcGan
     * @return
     */
    public TenRep getTenRepresantOfGan(Gan birthdayGan, Gan calcGan) {

        // 十神对应表
        int stemMeans[][] =
        {
            //	甲	乙	丙	丁	戊	己	庚	辛	壬	癸
            {	1,	2,	3,	4,	5,	6,	7,	8,	9,	10	},	// 甲
            {	2,	1,	4,	3,	6,	5,	8,	7,	10,	9	},	// 乙
            {	9,	10,	1,	2,	3,	4,	5,	6,	7,	8	},	// 丙
            {	10,	9,	2,	1,	4,	3,	6,	5,	8,	7	},	// 丁
            {	7,	8,	9,	10,	1,	2,	3,	4,	5,	6	},	// 戊
            {	8,	7,	10,	9,	2,	1,	4,	3,	6,	5	},	// 己
            {	5,	6,	7,	8,	9,	10,	1,	2,	3,	4	},	// 庚
            {	6,	5,	8,	7,	10,	9,	2,	1,	4,	3	},	// 辛
            {	3,	4,	5,	6,	7,	8,	9,	10,	1,	2	},	// 壬
            {	4,	3,	6,	5,	8,	7,	10,	9,	2,	1	},	// 癸
        };

        int represent = stemMeans[birthdayGan.getIntVal()][calcGan.getIntVal()];
        return TenRep.values()[represent];
    }

    /**
     * 根据日志的天干，得到输入的支的十神（包括藏干）
     * @param birthdayGan
     * @param calcZhi
     * @return
     */
    public List<TenRep> getTenRepresantOfZhi(Gan birthdayGan, Zhi calcZhi) {
        List<Gan> gans = getInnerGanFromZhi(calcZhi);
        List<TenRep> list = new ArrayList<>(3);

        for (Gan gan : gans) {
            TenRep rep = getTenRepresantOfGan(birthdayGan, gan);
            list.add(rep);
        }

        return list;
    }
}
