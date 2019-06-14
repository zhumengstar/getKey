package com.gennlife.getkey.file;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * 处理文件夹下xml文件
 *
 * @author:zhumeng
 * @desc:
 **/
public class LoadFileName {
    static List<String> list = new CopyOnWriteArrayList<>();

    /**
     * 加载可分析的文件到list中
     *
     * @param path
     * @return
     */
    public static List<String> openFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        openFile(file2.getAbsolutePath());
                    } else {
                        if (Pattern.matches(".+\\.(xml|html|txt)$", file2.getAbsolutePath())) {
                            list.add(file2.getAbsolutePath());
                        }
                    }
                }
            }
        }
        return list;
    }
}
