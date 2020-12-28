package com.letschat.master.util;

import java.util.Random;

/**
 * @author jarvis.yuan
 * @version 1.0.0
 * @ClassName RandomUtil.java
 * @Description 随机生成用户名,以后用业务替代
 * @createTime 2020年12月28日 15:29:00
 */
public class RandomUtil {
    private static Random random = new Random();
    private final static int DELTA = 0x9fa5 - 0x4e00 +1;

    public static char getName(){
        return (char)(0x4e00 + random.nextInt(DELTA));
    }
}
