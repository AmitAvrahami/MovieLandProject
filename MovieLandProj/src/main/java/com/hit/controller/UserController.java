package com.hit.controller;

import com.hit.service.UserService;

public class UserController {

    private UserService m_userService;

    public UserController(UserService userService) {
        this.m_userService = userService;
    }

    //TODO : make some methods as the service

}
