package com.app.guide.model;


import java.io.Serializable;

public class Second_model implements Serializable {

    private String description;

    public String getDescription2() {
        return description2;
    }

    private String description2;
    private String images;
    private String titles;



    public Second_model(String images, String description, String titles,String description2) {
        this.images = images;
        this.description = description;
        this.titles = titles;
        this.description2 = description2;
    }

    public String getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public String getTitles() {
        return titles;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }
}
