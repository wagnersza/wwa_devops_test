package com.wwa.devops.utils;

public class TestUtils {

    public static final String test() {
        return TestUtils.class.getPackage().getImplementationVersion();
    }

}
