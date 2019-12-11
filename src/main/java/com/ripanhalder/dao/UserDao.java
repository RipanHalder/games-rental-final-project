package com.ripanhalder.dao;

import com.ripanhalder.entity.User;

public interface UserDao {

    User searchByUName(String userName);
    
    User findUserByUserId(long userId);
    
    void save(User user);
    
}
