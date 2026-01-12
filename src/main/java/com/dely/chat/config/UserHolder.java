package com.dely.chat.config;

import java.util.Map;

public class UserHolder {
    private static final ThreadLocal<Map<String, Object>> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrent(Map<String, Object> userMap) {
        CURRENT_LOCAL.set(userMap);
    }

    public static Map<String, Object> getCurrent() {
        return CURRENT_LOCAL.get();
    }

    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}
