package com.geenlife.getkey.demo.service.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.*;
import java.util.List;

public class HtmlUtils {


    /**
     * html to string
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String fileToString(String path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        File file = new File(path);
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        String s = null;
        while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
            buffer.append(s.trim() + "\n");
        }
        String xml = buffer.toString();
        return xml;
    }

    /**
     * node to string
     *
     * @param node
     * @return
     */
    private static String extractText(Node node) {
        /* TextNode直接返回结果 */
        if (node instanceof TextNode) {
            return ((TextNode) node).text();
        }
        /* 非TextNode的Node，遍历其孩子Node */
        List<Node> children = node.childNodes();
        StringBuffer buffer = new StringBuffer();
        for (Node child : children) {
            buffer.append(extractText(child) + "\n");
        }
        return buffer.toString();
    }

    /**
     * html and jsoup to string
     *
     * @param html
     * @return
     */
    public static String html2Str(String html) {
        Document doc = Jsoup.parse(html);
        return extractText(doc);
    }
}
