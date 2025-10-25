package com.lays.service;

import com.lays.dto.UserDto;
import com.lays.entity.User;

import java.util.List;
import java.util.List;

public interface UserService {

    List<UserDto> getAll();
    boolean registerUser(User user);
    User loginUser(String login, String password);
    boolean isLoginAvailable(String login);
    User getUserByLogin(String login);
}
