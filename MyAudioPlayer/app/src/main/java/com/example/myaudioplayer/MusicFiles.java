package com.example.myaudioplayer;

public class MusicFiles {
    private String path;
    private String artist;
    private String album;
    private String title;
    private String duration;
    private String id;



    public MusicFiles(String path, String artist, String album, String title, String duration, String id) {
        this.path = path;
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.duration = duration;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MusicFiles() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
