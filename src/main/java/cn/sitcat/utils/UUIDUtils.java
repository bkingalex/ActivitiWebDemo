package cn.sitcat.utils;

import java.util.UUID;

/**
 * @author Hiseico
 * @description: uuid 工具类
 * @date 2018/4/1012:36
 */

public class UUIDUtils {
    public static String getUUID() {
        String uuidStr = UUID.randomUUID().toString();
        return uuidStr.replaceAll("-", "");
    }
}
