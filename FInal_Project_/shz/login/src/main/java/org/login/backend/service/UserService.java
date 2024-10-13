package org.login.backend.service;

import org.login.backend.user.User;

//import org.springboot.springbootlogindemo.domain.User;

public interface UserService {
    /**
     * login logic
     * @param username
     * @param password
     * @return
     */
    User loginService(String username, String password);

    /**
     * user registration login
     * @param user
     * @return
     */
    User registerService(User user);
}
