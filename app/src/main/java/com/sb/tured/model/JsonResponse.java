package com.sb.tured.model;

import com.google.gson.JsonObject;

public class JsonResponse {

    private JsonObject json_object_response;
    private JsonObject json_response_data;


    public JsonObject getJson_object_response() {
        return json_object_response;
    }

    public void setJson_object_response(JsonObject json_object_response) {
        this.json_object_response = json_object_response;
    }

    public JsonObject getJson_response_data() {
        return json_response_data;
    }

    public void setJson_response_data(JsonObject json_response_data) {
        this.json_response_data = json_response_data;
    }
}

