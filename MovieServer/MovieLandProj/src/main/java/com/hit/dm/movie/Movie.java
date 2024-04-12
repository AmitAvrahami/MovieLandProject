package com.hit.dm.movie;

import com.hit.dm.actor.Actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Movie implements Serializable, Comparable<Movie> {
    private static final long serialVersionUID =1883642389429622930L;
    private static Integer  lastAssignedId = 0;
    private Integer m_movieId;
    private String m_movieName;
    private String m_movieSynopsis;
    private MovieCategory m_movieCategory;
    private String m_movieTime;

    public String getMovieImage() {
        return m_movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.m_movieImage = movieImage;
    }

    private String m_movieImage;
    private List<Actor> m_movieActors = new ArrayList<>();

    public Movie(Integer m_movieId, String m_movieName, String m_movieSynopsis, MovieCategory m_movieCategory, String m_movieTime, List<Actor> m_movieActors) {
        this.m_movieId = m_movieId;
        this.m_movieName = m_movieName;
        this.m_movieSynopsis = m_movieSynopsis;
        this.m_movieCategory = m_movieCategory;
        this.m_movieTime = m_movieTime;
        if (m_movieActors == null) this.m_movieActors = new ArrayList<>(); else this.m_movieActors = m_movieActors;
    }

    public Movie() {
        this.m_movieId = 0;
        this.m_movieName = "";
        this.m_movieSynopsis = "";
        this.m_movieCategory = MovieCategory.ACTION;
        this.m_movieTime = "";
        this.m_movieImage = "";
        this.m_movieActors.add(new Actor());
    }

    public Integer getMovieId() {
        return m_movieId;
    }

    public void setMovieId(Integer m_movieId) {
        this.m_movieId = m_movieId;
    }

    public String getMovieName() {
        return m_movieName;
    }

    public void setMovieName(String m_movieName) {
        this.m_movieName = m_movieName;
    }



    public String getMovieSynopsis() {
        return m_movieSynopsis;
    }

    public void setMovieSynopsis(String m_movieSynopsis) {
        this.m_movieSynopsis = m_movieSynopsis;
    }

    public MovieCategory getMovieCategory() {
        return m_movieCategory;
    }

    public void setMovieCategory(MovieCategory m_movieCategory) {
        this.m_movieCategory = m_movieCategory;
    }

    public String getMovieTime() {
        return m_movieTime;
    }

    public void setMovieTime(String m_movieTime) {
        this.m_movieTime = m_movieTime;
    }

    public List<Actor> getMovieActors() {
        return m_movieActors;
    }

    public void setMovieActors(List<Actor> m_movieActors) {
        this.m_movieActors = m_movieActors;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Movie ID: ").append(m_movieId).append("\n");
        stringBuilder.append("Movie Name: ").append(m_movieName).append("\n");
        stringBuilder.append("Movie Synopsis: ").append(m_movieSynopsis).append("\n");
        stringBuilder.append("Movie Category: ").append(m_movieCategory).append("\n");
        stringBuilder.append("Movie Time: ").append(m_movieTime).append("\n");
        stringBuilder.append("Movie Actors: \n");
        for (Actor actor : m_movieActors) {
            stringBuilder.append("\t").append(actor).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null || getClass() != o.getClass()) {
            Movie movie = (Movie) o;
            if (Objects.equals(m_movieId, movie.m_movieId) && Objects.equals(m_movieName, movie.m_movieName)  && Objects.equals(m_movieSynopsis, movie.m_movieSynopsis) && Objects.equals(m_movieCategory, movie.m_movieCategory) && Objects.equals(m_movieTime, movie.m_movieTime) && Objects.equals(m_movieActors.size(), movie.m_movieActors.size()))
                return true;

        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_movieId, m_movieName, m_movieSynopsis, m_movieCategory, m_movieTime, m_movieActors);
    }



    @Override
    public int compareTo(Movie otherMovie) {
        return Integer.compare(this.getMovieId(), otherMovie.getMovieId());
    }
}

