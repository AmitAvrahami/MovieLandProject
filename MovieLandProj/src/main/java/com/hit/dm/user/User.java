package com.hit.dm.user;

import com.hit.dm.movie.Movie;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID =  -2065987734618278341L;

    private Integer m_userId;
    private String m_userName;
    private String m_userPassword;
    private PermissionLevel m_permissionLevel;
    private HashMap<Movie, Boolean> m_userMovieWatchList;

    public User() {
    }

    public User(Integer userId, String m_userName, String userPassword, PermissionLevel permissionLevel, HashMap<Movie, Boolean> userMovieWatchList) {
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

    public  HashMap<Movie, Boolean> getUserMovieWatchList() {
        return m_userMovieWatchList;
    }

    public void setUserMovieWatchList(HashMap<Movie, Boolean> m_userMovieWatchList) {
        this.m_userMovieWatchList = m_userMovieWatchList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(m_userId, user.m_userId) && Objects.equals(m_userName, user.m_userName) && Objects.equals(m_userPassword, user.m_userPassword) && m_permissionLevel == user.m_permissionLevel && Objects.equals(m_userMovieWatchList.size(), user.m_userMovieWatchList.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_userId, m_userName, m_userPassword, m_permissionLevel, m_userMovieWatchList);
    }
}
