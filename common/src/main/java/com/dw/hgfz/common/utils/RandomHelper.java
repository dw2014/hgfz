package com.dw.hgfz.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by dw on 9/7/2015.
 */
public class RandomHelper {

    private RandomHelper() {

    }

    public static String randomAlphabetic(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public static String randomNumeric(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    public static Long randomLong() {
        return new Random().nextLong();
    }

    public static int randomInt() {
        return new Random().nextInt();
    }

    public static int randomInt(int n) {
        return new Random().nextInt(n);
    }

    public static Boolean randomBoolean() {
        List<Object> array = new ArrayList<>();
        array.add(true);
        array.add(false);
        return Boolean.parseBoolean(randomValueFromList(array).toString());
    }

    public static String randomEncodeEmail() throws Exception {
        return URLEncoder.encode(randomEmail(), "UTF-8");
    }

    public static String randomEmail() {
        return "dw+hgfz_" + randomAlphabetic(8).toLowerCase() + String.format("@unity3d.com");
    }

    public static String randomUsername() {
        return "dwhgfz_" + randomAlphabetic(8);
    }

    public static String randomUsername(Integer length) {
        return length == null ? randomAlphabetic(8) : randomAlphabetic(length);
    }

    public static Object randomValueFromList(List<Object> values) {
        int rand = randomInt();
        int result = Math.abs(rand) % values.size();
        return values.get(result);
    }

    public static String randomIP() {
        return randomNumeric(2) + "." + randomNumeric(2) + "." + randomNumeric(2) + "." + randomNumeric(2);
    }

    public static String fixedLengthRandomString(int length, String value) {
        return (value.length() > length) ? value.substring(0, length - 1) :
                StringUtils.leftPad(value, length, "0");
    }

    public static Date randomBirthday() {
        Integer gap = new Random().nextInt(20);
        return DateUtils.setMilliseconds(DateUtils.addYears(new Date(), -(gap + 20)), 0);
    }

    public static String randomGender() {
        return new Random().nextInt() % 2 == 0 ? "MALE" : "FEMALE";
    }

    public static String randomPassword() throws Exception {
        return RandomHelper.randomAlphabetic(4).toLowerCase() +
                RandomHelper.randomNumeric(6) +
                RandomHelper.randomAlphabetic(4).toUpperCase();
    }

    public static String randomPhoneNumber() throws Exception {
        return "86135" + randomNumeric(8);
    }

    public static Double randomDouble() throws Exception {
        return new Random().nextDouble();
    }

    public static String randomUnityId() throws Exception {
        return Long.toString(RandomHelper.randomLong() & (0x3fffffffffL));
    }
}
