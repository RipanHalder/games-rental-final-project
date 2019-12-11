package com.ripanhalder.service;

import com.ripanhalder.entity.User;
import com.ripanhalder.user.RegisterationUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User searchByUName(String userName);

    void save(RegisterationUser registerationUser);
}
