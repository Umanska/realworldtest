package org.realworld.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class StringUtils {

    public static String getUniqueUsername(String usernamePrefix) {
        return usernamePrefix + UUID.randomUUID();
    }

    public static String getRandomString(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }
}
