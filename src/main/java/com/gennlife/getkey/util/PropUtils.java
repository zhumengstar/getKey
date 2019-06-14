package com.gennlife.getkey.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author:zhumeng
 * @desc:
 **/
public class PropUtils {
    public static Properties getProp() {
        Properties prop = new Properties();
        try {
            //读取属性文件a.properties
            ClassPathResource resource = new ClassPathResource("application.properties");

            InputStream in = new BufferedInputStream(resource.getInputStream());
            prop.load(in);     ///加载属性列表
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
