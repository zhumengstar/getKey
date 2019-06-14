package com.gennlife.getkey.file;

import com.gennlife.getkey.key.SplitContent;
import com.gennlife.getkey.util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
public class GetContent {
    static final String regex = ">([^><]+)<";

    public static List getContentList(String path) {
        List list = new ArrayList();
        String s = FileUtils.readFile(path);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        while (m.find()) {
            String trim = m.group().substring(1, m.group().length() - 1).trim();

            trim = SplitContent.getTime(list, trim, path);

            if (!"".equals(trim) && !Pattern.matches("[\\s ]", trim)) {
                SplitContent.spli(list, trim);
            }
        }
        return list;
    }
}
