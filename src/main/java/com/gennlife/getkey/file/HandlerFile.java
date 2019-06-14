package com.gennlife.getkey.file;

import com.gennlife.getkey.key.SplitContent;
import com.gennlife.getkey.util.HtmlUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhumeng
 * @desc: 三种不同的文件类型进行解析
 **/
public class HandlerFile {

    public static List htmlToDocument(String path) throws IOException {
        List<String> list = new ArrayList<>();
        org.jsoup.nodes.Document document = Jsoup.parse(HtmlUtils.fileToString(path));
        Elements c = document.getElementsByTag("c");
        for (org.jsoup.nodes.Element element : c) {
            String name = element.attr("name");
            String value = element.text();
            if (name.equals("SubTitle") || name.equals("标题")) {
                //直接添加为key
                AddContentToFile.addContent(true, list, path, value);
                continue;
            }
            if (value != null && !"".equals(value)) SplitContent.getKeyForValue(list, value, path);
        }
        return list;
    }


    public static List txtToDocument(String path) throws IOException {
        List<String> list = new ArrayList<>();

        InputStreamReader read = new InputStreamReader(new FileInputStream(new File(path)), EncodingDetect.detect(path));
        BufferedReader br = new BufferedReader(read);

        String text = null;
        while ((text = br.readLine()) != null) {
            //使用readLine方法，一次读一行
            if (!"".equals(text)) {
                SplitContent.getKeyForValue(list, text, path);
            }
        }
        br.close();
        return list;
    }
    public static List xmlToDocument(String path) {
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

        if (path.contains("zzzx")) {
            zzzxGetNodes(list, root, path);//从根节点开始遍历所有节点
        }
        if (path.contains("xjd")) {
            xjdGetNodes(list, root, path);
        }
        return list;
    }

    /**
     * 从指定节点开始,递归遍历所有子节点
     *
     * @param node
     */
    public static void xjdGetNodes(List list, Element node, String path) {

        if (node.attribute("name") != null)
            if ("SubTitle".equals(node.attribute("name").getValue()) || "标题".equals(node.attribute("name").getValue())) {
                AddContentToFile.addContent(true, list, path, node.getTextTrim());
            } else {
                SplitContent.getKeyForValue(list, node.getTextTrim(), path);
            }
        //递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();//所有一级子节点的list
        for (Element e : listElement) {//遍历所有一级子节点
            xjdGetNodes(list, e, path);//递归
        }
    }


    /**
     * 从指定节点开始,递归遍历所有子节点
     *
     * @param node
     */
    public static void zzzxGetNodes(List list, Element node, String path) {
        if (node.attribute("ID") != null) {
            if (node.attribute("ID") != null && ("TITLE".equals(node.attribute("ID").getValue()) || "TITLE_NO".equals(node.attribute("ID").getValue()))) {
                AddContentToFile.addContent(true, list, path, node.getText());
            } else {
                SplitContent.getKeyForValue(list, node.getText(), path);
            }
        }
        //递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();//所有一级子节点的list
        for (Element e : listElement) {//遍历所有一级子节点
            zzzxGetNodes(list, e, path);//递归
        }
    }
}
