package com.example.myapplication;

public class PostsModel {
    private String title;
    private String created_at;
    private boolean isSwitchChecked;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isSwitchChecked() {
        return isSwitchChecked;
    }

    public void setSwitchChecked(boolean switchChecked) {
        isSwitchChecked = switchChecked;
    }
}
