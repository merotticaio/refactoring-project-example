package com.github.core;

import static org.apache.commons.lang3.RandomStringUtils.random;

public class IdGenerator {

    public static String id() {
        return random(10, true, true);
    }

}
