package com.tanglei.springbootstudydemo.entity;

public class Student {
    private String id;
    private String score;
    private String name;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", score='" + score + '\'' +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
