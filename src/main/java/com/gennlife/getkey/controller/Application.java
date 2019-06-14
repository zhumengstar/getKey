package com.gennlife.getkey.controller;

import com.gennlife.getkey.file.HandlerFile;
import com.gennlife.getkey.file.LoadFileName;
import com.gennlife.getkey.util.KeyData;
import com.gennlife.getkey.util.PropUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
            KeyData k = new KeyData();
            k.setModel_type("key_classify");
            long start = System.currentTimeMillis();
            KeyData keyData = application.uploadFolder(k, PropUtils.getProp().getProperty("filepath"));
            System.out.println(System.currentTimeMillis() - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //加载各类文件
    public KeyData uploadFolder(KeyData k, String path) throws IOException {

        ExecutorService service = Executors.newFixedThreadPool(40);
        List<String> files = LoadFileName.openFile(path);

//        CountDownLatch latch = new CountDownLatch(files.size());
        for (String file : files) {
//            service.submit(k,new ThreadPool(latch,file));
//            AddContentToFile.addKey(true, new ArrayList(), file, "file://" + file);
            handler(k, file);
        }
        return k;
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        service.shutdown();
    }

    class ThreadPool implements Runnable {
        String file;
        CountDownLatch n;
        KeyData k;

        ThreadPool(KeyData k, CountDownLatch n, String file) {
            this.k = k;
            this.n = n;
            this.file = file;
        }

        @Override
        public void run() {
            try {
                handler(k, file);

            } catch (IOException e) {
                e.printStackTrace();
            }
            n.countDown();
        }
    }
    private List handler(KeyData k, String file) throws IOException {
        List list = new ArrayList();
        if (Pattern.matches(".*\\.xml$", file)) {
//            System.out.println(HandlerFile.xmlToDocument(file));
            list = HandlerFile.xmlToDocument(file);
        }
        if (Pattern.matches(".*\\.html$", file)) {
//            System.out.println(HandlerFile.htmlToDocument(file));
            list = HandlerFile.htmlToDocument(file);
        }
        HandlerFile.htmlToDocument(file);
        if (Pattern.matches(".*\\.txt$", file)) {
//            System.out.println(HandlerFile.txtToDocument(file));
            list = HandlerFile.txtToDocument(file);
        }
        return list;
    }
}
