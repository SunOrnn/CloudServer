package com.ornn.wallet.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

@Slf4j
public class IDutils {
    /**
     * WorkID的获取方式为：根据机器IP地址获取工作进程ID。如果线上机器的IP地址的二进制标识的最后10位不重复，则建议使用此种方式。
     * 例如机器的IP地址为“192.168.1.108”,二进制标识为“11000000 10101000”，截取最后10位“01 01101100”， 转为十进制数为364，则设置workID为364
     */
    // 性能待优化
    public static int getWorkId() {
        int workId = 1;
        try {
            // 获取机器IP地址的二进表示
            InetAddress address = InetAddress.getLocalHost();
            String sIP = address.getHostAddress();
            sIP = sIP.replaceAll("\t", "").trim();
            String[] arr = sIP.split("\\.");
            String rs = "";
            for (String str : arr) {
                String s = Integer.toBinaryString(Integer.parseInt(str));
                if (s.length() < 8) {
                    int diff = 8 - s.length();
                    for (int i = 0; i < diff; i++) {
                        s = "0" + s;
                        rs += s;
                    }
                }
                if (StringUtils.isNotBlank(rs)) {
                    // 截取IP地址二进制表示的后10位
                    String last10 = rs.substring(rs.length() - 10, rs.length());
                    workId = Integer.parseInt(last10, 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return workId;
    }
}
