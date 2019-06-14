package com.gennlife.getkey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author:zhumeng
 * @desc:
 **/
@Controller
public class LoadController {
    //文件下载
    @RequestMapping("/fileDownload")
    public void fileDownload(String fileName, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //一个头,两个流
        InputStream is = null;
        OutputStream os = null;
        //首先还是获取upload目录下的文件名路径
        String path = request.getSession().getServletContext().getRealPath("/upload/" + fileName);
        System.out.println(path);
        //根据获取的路径,读入文件到内存中
        //从服务器端本地读入文件流
        is = new BufferedInputStream(new FileInputStream(path));
        //解决文件名在下载时遇到中文出现乱码问题----反向编码一次
        //注意这个编码要在文件读取文件到输入流中之后,不然把文件名先改变编码后,
        //在服务器端根据路径去读文件到输入流,会找不到这个文件,因为编码方式不同,文件名就变了,会报错
        fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
        response.addHeader("content-Disposition", "attachment;fileName=" + fileName);
        byte[] b = new byte[1024];
        os = response.getOutputStream(); //写出到客户端的写出流
        int len = -1;
        while ((len = is.read(b)) != -1) {
            os.write(b, 0, len);
        }
        os.close();
        is.close();
    }
}
