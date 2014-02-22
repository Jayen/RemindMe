package com.app.jayen.remindme;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class Reminder {
    private long id;
    private String title;
    private String description;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
