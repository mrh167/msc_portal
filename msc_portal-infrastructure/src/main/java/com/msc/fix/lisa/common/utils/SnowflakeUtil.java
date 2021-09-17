package com.msc.fix.lisa.common.utils;

import cn.hutool.core.lang.Snowflake;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class SnowflakeUtil {

    private static Snowflake snowflake;

    static {
        String workerId = getWorkId();
        String datacenterId = getDatacenterId();
        snowflake = new Snowflake(Integer.parseInt(datacenterId), Integer.parseInt(workerId));
    }

    /**
     * 生成雪花算法ID
     * generateId
     * @return
     */
    public static String genId() {
        return String.valueOf(snowflake.nextId());
    }

    private static String getWorkId(){
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for(int b : ints){
                sums += b;
            }
            return String.valueOf((sums % 32));
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return String.valueOf(RandomUtils.nextLong(0,31));
        }
    }

    private static String getDatacenterId(){
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        
        int sums = 0;
        for (int i: ints) {
            sums += i;
        }
        return String.valueOf((sums % 32));
    }
}