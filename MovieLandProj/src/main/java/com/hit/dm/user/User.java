package com.hit.dm.user;

import com.hit.dm.movie.Movie;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private Integer m_userId;
    private String m_userName;
    private String m_userPassword;
    private PermissionLevel m_permissionLevel;
    private List<Movie> m_userMovieWatchList;


    public User(Integer userId, String m_userName, String userPassword, PermissionLevel permissionLevel, List<Movie> userMovieWatchList) {
        this.m_userId = userId;
        this.m_userName = m_userName;
        this.m_userPassword = userPassword;
        this.m_permissionLevel = permissionLevel;
        this.m_userMovieWatchList = userMovieWatchList;
    }

    public Integer getUserId() {
        return m_userId;
    }

    public void setUserId(Integer m_userId) {
        this.m_userId = m_userId;
    }

    public String getUserName() {
        return m_userName;
    }

    public void setUserName(String m_userName) {
        this.m_userName = m_userName;
    }

    public String getUserPassword() {
        return m_userPassword;
    }

    public void setUserPassword(String m_userLastName) {
        this.m_userPassword = m_userLastName;
    }

    public PermissionLevel getPermissionLevel() {
        return m_permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel m_permissionLevel) {
        this.m_permissionLevel = m_permissionLevel;
    }

    public List<Movie> getUserMovieWatchList() {
        return m_userMovieWatchList;
    }

    public void setUserMovieWatchList(List<Movie> m_userMovieWatchList) {
        this.m_userMovieWatchList = m_userMovieWatchList;
    }
}
