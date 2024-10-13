package org.login.backend.controller;

import org.login.backend.service.UserService;
import org.login.backend.user.User;
import org.login.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.core.ClientInfo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Resource
    private UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<ClientInfo> loginController(@RequestParam String user_name, @RequestParam String password) {
        User user = userService.loginService(user_name, password);
        if (user != null) {
            ClientInfo clientInfo = new ClientInfo(user.getUserId(), user.getUserName(), user.getAge());
            return ResponseEntity.status(HttpStatus.OK).body(clientInfo);
        } else {
            ClientInfo clientInfo1 = new ClientInfo(0, "null", 0);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(clientInfo1);
        }
    }






    @PostMapping("/user/register")
    public ResponseEntity<ClientInfo> registerController(@RequestBody ClientInfo newUser) {
        User user1 = new User(newUser.name, newUser.password, newUser.age);
        User user = userService.registerService(user1);
        if (user != null) {
            ClientInfo clientInfo = new ClientInfo(user.getUserId(), user.getUserName(), user.getAge());
            return ResponseEntity.status(HttpStatus.CREATED).body(clientInfo);
        } else {
            ClientInfo clientInfo1 = new ClientInfo(0, "null", 0);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(clientInfo1);


        }
    }
}