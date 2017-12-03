package com.example.sami.mappe3;

public class Achievement {

    private String date;
    private String description;
    private int resourceId;

    public Achievement(String date, String description, int resourceId) {
        this.date = date;
        this.description = description;
        this.resourceId = resourceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResourceId(){
        return resourceId;
    }
    public void setResourceId(int resourceId){
        this.resourceId = resourceId;
    }
}
