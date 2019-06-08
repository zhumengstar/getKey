package com.geenlife.getkey.demo.service.file;

import com.geenlife.getkey.demo.service.key.Match;

import java.io.*;
import java.util.List;

/**
 * @author:zhumeng
 * @desc: 添加数据到文件中
 **/
public class AddKeyToFile {

    public static void addValue(List list, String value, String path) {
        add(false, list, path, value);
    }

    public static void addKey(List list, String value, String path) {
        //TODO value进行组装
        add(true, list, path, value);
    }

    public static void add(boolean key, List list, String file, String content) {

        //创建数据保存的文件夹
        File f = new File(file);
        String name = f.getName();
        int i = name.indexOf(".");
        String newFileName = name.substring(0, i) + "-" + Thread.currentThread().getId() + ".data";
        File newFile = new File("/Users/zgh/Desktop/java/getkey/src/main/java/com/geenlife/getkey/demo/data/" + newFileName);

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

        //保存数据到指定文件夹中
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile, true)));
            //清除空格
//            content = content.replace(" ", "");
            if (key) {
                out.write("key:" + content + "\t\t\t\t\t" + Match.match(content) + "\n");
//                if (Match.match(content).equals("key"))
                    list.add(content.trim());
            } else {
                out.write("    " + content + "\n");
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
}
