package org.login.backend.service.serviceImpl;

import org.login.backend.service.UserService;
import org.login.backend.user.User;
import org.login.backend.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Override
    public User loginService(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if(user != null){
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User registerService(User user) {
//        System.out.println("userId = "+user.getUserId());
        User user1 = userRepository.findUserByUserName(user.getUserName());
        if(user1!=null){
            return null;
        }else{
            User newUser = userRepository.save(user);
            return newUser;
        }
    }
}
