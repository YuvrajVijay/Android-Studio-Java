package com.example.introfirestore;

public class Journal {
    String title;
    String thoughts;

    public Journal() {
    }

    public Journal(String title, String thoughts) {
        this.title = title;
        this.thoughts = thoughts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }
}
