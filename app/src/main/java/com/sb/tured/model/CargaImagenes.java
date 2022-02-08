package com.sb.tured.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CargaImagenes {

    private String id;
    private String url;
    public ArrayList <ImageView> img = new ArrayList<ImageView>();
    private int radius;
    private int margin;
    private int width;
    private int height;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<ImageView> getImg() {
        return img;
    }

    public void setImg(ArrayList<ImageView> img) {
        this.img = img;
    }


}
