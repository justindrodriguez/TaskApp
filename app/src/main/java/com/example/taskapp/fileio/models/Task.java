package com.example.taskapp.fileio.models;

import java.util.Date;

public class Task {
    private long id;
    private String description;
    private Date due;
    private boolean done;

    // Overload

    public Task(String description, Date due, boolean done) {
        this.description = description;
        this.due = due;
        this.done = done;
    }

    public Task(long id, String description, Date due, boolean done) {
        this.id = id;
        this.description = description;
        this.due = due;
        this.done = done;
    }

    // Getters Setters //

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    // Business Logic

    @Override
    public String toString(){
        String dueStr = due != null ? due.toString() : "NULL";
        return String.format("ID: %d DESC: %s DUE: %s COMPLETED: %b", this.id, this.description, dueStr, this.done);
    }



}
