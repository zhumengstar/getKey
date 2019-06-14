package com.gennlife.getkey.controller;

import com.alibaba.fastjson.JSON;
import com.gennlife.getkey.file.GetContent;
import com.gennlife.getkey.file.HandlerFile;
import com.gennlife.getkey.file.LoadFileName;
import com.gennlife.getkey.handle.Handle;
import com.gennlife.getkey.util.HttpRequest;
import com.gennlife.getkey.util.KeyData;
import com.gennlife.getkey.util.ReturnData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * @author:zhumeng
 * @desc:
 **/
@RestController
public class Application2 {

    @Value("${filepath}")
    private String filepath;

    @Value("${geturl}")
    private String getUrl;


    /**
     * 访问接口
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/key")
    public ReturnData upload() throws IOException {
        List<String> l = new CopyOnWriteArrayList();
        KeyData k = new KeyData();
        k.setModel_type("key_classify");
        //加载文件目录--->文件名
        List<String> files = LoadFileName.openFile(filepath);
        for (String file : files) {
            //标点分词
            if (Pattern.matches(".*\\.txt$", file)) {
                List list = HandlerFile.txtToDocument(file);
                l.addAll(list);
            } else {
                List contentList = GetContent.getContentList(file);
                l.addAll(contentList);
            }
        }
//        for (String s : l) {
//            AddContentToFile.addContent(true, new ArrayList(), "", s);
//        }
        Map m = new HashMap<>();
        m.put("model_type", "key_recognition");
        m.put("data", l);

        String string = JSON.toJSONString(m);
        String post = HttpRequest.post(getUrl, string);


        //TODO
        //之后的代码未验证
        //访问模型,标注key和no_key转为对应的对象
        ReturnData parse = (ReturnData) JSON.parse(post);

        //进行对key的分类,并进行模型分类
        ReturnData returnData = Handle.handle2(parse);

        //对未分类对key进行再设置分类
//        ReturnData returnData2 = Handle.handle3(returnData);
        //TODO
        //还需要进行写入文件操作
//        k.setData(l);
        //结果转为json格式
        return returnData;
    }
}
