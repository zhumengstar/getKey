package com.gennlife.getkey.file;

import com.gennlife.getkey.util.PropUtils;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author:zhumeng
 * @desc: 添加数据到文件中
 **/
public class AddContentToFile {
    static volatile Set set = new ConcurrentSkipListSet();

    public static File newFile(String file) {
        //创建数据保存的文件夹
        File f = new File(file);
        String name = f.getName();
        int i = name.indexOf(".");
//        String newFileName = name.substring(0, i) + "-" + Thread.currentThread().getId() + ".data";
//        File newFile = new File("/Users/zgh/Desktop/java/getkey/src/main/java/com/gennlife/getkey/dRemo/data/" + newFileName);
        File newFile;
//        if (file.contains("xjd")) {
//            newFile = new File("/Users/zgh/Desktop/java/getkey/src/main/java/com/gennlife/getkey/data/xjd.data");
//        } else {
//            newFile = new File("/Users/zgh/Desktop/java/getkey/src/main/java/com/gennlife/getkey/data/zzzx.data");
//        }
        newFile = new File(PropUtils.getProp().getProperty("storeKey"));
        //判断文件夹是否存在     若文件夹不存在则创建文件夹
        File folder = new File(newFile.getParent());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //判断文件是否存在     若文件夹不存在则创建文件
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                System.out.println("创建文件失败");
            }
        }
        return newFile;
    }

    public static void addContent(boolean key, String file, String content) {
        File newFile = newFile(file);
        //保存数据到指定文件夹中
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile, true)));
            //清除空格
//            content = content.replaceAll("[　 \\s]*", "");
            if (key) {
                out.write("key:" + content.trim() + "\n");
            } else {
                out.write("    " + content.trim() + "\n");
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void addContent(boolean key, List list, String file, String content) {
//        File newFile = newFile(file);
        //保存数据到指定文件夹中
//        BufferedWriter out = null;
        try {
//            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile, true)));
            //清除空格
//            content = content.replaceAll("[　 \\s]*", "");
            if (key) {
//                out.write(content.trim() + "\n");
                list.add(content.trim());
            } else {
//                out.write(content.trim() + "\n");
                list.add(content.trim());
            }
//            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
