package com.geenlife.getkey.demo.service.key;

import com.geenlife.getkey.demo.service.file.AddKeyToFile;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
public class GetKey {

    public static void getXmlKey(List l, Vector v, Element node, String path) {
        List<Attribute> attributes = node.attributes();
        for (Attribute attr : attributes) {
            if ("name".equals(attr.getName()) && !"".equals(attr.getValue())) {
                getKeyForName(l, v, attr.getValue(), path);
                break;
            }
        }
        getKeyForValue(l, v, node.getTextTrim(), path);
    }

    /**
     * 提取属性上的name数据
     * 只要不重复就为key
     *
     * @param path
     */
    public static void getKeyForName(List l, Vector v, String value, String path) {
        if (!IsKey.isRepeat(v, value)) {
            AddKeyToFile.addKey(l, value, path);
        }
    }

    /**
     * 提取文本数据
     *
     * @param text
     * @param path
     */
    public static void getKeyForValue(List l, Vector v, String text, String path) {
        //是否为空   太长移除
        if (!"".equals(text) && text.length() < 2000) {
            //是否含有冒号  不能为字母数字等开头的   ---->   不符合则加入value中
            if (Pattern.matches(".+[:|：](.*)?", text) && !Pattern.matches("^\\w.*", text)) {
                if (!IsKey.isRepeat(v, text)) {
                    //提取key之前的数据

                    if (text.contains(":") && text.contains("：")) {
                        if (text.indexOf(":") < text.indexOf("：")) {
                            //提取出冒号前的数据
                            AddKeyToFile.addKey(l, text.substring(0, text.indexOf(":")), path);
                            if (!":".equals(text.substring(text.indexOf(":")).trim()))
                                AddKeyToFile.addValue(l, text.substring(text.indexOf(":") + 1), path);
                        } else {
                            AddKeyToFile.addKey(l, text.substring(0, text.indexOf("：")), path);
                            if (!"：".equals(text.substring(text.indexOf("：")).trim()))
                                AddKeyToFile.addValue(l, text.substring(text.indexOf("：") + 1), path);
                        }
                    }
                    if (text.contains(":") && !text.contains("：")) {
                        //提取出冒号前的数据
                        AddKeyToFile.addKey(l, text.substring(0, text.indexOf(":")), path);
                        if (!":".equals(text.substring(text.indexOf(":")).trim()))
                            AddKeyToFile.addValue(l, text.substring(text.indexOf(":") + 1), path);
                    }
                    if (text.contains("：") && !text.contains(":")) {
                        AddKeyToFile.addKey(l, text.substring(0, text.indexOf("：")), path);
                        if (!"：".equals(text.substring(text.indexOf("：")).trim()))
                            AddKeyToFile.addValue(l, text.substring(text.indexOf("：") + 1), path);
                    }
                }
            } else {
                if (!IsKey.isRepeat(v, text))
                    AddKeyToFile.addValue(l, text, path);
            }
        }
    }


}

