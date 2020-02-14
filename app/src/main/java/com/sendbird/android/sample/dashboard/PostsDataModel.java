package com.sendbird.android.sample.dashboard;


public class PostsDataModel {
    String name;
    String post;
    String img;

    public PostsDataModel(String name, String Post, String img){
        this.name = name;
        this. post = Post;
        this.img = img;
    }

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
