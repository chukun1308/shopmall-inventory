package com.chukun.inventory.vo;

public class ResponseData {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private String data;


    public ResponseData() {

    }

    public ResponseData(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
