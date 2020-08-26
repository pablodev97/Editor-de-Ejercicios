package com.pjimeno97.exerciseeditortool;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String name;
    private String description;
    private String urlVideo;
    private String muscle;

    public Exercise(String name, String description, String urlVideo, String muscle) {
        this.name = name;
        this.description = description;
        this.urlVideo = urlVideo;
        this.muscle = muscle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }
}
