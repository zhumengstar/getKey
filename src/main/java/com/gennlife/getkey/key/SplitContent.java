package com.gennlife.getkey.key;

import com.gennlife.getkey.file.AddContentToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: ZhuMeng
 * @desc:
 **/
public class SplitContent {

    static final String timeRegex = "(\\d{4}[-年.]\\d{1,2}[-月.]\\d{1,2}([ 日号]+)?(\\d{2}[:：]\\d{2})?)" +
            "|(\\d{2}[：:]\\d{2}-\\d{2}[：:]\\d{2})" +
            "|(\\d{2}[：:]\\d{2})";

    /**
     * 移除无效子字符串
     */
    static final String replaceRegex = "([(（].*[）)])" +
            "|(\\d*[.．、])" +
            "|([:：])" +
            "|(\\p{P})";

    /**
     * 移除无效字符串
     */
    static final String removeRegex = "(\\d*)" +
            "|([^\\x00-\\xff])" +
            "|([^\\u4e00-\\u9fa5]+)";

    /**
     * 字符串分割,并保留分割符号
     */
    static final String split = "([：:；;。])";

    static final char[] c = {';', '；', ':', '：', '。'};

    public static List<String> spli(List<String> ls, String sp) {
        Pattern p = Pattern.compile(split);
        if (sp.length() - 1 >= 0 && !Pattern.matches("[\\s]+", sp)) {
            Matcher m = p.matcher(sp.substring(0, sp.length() - 1));
            if (!m.find()) {
                if (sp.length() < 10000)
                    ls.add(sp);
            }
        }
        for (char c1 : c) {
            if (sp.contains(String.valueOf(c1)) && sp.indexOf(String.valueOf(c1)) != sp.length() - 1) {
                spli(ls, sp.substring(0, sp.indexOf(c1) + 1));
                spli(ls, sp.substring(sp.indexOf(c1) + 1));
                break;
            }
        }
        return ls;
    }


    public static boolean remove(String content) {
        if (Pattern.matches(removeRegex, content))
            return true;
        else
            return false;
    }

    /**
     * 替换分割符
     *
     * @param content
     * @return
     */
    public static String replace(String content) {
        Pattern p = Pattern.compile(replaceRegex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            content = m.replaceAll("");
        }
        return content;
    }

    /**
     * 抽取时间
     *
     * @param list
     * @param content
     * @param path
     * @return
     */
    public static String getTime(List list, String content, String path) {
        Pattern pt = Pattern.compile(timeRegex);
        Matcher mt = pt.matcher(content);
        while (mt.find()) {
//            AddContentToFile.addContent(true, list, path, "时间:");
//            AddContentToFile.addContent(false, list, path, mt.group());
            list.add("时间:");
            list.add(mt.group());
        }
        return mt.replaceAll("");
    }

    /**
     * 解析字符串
     *
     * @param list
     * @param content
     * @param path
     */
    public static void getKeyForValue(List list, String content, String path) {
        content = getTime(list, content, path);
//        String[] splits = content.split(split);
        List<String> spli = spli(new ArrayList<>(), content);
        for (String s : spli) {
//            s = s.replaceAll("[\\s]", "");
            if (!"".equals(s) && s != null) {
//                s = replace(s);
//                if (!remove(s) && !Pattern.matches("[\\s]+", s)) {
                if (s.length() < 10000)
                    AddContentToFile.addContent(true, list, path, s);
//                }
            }
        }
    }
}
