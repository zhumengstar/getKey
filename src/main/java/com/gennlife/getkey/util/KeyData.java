package com.gennlife.getkey.util;

import java.util.List;

/**
 * @author:zhumeng
 * @desc:
 **/
public class KeyData {

    String model_type;

    List<String> data;

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KeyData{" +
                "model_type='" + model_type + '\'' +
                ", data=" + data +
                '}';
    }
}

