package com.buaa.service;

import com.buaa.model.entity.User;

public interface AuthService {

    User register( User userToAdd );
    String login( String username, String password );
}
