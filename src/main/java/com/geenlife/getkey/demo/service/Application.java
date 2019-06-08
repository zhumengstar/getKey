package com.geenlife.getkey.demo.service;

import com.geenlife.getkey.demo.service.file.HandlerFile;
import com.geenlife.getkey.demo.service.file.LoadFileName;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
public class Application {

    public static void main(String[] args) {
        try {
            Application application = new Application();
            long start = System.currentTimeMillis();
            application.uploadFolder("/Users/zgh/Desktop/java/getkey/src/main/resources/static/originfile/xjd");
            System.out.println(System.currentTimeMillis() - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //加载文件
    public void uploadFile(String path) throws IOException {
        new Thread(new ThreadPool(path)).start();
    }

    //加载各类文件
    public void uploadFolder(String path) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(20);
        List<String> files = LoadFileName.openFile(path);
        for (String file : files) {
            service.submit(new ThreadPool(file));
//            handler(file);
        }
        service.shutdown();
    }

    class ThreadPool implements Runnable {
        String file;

        ThreadPool(String file) {
            this.file = file;
        }

        @Override
        public void run() {
            try {
                handler(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handler(String file) throws IOException {
//        AddKeyToFile.add(true, new ArrayList(), file, file);
        Vector<String> v = new Vector<>();
        if (Pattern.matches(".*\\.xml$", file))
            System.out.println(HandlerFile.xmlToDocument(v, file));
        if (Pattern.matches(".*\\.html$", file))
            System.out.println(HandlerFile.htmlToDocument(v, file));
        if (Pattern.matches(".*\\.txt$", file))
            System.out.println(HandlerFile.txtToDocument(v, file));
    }
}
