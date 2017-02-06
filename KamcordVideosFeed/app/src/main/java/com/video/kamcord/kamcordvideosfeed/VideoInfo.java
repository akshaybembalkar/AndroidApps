package com.video.kamcord.kamcordvideosfeed;

/**
 * Created by Akshay B on 12/25/2016.
 * VideoInfo object stores values of heartCount, shotThumbnail and videoUrl for each record.
 */

public class VideoInfo {

    private int heartCount;
    private String shotThumbnail;
    private String videoUrl;

    public VideoInfo(int count,String thumb,String vidUrl){
        this.heartCount = count;
        this.shotThumbnail = thumb;
        this.videoUrl = vidUrl;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public String getShotThumbnail() {
        return shotThumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
