package com.gennlife.getkey.util;

import java.util.List;

/**
 * @author:zhumeng
 * @desc:
 **/
public class NoMatch {
    String noMatch;
    List<List<String>> data;

    public String getNoMatch() {
        return noMatch;
    }

    public void setNoMatch(String noMatch) {
        this.noMatch = noMatch;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NoMatch{" +
                "noMatch='" + noMatch + '\'' +
                ", data=" + data +
                '}';
    }
}
