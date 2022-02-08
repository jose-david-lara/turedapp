package com.sb.tured.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class tokenResponse {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("result")
    @Expose
    private List<String> result;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
