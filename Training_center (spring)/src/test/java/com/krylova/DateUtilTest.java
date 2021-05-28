package com.krylova;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DateUtilTest {

    @Test
    void format() {
        Assert.assertNull(DateUtil.format(null));
        String actual1 = DateUtil.format(LocalDate.of(1978, 12, 3));
        String expected1 = "03.12.1978";
        Assert.assertEquals(expected1, actual1);
    }

    @Test
    void parse() {
        LocalDate actual = DateUtil.parse("1989-27-34");
        Assert.assertNull(actual);
        LocalDate actual1 = DateUtil.parse("1978-12-03");
        LocalDate expected1 = LocalDate.of(1978, 12, 3);
        Assert.assertEquals(expected1, actual1);
        LocalDate actual2 = DateUtil.parse("03.12.1978");
        Assert.assertEquals(expected1, actual2);
    }

    @Test
    void reformat() {
        String actual = DateUtil.reformat("1989-27-34");
        String expected = "34.27.1989";
        Assert.assertEquals(expected, actual);
        String actual1 = DateUtil.reformat("19989-27-34");
        String expected1 = "19989-27-34";
        Assert.assertEquals(expected1, actual1);
    }

    @Test
    void validDate() {
        boolean actual = DateUtil.validDate("1989-27-34");
        Assert.assertFalse(actual);
        boolean actual1 = DateUtil.validDate("1763-08-30");
        Assert.assertTrue(actual1);
        boolean actual2 = DateUtil.validDate("drjftgvdjr");
        Assert.assertFalse(actual2);
    }
}