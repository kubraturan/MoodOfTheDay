package com.example.developer.moodoftheday;

import android.support.v7.widget.RecyclerView;

/**
 * Created by rumeysal on 8/19/17.
 */

public class Post {
    private String name;
    private int ImageSource;
    private int mode;
    private String post;


    Post() {}

    public Post(String name, int imageSource, int mode, String post) {
        this.name = name;
        ImageSource = imageSource;
        this.mode = mode;
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageSource() {
        return ImageSource;
    }

    public void setImageSource(int imageSource) {
        ImageSource = imageSource;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
