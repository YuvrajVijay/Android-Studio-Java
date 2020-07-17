package Model;

import com.google.firebase.Timestamp;

public class Journal {
    private String title;
    private String thoughts;
    private String imageUrl;
    private String userId;
    private com.google.firebase.Timestamp timeadded;
    private String userName;

    public Journal() {
    }

    public Journal(String title, String thoughts, String imageUrl, String userId, com.google.firebase.Timestamp timeadded, String userName) {
        this.title = title;
        this.thoughts = thoughts;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.timeadded = timeadded;
        this.userName = userName;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public com.google.firebase.Timestamp getTimeadded() {
        return timeadded;
    }

    public void setTimeadded(Timestamp timeadded) {
        this.timeadded = timeadded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
