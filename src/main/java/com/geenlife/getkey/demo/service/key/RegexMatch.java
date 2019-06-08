package com.geenlife.getkey.demo.service.key;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author:zhumeng
 * @desc:
 **/
public class RegexMatch {
    public static void main(String[] args) throws IOException {
        //读取json文件地址
        /* String path = getClass().getClassLoader().getResource("menu.json").toString();
        path = path.replace("\\", "/");
        if (path.contains(":")) {
            path = path.replace("file:/", "");
        }*/
        ClassPathResource resource = new ClassPathResource("regex.json");
        File filePath = resource.getFile();
        System.out.println(filePath);
    }

    public void match() {
        String string = this.getClass().getClassLoader().getResource("regex.json").toString();


        URL resource1 = this.getClass().getResource("");
        System.out.println(string);
        System.out.println(resource1);

    }
}
