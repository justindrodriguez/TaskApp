package com.example.taskapp.models;
//package com.remwebdevelopment.samplecode2018.models;

import java.io.Serializable; // We'll explain this later, when we learn more about interfaces
import java.util.ArrayList;

public class User implements Serializable {

    public static ArrayList<User>allUsers = new ArrayList(){{
        add(new User(1,"bob", "bob@b.com", User.Music.COUNTRY, true));
        add(new User(2,"sally", "sally@b.com", User.Music.RAP, false));
        add(new User(3, "betty", "betty@b.com", User.Music.JAZZ, true));
    }};


    public enum Music {COUNTRY, RAP, JAZZ}
    // Note that if it's not public it won't be visible outside the package!!

    private long id;
    private String firstName;
    private String email;
    private Music favoriteMusic;
    private boolean active;

    public User(){}

    public User(long id, String firstName, String email, Music favoriteMusic, boolean active){
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.favoriteMusic = favoriteMusic;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Music getFavoriteMusic() {
        return favoriteMusic;
    }

    public void setFavoriteMusic(Music favoriteMusic) {
        this.favoriteMusic = favoriteMusic;
    }

    public boolean isActive() { return active;    }

    public void setActive(boolean active) {
        this.active = active;
    }



    @Override
    public String toString(){
        return this.id + "-" + this.getFirstName() + "-" + this.getEmail() + "-" + this.getFavoriteMusic() + "-" + this.isActive();
    }
}