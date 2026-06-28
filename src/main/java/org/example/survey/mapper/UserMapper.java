package org.example.survey.mapper;

import org.example.survey.entity.User;

public interface UserMapper {
    User selectByUserName(String username);
    int addUser(User user);
    Long getUserId(String username);
}
