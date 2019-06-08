package com.geenlife.getkey.demo.service.key;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

/**
 * @author:zhumeng
 * @desc:
 **/
public class Match {

    public static void main(String[] args) {
        Match match = new Match();
        System.out.println(Match.match("性别"));
    }

    private static Properties prop = null;

    Match() {
        prop = new Properties();
        try {
            prop.load(new InputStreamReader(Objects.requireNonNull(Match.class.getClassLoader().getResourceAsStream("regex.properties")), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String match(String content) {
        if (Match.prop == null) {
            new Match();
        }
        Iterator<String> it = prop.stringPropertyNames().iterator();
        try {
            while (it.hasNext()) {
                String key = it.next();
                if (content.contains(key)) return prop.getProperty(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "key";
    }

//    public static String match(String content) {
//        if (content.contains("记录")) return "医院记录";
//        if (content.contains("病史")) return "疾病史";
//        if (content.contains("号")) return "号码";
//        if (content.contains("姓名") || content.contains("性别") || content.contains("出生日") || content.contains("年龄"))
//            return "个人信息";
//        if (content.contains("住址")) return "居住地址";
//        if (content.contains("签名")) return "签名";
//        if (content.contains("诊断")) return "诊断";
//        if (content.contains("结果")) return "疾病结果";
//        if (content.contains("过敏史")) return "过敏史";
//        return null;
//    }
}
