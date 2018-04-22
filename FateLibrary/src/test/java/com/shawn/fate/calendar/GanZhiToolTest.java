package com.shawn.fate.calendar;

import com.shawn.fate.constance.Gan;
import com.shawn.fate.constance.TenRepre;
import com.shawn.fate.constance.Zhi;
import com.shawn.fate.tools.GanZhiTool;
import org.junit.Test;

import java.util.List;

public class GanZhiToolTest {

    @Test
    public void TestYangYear() {
        System.out.println(GanZhiTool.isYangYear(2019));
    }

    @Test
    public void InnerGan() {
        List<Gan> list = GanZhiTool.getInnerGanFromZhi(Zhi.YOU);

        for (Gan gan : list) {
            System.out.println(gan.getChineseVal());
        }
    }

    @Test
    public void TenRepres() {
        TenRepre rep = GanZhiTool.getTenRepresantOfGan(Gan.GUI, Gan.BING);
        System.out.println(rep.getChineseVal());

        List<TenRepre> list = GanZhiTool.getTenRepresantOfZhi(Gan.GUI, Zhi.CHOU);
        for (TenRepre re : list) {
            System.out.println(re.getChineseVal());
        }
    }
}
