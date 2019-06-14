package com.gennlife.getkey;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@Configurable //允许基于注解的配置
public class GetkeyApplication {


    /**
     * 配置文件上传大小
     */
    @Bean
    public MultipartConfigElement getMultiConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("4000MB");
        factory.setMaxRequestSize("4000MB");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(GetkeyApplication.class, args);
    }

}
