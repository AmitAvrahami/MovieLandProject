package com.hit.dm.movie;

import com.hit.dm.actor.Actor;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    private Integer m_movieId;
    private String m_movieName;
    private MovieRateRange m_movieRate;
    private String m_movieSynopsis;
    private MovieCategory m_movieCategory;
    private String m_movieTime;
    private List<Actor> m_movieActors = new ArrayList<>();
    private List<Image> m_moviePhoto = new ArrayList<>();

    public Movie(Integer m_movieId, String m_movieName, MovieRateRange m_movieRate, String m_movieSynopsis, MovieCategory m_movieCategory, String m_movieTime, List<Actor> m_movieActors, List<Image> m_moviePhoto) {
        this.m_movieId = m_movieId;
        this.m_movieName = m_movieName;
        this.m_movieRate = m_movieRate;
        this.m_movieSynopsis = m_movieSynopsis;
        this.m_movieCategory = m_movieCategory;
        this.m_movieTime = m_movieTime;
        this.m_movieActors = m_movieActors;
        this.m_moviePhoto = m_moviePhoto;
    }

    public Integer getM_movieId() {
        return m_movieId;
    }

    public void setM_movieId(Integer m_movieId) {
        this.m_movieId = m_movieId;
    }

    public String getM_movieName() {
        return m_movieName;
    }

    public void setM_movieName(String m_movieName) {
        this.m_movieName = m_movieName;
    }

    public MovieRateRange getM_movieRate() {
        return m_movieRate;
    }

    public void setM_movieRate(MovieRateRange m_movieRate) {
        this.m_movieRate = m_movieRate;
    }

    public String getM_movieSynopsis() {
        return m_movieSynopsis;
    }

    public void setM_movieSynopsis(String m_movieSynopsis) {
        this.m_movieSynopsis = m_movieSynopsis;
    }

    public MovieCategory getM_movieCategory() {
        return m_movieCategory;
    }

    public void setM_movieCategory(MovieCategory m_movieCategory) {
        this.m_movieCategory = m_movieCategory;
    }

    public String getM_movieTime() {
        return m_movieTime;
    }

    public void setM_movieTime(String m_movieTime) {
        this.m_movieTime = m_movieTime;
    }

    public List<Actor> getM_movieActors() {
        return m_movieActors;
    }

    public void setM_movieActors(List<Actor> m_movieActors) {
        this.m_movieActors = m_movieActors;
    }

    public List<Image> getM_moviePhoto() {
        return m_moviePhoto;
    }

    public void setM_moviePhoto(List<Image> m_moviePhoto) {
        this.m_moviePhoto = m_moviePhoto;
    }
}

