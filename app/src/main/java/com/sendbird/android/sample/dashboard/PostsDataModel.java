package com.sendbird.android.sample.dashboard;


import java.util.Date;

public class PostsDataModel {
    String name;
    String post;
    String img;
    Date date;

    public PostsDataModel(String name, String Post, String img, Date date){
        this.name = name;
        this. post = Post;
        this.img = img;
        this.date = date;
    }

    public PostsDataModel(){}

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getPost() {
        return post;
    }
}
