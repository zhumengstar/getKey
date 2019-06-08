package com.geenlife.getkey.demo.service.util;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
public class ReNameTxtToXml {
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
                        if (Pattern.matches(".+\\.txt$", fileName)) {
                            int i = fileName.indexOf(".");
                            String newFileName = fileName.substring(0, i);
                            file2.renameTo(new File("" + newFileName + ".xml"));
                        }
                    }
                }
            }
        }
    }
}
