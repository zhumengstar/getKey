package com.geenlife.getkey.demo.service.key;

import java.util.Vector;

/**
 * @author:zhumeng
 * @desc:
 **/
public class IsKey {
    /**
     * 判断是否重复的key
     *
     * @param value
     * @return
     */
    public static boolean isRepeat(Vector v, String value) {
        //除去冒号
        value = value.replaceAll("[:：]", "");

        //数据key太短移除
        //清除空格
        value = value.replaceAll(" ", "");
        value = value.replaceAll("　", "");

        if (!v.contains(value)) {
            if (v.size() > 1) v.remove(0);
            v.add(value);
            return false;
        }
        return true;
    }
}