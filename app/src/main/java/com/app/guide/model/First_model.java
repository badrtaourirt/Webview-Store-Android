package com.app.guide.model;

import org.json.JSONArray;

public class First_model {

    private String image;
    private String description;
    private String title;
    private String myjsonarray;



    private String imagebackground;


    public First_model(String image, String description, String title,String myjsonarray,String imagebackground) {
        this.image = image;
        this.description = description;
        this.title = title;
        this.myjsonarray=myjsonarray;
        this.imagebackground=imagebackground;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getImagebackground() {
        return imagebackground;
    }
    public String getTitle() {
        return title;
    }

    public String getMyjsonarray() {
        return myjsonarray;
    }

}
