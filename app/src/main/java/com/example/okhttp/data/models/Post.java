package com.example.okhttp.data.models;

import java.io.Serializable;

public class Post implements Serializable {

    private int id;
    private int user;
    private int group;
    private String title;
    private String content;


    public Post(String title, String content, int user, int group) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.group = group;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
