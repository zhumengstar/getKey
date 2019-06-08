package com.geenlife.getkey.demo.service.file;

import com.geenlife.getkey.demo.service.key.GetKey;
import com.geenlife.getkey.demo.service.util.HtmlUtils;
import com.geenlife.getkey.demo.service.util.KeyData;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author:zhumeng
 * @desc: 三种不同的文件类型进行解析
 **/
public class HandlerFile {
    public static void main(String[] args) throws IOException {
//        System.out.println(xmlToDocument(new Vector(), "/Users/zgh/Desktop/java/getkey/src/main/resources/static/originfile/xjd/jh.xml"));
//        System.out.println(htmlToDocument(new Vector(), "/Users/zgh/Desktop/java/getkey/src/main/resources/static/originfile/xjd/file.html"));
        System.out.println(txtToDocument(new Vector(), "/Users/zgh/Desktop/java/getkey/src/main/resources/static/originfile/xjd/export/000001158000_1_1_00010015_A.txt"));
    }

    public static KeyData htmlToDocument(Vector v, String path) throws IOException {
        List<String> list = new ArrayList<>();
        org.jsoup.nodes.Document document = Jsoup.parse(HtmlUtils.fileToString(path));
        Elements c = document.getElementsByTag("c");
        for (org.jsoup.nodes.Element element : c) {
            String name = element.attr("name");
            String value = element.text();
            if (name != null && !"".equals(name.trim())) GetKey.getKeyForName(list, v, name, path);
            if (value != null && !"".equals(value.replaceAll(" ", ""))) GetKey.getKeyForValue(list, v, value, path);
        }
        KeyData keyData = new KeyData();
        keyData.setModel_type("file://"+path);
        keyData.setModel_type("key_classify");
        keyData.setData(list);
        return keyData;
    }


    public static KeyData txtToDocument(Vector v, String path) throws IOException {
        List<String> list = new ArrayList<>();


        InputStreamReader read = new InputStreamReader(new FileInputStream(new File(path)), EncodingDetect.detect(path));
        BufferedReader br = new BufferedReader(read);

        String text = null;
        while ((text = br.readLine()) != null) {
            //使用readLine方法，一次读一行
            if (!"".equals(text)) {
                GetKey.getKeyForValue(list, v, text, path);
            }
        }
        br.close();
        KeyData keyData = new KeyData();
        keyData.setModel_type("file://"+path);
        keyData.setModel_type("key_classify");
        keyData.setData(list);
        return keyData;
    }


    public static KeyData xmlToDocument(Vector v, String path) {
        List<String> list = new ArrayList<>();

        File xmlFile = new File(path);
        SAXReader sax = new SAXReader();
        sax.setEncoding(EncodingDetect.detect(xmlFile));

        Document document = null;//获取document对象,如果文档无节点，则会抛出Exception提前结束
        try {
            document = sax.read(xmlFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();//获取根节点
        getNodes(list, v, root, path);//从根节点开始遍历所有节点

        KeyData keyData = new KeyData();
        keyData.setModel_type("file://"+path);
        keyData.setModel_type("key_classify");
        keyData.setData(list);
        return keyData;
    }

    /**
     * 从指定节点开始,递归遍历所有子节点
     *
     * @param node
     */
    public static void getNodes(List list, Vector v, Element node, String path) {

        GetKey.getXmlKey(list, v, node, path);
        //递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();//所有一级子节点的list
        for (Element e : listElement) {//遍历所有一级子节点
            getNodes(list, v, e, path);//递归
        }
    }
}
