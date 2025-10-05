package com.lays.dao;

import com.lays.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();

    void save(User user);

    User getById(Integer id);
    User getByLogin(String login);
}
