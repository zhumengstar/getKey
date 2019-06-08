package com.geenlife.getkey.mywork.httpService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static final int port = 8088;
    private ServerSocket serverSocket = null;

    public HttpServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("HTTPServer startup OK...");
    }

    public String getRequest(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        int size = socketIn.available();
        byte[] requestBuff = new byte[size];
        socketIn.read(requestBuff);
        return new String(requestBuff);
    }

    public String getURI(String request) {
        String firstLine = request.substring(0, request.indexOf("\r\n"));
        String[] parts = firstLine.split(" ");

        return parts[1];
    }

    public String getContentType(String URI) {
        /* 决定HTTP响应正文的类型 */
        String contentType;
        if (URI.indexOf("html") != -1 || URI.indexOf("htm") != -1)
            contentType = "data/html";
        else if (URI.indexOf("jpg") != -1 || URI.indexOf("jpeg") != -1)
            contentType = "image/jpeg";
        else if (URI.indexOf("gif") != -1)
            contentType = "image/gif";
        else
            contentType = "application/octet-stream";
        return contentType;
    }

    public InputStream getResponseContent(String URI)
            throws FileNotFoundException {

        InputStream htmlInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/webapp/WEB-INF" + URI);
        return htmlInputStream;
    }

    public String assembleResponseHead(String URI, String contentType) {
        /* 创建HTTP响应结果 */
        // HTTP响应的第一行
        String responseFirstLine = "HTTP/1.1 200 OK\r\n";
        // HTTP响应头
        String responseHeader = "Content-Type:" + contentType + "\r\n\r\n";

        return responseFirstLine + responseHeader;
    }

    public void service() throws InterruptedException {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();

                String request = getRequest(socket);
                System.out.println("HttpServer receive request:\n" + request);

                String URI = getURI(request);

                String contentType = getContentType(URI);
                OutputStream out = socket.getOutputStream();

                out.write(assembleResponseHead(URI, contentType).getBytes());

                int len = 0;
                byte[] buffer = new byte[128];
                InputStream htmlInputStream = getResponseContent(URI);
                while ((len = htmlInputStream.read(buffer)) != -1)
                    out.write(buffer, 0, len);
                Thread.sleep(1000);
                socket.close(); // 关闭TCP连接
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException,
            InterruptedException {
        HttpServer httpServer = new HttpServer();
        // System.out.println(System.getProperty("user.dir"));
//        while (true)
        httpServer.service();
//        String s="demoasd\r\n";
//        System.out.println(s.indexOf(""));
    }

}