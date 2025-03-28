package com.oneasad.journalApp.controller;

import com.oneasad.journalApp.entity.User;
import com.oneasad.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userIndb = userService.findUserByUsername(userName);
        if (userIndb != null) {
            userIndb.setUserName(user.getUserName());
            userIndb.setPassword(user.getPassword());
            userService.saveNewUser(userIndb);
            return new ResponseEntity<>(userIndb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userIndb = userService.findUserByUsername(userName);
        if (userIndb != null) {
            userService.deleteById(userIndb.getId());
            return new ResponseEntity<>(userIndb, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
