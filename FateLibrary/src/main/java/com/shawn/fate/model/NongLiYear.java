package com.shawn.fate.model;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NongLiYear {

    private boolean hasLeapMonth = false;
    private List<NongLi> monthItems = new ArrayList<>(13);

    /**
     * 初始化一个农历年
     * @param months 月份队列
     */
    public NongLiYear(@NotNull List<NongLi> months) {
        monthItems.clear();
        monthItems = months.stream().sorted().collect(Collectors.toList());

        if (months.stream().anyMatch(NongLi::getIsLeapMonth)) {
            hasLeapMonth = true;
        }
    }

    /**
     * 该年是否包含闰月
     * @return
     */
    public boolean hasLeapMonth() {
        return hasLeapMonth;
    }

    /**
     * 返回该年的闰月
     * @return
     * @throws Exception
     */
    public NongLi leapMonth() throws Exception {
        return monthItems.stream().filter(NongLi::getIsLeapMonth).findFirst().orElseThrow(() -> new Exception("This year doesn't contain any leap month"));
    }
}
