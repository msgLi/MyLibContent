package com.mygame.android.model;

/**
 * Created by msg on 2017/7/24.
 */

public class BasicNameValuePair implements NameValuePair {

    private String name;
    private String value;

    public BasicNameValuePair(String name,String value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
