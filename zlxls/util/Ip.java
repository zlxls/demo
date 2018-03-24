package com.zlxls.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 获取公网ip地址操作
 * @ClassNmae：NewClass   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class Ip {
    /**
     * @Description：获取公网ip地址操作
     * @param request
     * @return ip地址
     */
    public static String getRemoteLoginUserIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
