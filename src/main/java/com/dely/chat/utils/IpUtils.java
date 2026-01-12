package com.dely.chat.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) return null;

        // 1. 尝试从 X-Forwarded-For 获取（最常用，经过多级代理时，第一个是真实IP）
        String ip = request.getHeader("x-forwarded-for");

        // 2. 尝试其他常见的代理头
        if (isUnknown(ip)) ip = request.getHeader("Proxy-Client-IP");
        if (isUnknown(ip)) ip = request.getHeader("WL-Proxy-Client-IP");
        if (isUnknown(ip)) ip = request.getHeader("HTTP_CLIENT_IP");
        if (isUnknown(ip)) ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        // 3. 最后直接获取（如果没有代理，这就是真实IP）
        if (isUnknown(ip)) ip = request.getRemoteAddr();

        // 4. 处理多重代理的情况，取第一个非 unknown 的 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        // 5. 特殊处理：IPv6 的本地回环地址转为 IPv4
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private static boolean isUnknown(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }
}
