package com.gennlife.getkey.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    public static void main(String[] args) throws IOException {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        List<String> l = new ArrayList<>();
        l.add("目前情况");
        l.add("查体");
        mapParams.put("model_type", "key_recognition");
        mapParams.put("data", l);
        String string = JSON.toJSONString(mapParams);

        String post = post("http://10.0.2.100:1500/key_recognition", string);
        System.out.println(post);


    }

    private static String ascii2native(String asciicode) {
        String[] asciis = asciicode.split("\\\\u");
        String nativeValue = asciis[0];
        try {
            for (int i = 1; i < asciis.length; i++) {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
                if (code.length() > 4) {
                    nativeValue += code.substring(4, code.length());
                }
            }
        } catch (NumberFormatException e) {
            return asciicode;
        }
        return nativeValue;
    }

    public static String post(String url, String param) throws IOException {
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod method = new PostMethod(url);
        RequestEntity entity = new StringRequestEntity(param, "application/json", "UTF-8");
        method.setRequestEntity(entity);
        httpClient.executeMethod(method);
        InputStream in = method.getResponseBodyAsStream();
        //下面将stream转换为String
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(in, "utf-8");
        char[] b = new char[4096];
        for (int n; (n = isr.read(b)) != -1; ) {
            sb.append(new String(b, 0, n));
        }
        String returnStr = sb.toString();
        return ascii2native(returnStr);
    }
}
