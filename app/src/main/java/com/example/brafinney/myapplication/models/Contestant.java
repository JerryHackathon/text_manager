package com.example.brafinney.myapplication.models;

import java.io.Serializable;

/**
 * Created by pianoafrik on 5/9/17.
 */

public class Contestant implements Serializable {
    private String id, name, image_url;

    public Contestant(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
    }

    public Contestant(String id, String name, String image_url) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
