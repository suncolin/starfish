package com.starfish.core.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * DateTimeUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-05
 */
public class DateTimeUtilTest {

    @Test
    public void parseTest() {
        Date date = DateTimeUtil.parse("2020-01-01");
        System.out.println(date);
        Assert.assertNotNull(date);

        Date date2 = DateTimeUtil.parse("2020-01-01 12:00:00");
        System.out.println(date2);
        Assert.assertNotNull(date2);

        Date date3 = DateTimeUtil.parse("2020-01-01");
        System.out.println(date3);
        Assert.assertNotNull(date3);

        Date date4 = DateTimeUtil.parse("20200101");
        System.out.println(date4);
        Assert.assertNotNull(date4);

        Date date5 = DateTimeUtil.parse("2020-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss");
        System.out.println(date5);
        Assert.assertNotNull(date5);

        Date date6 = DateTimeUtil.parse(1634106492000L);
        System.out.println(date6);
        Assert.assertNotNull(date6);
    }

    @Test
    public void dateBetweenTest() {
        List<String> expectedValue = Arrays.asList("2020-01-01", "2020-01-02", "2020-01-03");
        List<String> actualValue = DateTimeUtil.dateBetween("2020-01-01", "2020-01-03");
        System.out.println(actualValue);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void getMonthStartTest() {
        // 时间是2020-10-15 09:00:00，当月第一天是2020-10-01 00:00:00，时间戳是1601481600000
        long expectedValue = 1601481600000L;
        Date monthStart = DateTimeUtil.getMonthStart( new Date(1602723600000L));
        long actualValue = monthStart.getTime();
        System.out.println(actualValue);
        Assert.assertEquals(expectedValue, actualValue);
    }

}
