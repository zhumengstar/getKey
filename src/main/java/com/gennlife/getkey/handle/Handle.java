package com.gennlife.getkey.handle;

import com.gennlife.getkey.file.AddContentToFile;
import com.gennlife.getkey.key.RegexMatch;
import com.gennlife.getkey.util.NoMatch;
import com.gennlife.getkey.util.ReturnData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhumeng
 * @desc:
 **/
public class Handle {
    static ReturnData staticReturnData = new ReturnData();
    static NoMatch staticNoMatch = new NoMatch();
    static List<List<String>> lists = new ArrayList<>();
    static List<List<String>> noMatchList = new ArrayList<>();

    public static void main(String[] args) {

        ReturnData r = new ReturnData();
        r.setCode("0");
        List<List<String>> l = new ArrayList();
        List<String> l2 = new ArrayList();
        List<String> l3 = new ArrayList();
        List<String> l4 = new ArrayList();
        List<String> l5 = new ArrayList();
        l2.add("查体");
        l2.add("key");
        l3.add("小时入量");
        l3.add("key");
        l4.add("期间于");
        l4.add("not_key");
        l5.add("***查房记录");
        l5.add("key");
        l.add(l2);
        l.add(l3);
        l.add(l4);
        l.add(l5);
        r.setResult(l);
        handle2(r);
        staticReturnData.setCode("0");
        staticNoMatch.setNoMatch("noMatch");
        staticNoMatch.setData(noMatchList);

        System.out.println(staticNoMatch);

        System.out.println(staticReturnData);
        System.out.println(noMatchList);

    }

    public static void handle(ReturnData returnData) {
        if ("0".equals(returnData.getCode())) {
            for (List<String> l : returnData.getResult()) {
                if ("key".equals(l.get(1))) {
                    String key = RegexMatch.match(l.get(0));
                    if ("key".equals(key)) {
                        AddContentToFile.addContent(true, "", l.get(0) + "         NO_MATCH");
                    } else {
                        AddContentToFile.addContent(true, "", l.get(0) + "      " + key);
                    }
                    l.add(key);
                    lists.add(l);
                } else {
                    AddContentToFile.addContent(false, "", l.get(0));
                }
            }
            staticReturnData.setResult(lists);
        }
    }

    //
    public static ReturnData handle2(ReturnData returnData) {
        if ("0".equals(returnData.getCode())) {
            for (int i = 0; i < returnData.getResult().size(); i++) {
                List<String> l = returnData.getResult().get(i);

                if ("key".equals(l.get(1))) {
                    String key = RegexMatch.match(l.get(0));
                    if ("NO_MATCH".equals(key)) {
                        AddContentToFile.addContent(true, "", l.get(0) + "         NO_MATCH");
                        List<String> nol = new ArrayList<>();
                        nol.add(l.get(0));
                        nol.add(String.valueOf(i));
                        noMatchList.add(nol);
                    } else {
                        AddContentToFile.addContent(true, "", l.get(0) + "      " + key);
                    }
                    l.add(key);
                    lists.add(l);

                } else {
                    AddContentToFile.addContent(false, "", l.get(0));
                }
            }
            staticReturnData.setResult(lists);
        }
        return staticReturnData;
    }

    public static ReturnData handle3(ReturnData returnData) {
        List<List<String>> result = returnData.getResult();
        List<List<String>> result1 = staticReturnData.getResult();
        for (List<String> l : result) {
            List<String> list = result1.get(Integer.valueOf(l.get(1)));
            list.set(2, l.get(2));
        }
        return staticReturnData;
    }
}
