package com.gennlife.getkey.util;

import java.util.List;

/**
 * @author:zhumeng
 * @desc:
 **/
public class ReturnData {
    String code;
    List<List<String>> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<List<String>> getResult() {
        return result;
    }

    public void setResult(List<List<String>> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ReturnData{" +
                "code='" + code + '\'' +
                ", result=" + result +
                '}';
    }
}
