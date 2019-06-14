package com.gennlife.getkey.util;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
public class ReNameTxtToXml {
    public static void main(String[] args) {
        reNameFile("/Users/zgh/OneDrive/数据/export_201707111722");
    }
    public static void reNameFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    String fileName = file2.getAbsolutePath();
                    if (file2.isDirectory()) {
                        reNameFile(file2.getAbsolutePath());
                    } else {
                        if (Pattern.matches(".+\\.(txt|html|xml)$", fileName)) {
                            int i = fileName.indexOf(".");
                        }else {
                            file2.renameTo(new File("" + fileName + ".txt"));
                        }
                    }
                }
            }
        }
    }
}
