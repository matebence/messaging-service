package com.blesk.messagingservice.DTO;

public class Notifications {

    private String title;
    private String body;
    private String token;
    private String imageUrl = "blesk-default-notifcation-icon.jpg";

    public Notifications(String title, String body, String token) {
        this.title = title;
        this.body = body;
        this.token = token;
    }

    public Notifications() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}