package com.hit.dm.actor;

import com.hit.dm.movie.Movie;

import java.awt.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.List;

public class Actor implements Serializable {

    private Integer actorId;
    private String actorName;
    private String actorLastName;
    private Integer actorAge;
    private List<Movie> actorMovies;

    public Actor(Integer actorId, String actorName, String actorLastName, Integer actorAge, List<Movie> actorMovies) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorLastName = actorLastName;
        this.actorAge = actorAge;
        this.actorMovies = actorMovies;
    }

    public Actor() {

    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorLastName() {
        return actorLastName;
    }

    public void setActorLastName(String actorLastName) {
        this.actorLastName = actorLastName;
    }

    public Integer getActorAge() {
        return actorAge;
    }

    public void setActorAge(Integer actorAge) {
        this.actorAge = actorAge;
    }

    public List<Movie> getActorMovies() {
        return actorMovies;
    }

    public void setActorMovies(List<Movie> actorMovies) {
        this.actorMovies = actorMovies;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", actorName='" + actorName + '\'' +
                ", actorLastName='" + actorLastName + '\'' +
                ", actorAge=" + actorAge +
                ", actorMovies=" + actorMovies +
                '}';
    }
}
