package com.gennlife.getkey.key;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gennlife.getkey.util.PropertiesUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
public class RegexMatch {

    @Value("${regexfile}")
    private static String regexfile;

    static Map<String, String> regexMap = load();

    public static Map<String, String> load() {
        JSONObject regex = PropertiesUtils.getJsonResource(regexfile);
        Map<String, String> maps = (Map) JSON.parse(regex.toJSONString());
        return maps;
    }

    public static String match(String content) {
        for (Map.Entry<String, String> entry : regexMap.entrySet()) {
            if (Pattern.matches(entry.getValue(), content)) return entry.getKey();
        }
        return "NO_MATCH";
    }

    public static void main(String[] args) {
        System.out.println(match("查体"));
    }
}
